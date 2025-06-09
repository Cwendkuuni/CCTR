import os
import json
import time
import openai
from openai import OpenAI
import tiktoken
from dotenv import load_dotenv
from pathlib import Path

# Load environment variables and configure OpenAI client
load_dotenv()
client = OpenAI(api_key=os.getenv("OPENAI_API_KEY-1"))
encoding = tiktoken.encoding_for_model("gpt-4o")

# Constants for model configuration and token limits
MODEL_NAME = "gpt-4o"
MAX_TOTAL_TOKENS = 128000
RESERVED_RESPONSE_TOKENS = 16000
TEMPERATURE = 0.1
NUM_ITERATIONS = 15

# Output and logging directories
OUTPUT_BASE_DIR = "Generated-TestSuite-output"
LOG_DIR = "error_logs"

def log_error(file_path, message):
    """Log errors to a file in the error log directory."""
    os.makedirs(LOG_DIR, exist_ok=True)
    log_path = os.path.join(LOG_DIR, f"{file_path}.log")
    with open(log_path, "a") as log_file:
        log_file.write(f"{message}\n")

def trim_text_to_token_limit(text, reserved_tokens=RESERVED_RESPONSE_TOKENS, max_total_tokens=MAX_TOTAL_TOKENS):
    """Trim input text to respect the maximum number of tokens allowed."""
    max_prompt_tokens = max_total_tokens - reserved_tokens
    tokens = encoding.encode(text)
    if len(tokens) > max_prompt_tokens:
        return encoding.decode(tokens[:max_prompt_tokens])
    return text

def generate_test_prompt(class_name, source_code):
    """Generate the test generation prompt for the LLM."""
    return f"""
As a professional software tester who writes Java test methods, generate a complete JUnit 4 test suite to comprehensively test all methods in the following class named {class_name}. 

Here is the source code:
{source_code}

Please ensure the generated test suite is enclosed within triple backticks ``` ``` for easy extraction.
"""

def get_completion(prompt, retries=30):
    """Query the OpenAI API with retry logic in case of error or rate limiting."""
    prompt = trim_text_to_token_limit(prompt)

    for attempt in range(retries):
        try:
            response = client.chat.completions.create(
                model=MODEL_NAME,
                messages=[{"role": "user", "content": prompt}],
                max_tokens=RESERVED_RESPONSE_TOKENS,
                temperature=TEMPERATURE
            )
            return response.choices[0].message.content.strip()

        except openai.APIError as e:
            print(f"API error: {e}")
            if attempt < retries - 1:
                time.sleep(10)
            else:
                raise RuntimeError("Failed after multiple attempts due to API error.")

        except openai.RateLimitError as e:
            print(f"Rate limit error: {e}")
            if attempt < retries - 1:
                time.sleep(10)
            else:
                raise RuntimeError("Failed after multiple attempts due to rate limit.")

        except Exception as e:
            print(f"Unexpected error: {e}")
            if attempt < retries - 1:
                time.sleep(10)
            else:
                raise RuntimeError(f"Failed after multiple attempts due to unexpected error: {e}")

def save_generated_test(output_dir, filename, content):
    """Save the generated test suite to a file."""
    os.makedirs(output_dir, exist_ok=True)
    filepath = os.path.join(output_dir, filename)
    with open(filepath, "w", encoding="utf-8") as f:
        f.write(content)

def process_dataset(dataset_path, dataset_name, has_bug_id=False):
    """Main processing loop for each dataset entry."""
    with open(dataset_path, 'r', encoding="utf-8") as f:
        data = json.load(f)

    for entry in data:
        project_name = entry["project_name"]
        class_name = entry["class"]
        fqdn = entry["fqdn"]
        source_code = entry["source_code"]
        bug_id = entry["bug-id"] if has_bug_id else "null"

        if not source_code.strip():
            log_error(f"{project_name}-{bug_id}-{class_name}", "Empty source code. Skipping generation.")
            continue

        for iteration_num in range(1, NUM_ITERATIONS + 1):
            output_dir = Path(OUTPUT_BASE_DIR) / "GPT" / dataset_name / project_name / class_name / str(bug_id) / f"Iteration_{iteration_num}"
            filename = f"{project_name}-{bug_id}-{class_name}-iter-{iteration_num}.txt"
            output_filepath = output_dir / filename

            if output_filepath.exists():
                print(f"{filename} already exists. Skipping...")
                continue

            print(f"Generating test suite for {filename}...")
            try:
                prompt = generate_test_prompt(class_name, source_code)
                generated_test_code = get_completion(prompt)

                if generated_test_code:
                    save_generated_test(output_dir, filename, generated_test_code)
                else:
                    log_error(filename, "Empty response from LLM.")
            except Exception as e:
                log_error(filename, f"Generation error: {e}")

    print(f"Test generation completed for dataset: {dataset_name}.")

# Resolve the dataset directory path relative to this script
SCRIPT_DIR = Path(__file__).resolve().parent
DATASET_BASE_PATH = SCRIPT_DIR / "DATASET"

datasets_info = [
    {"name": "Defects4J", "has_bug_id": True},
    {"name": "SF110", "has_bug_id": False}
]

for dataset_info in datasets_info:
    dataset_name = dataset_info["name"]
    has_bug_id = dataset_info["has_bug_id"]
    dataset_filename = f"{dataset_name}_unique_dataset_part3.json"
    dataset_path = DATASET_BASE_PATH / dataset_filename

    if not dataset_path.exists():
        print(f"Dataset file {dataset_path} does not exist. Skipping...")
        continue

    process_dataset(dataset_path, dataset_name, has_bug_id)

print("Test generation completed for all datasets.")
