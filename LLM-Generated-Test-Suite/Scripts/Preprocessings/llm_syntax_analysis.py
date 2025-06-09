import os
import re
import csv
from collections import defaultdict
from tree_sitter import Language, Parser
import tree_sitter_java as tsjava
import tiktoken

# === Initialization ===
JAVA_LANGUAGE = Language(tsjava.language())
parser = Parser(JAVA_LANGUAGE)
ENCODING = tiktoken.get_encoding("cl100k_base")

BASE_DIR = "Generated-TestSuite-output"
OUTPUT_DIR = "LLM-GENERATED-PROCESSED"
SYNTAX_CSV = "llm_syntax_statistics.csv"
ERRORS_CSV = "llm_syntax_errors.csv"
SUMMARY_CSV = "llm_summary_statistics.csv"

def extract_code(file_path):
    try:
        with open(file_path, 'r', encoding='utf-8') as f:
            content = f.read()
        return re.findall(r'```(?:java)?\s*(.*?)\s*```', content, re.DOTALL | re.IGNORECASE)
    except Exception as e:
        print(f"Read error in {file_path}: {e}")
        return []

def count_tokens(code):
    return len(ENCODING.encode(code))

def extract_methods_and_locs(node, code_bytes):
    methods, loc_count = [], 0
    if node.type == 'method_declaration':
        method_code = code_bytes[node.start_byte:node.end_byte].decode('utf8')
        methods.append(method_code)
        loc_count += len(method_code.splitlines())
    for child in node.children:
        child_methods, child_locs = extract_methods_and_locs(child, code_bytes)
        methods.extend(child_methods)
        loc_count += child_locs
    return methods, loc_count

def analyze_java_code(java_code):
    try:
        code_bytes = java_code.encode('utf8')
        tree = parser.parse(code_bytes)
        root = tree.root_node
        methods, locs = extract_methods_and_locs(root, code_bytes)
        tokens = count_tokens(java_code)
        return len(methods), locs, tokens
    except Exception:
        return 0, 0, 0

def is_syntax_correct(java_code):
    try:
        tree = parser.parse(java_code.encode('utf8'))
        return not tree.root_node.has_error, tree
    except Exception as e:
        return False, str(e)

def collect_syntax_errors(node, errors):
    if node.has_error:
        errors.append({"type": node.type, "start": node.start_point, "end": node.end_point})
    for child in node.children:
        collect_syntax_errors(child, errors)

def main():
    syntax_stats = []
    syntax_errors = []
    total_tests, syntax_ok_count = 0, 0
    summary_stats = defaultdict(lambda: {"total": 0, "correct": 0})

    for model in ["GPT", "MISTRAL"]:
        for dataset in ["Defects4J", "SF110"]:
            dataset_path = os.path.join(BASE_DIR, model, dataset)
            if not os.path.exists(dataset_path):
                continue
            for project in os.listdir(dataset_path):
                for clazz in os.listdir(os.path.join(dataset_path, project)):
                    for bug_or_null in os.listdir(os.path.join(dataset_path, project, clazz)):
                        for iteration in os.listdir(os.path.join(dataset_path, project, clazz, bug_or_null)):
                            iter_path = os.path.join(dataset_path, project, clazz, bug_or_null, iteration)
                            for filename in os.listdir(iter_path):
                                if not filename.endswith(".txt"):
                                    continue
                                code_blocks = extract_code(os.path.join(iter_path, filename))
                                if not code_blocks:
                                    continue
                                java_code = "\n".join(code_blocks)
                                correct, tree_or_err = is_syntax_correct(java_code)
                                method_count, loc, tokens = analyze_java_code(java_code) if correct else (0, 0, 0)

                                total_tests += 1
                                if correct:
                                    syntax_ok_count += 1

                                summary_stats[(model, dataset)]["total"] += 1
                                if correct:
                                    summary_stats[(model, dataset)]["correct"] += 1

                                syntax_stats.append({
                                    "Model": model, "Dataset": dataset, "Project": project,
                                    "Class": clazz, "Bug-ID": bug_or_null if dataset == "Defects4J" else "",
                                    "Iteration": iteration, "Filename": filename, "Syntax OK": correct,
                                    "Methods": method_count, "LOC": loc, "Tokens": tokens
                                })

                                if not correct and isinstance(tree_or_err, object):
                                    errors = []
                                    collect_syntax_errors(tree_or_err.root_node, errors)
                                    for err in errors:
                                        syntax_errors.append({
                                            "Model": model, "Dataset": dataset, "Project": project,
                                            "Class": clazz, "Bug-ID": bug_or_null if dataset == "Defects4J" else "",
                                            "Iteration": iteration, "Filename": filename,
                                            "Error Type": err["type"], "Start": err["start"], "End": err["end"]
                                        })

                                output_path = os.path.join(
                                    OUTPUT_DIR, model, dataset, project, clazz,
                                    bug_or_null if dataset == "Defects4J" else "",
                                    iteration
                                )
                                os.makedirs(output_path, exist_ok=True)
                                with open(os.path.join(output_path, f"{clazz}Test.java"), 'w', encoding='utf-8') as f:
                                    f.write(java_code)

    # Save outputs
    with open(SYNTAX_CSV, 'w', newline='', encoding='utf-8') as f:
        writer = csv.DictWriter(f, fieldnames=syntax_stats[0].keys())
        writer.writeheader()
        writer.writerows(syntax_stats)

    if syntax_errors:
        with open(ERRORS_CSV, 'w', newline='', encoding='utf-8') as f:
            writer = csv.DictWriter(f, fieldnames=syntax_errors[0].keys())
            writer.writeheader()
            writer.writerows(syntax_errors)

    with open(SUMMARY_CSV, 'w', newline='', encoding='utf-8') as f:
        writer = csv.DictWriter(f, fieldnames=["Model", "Dataset", "Total Tests", "Syntax Correct", "Syntax Incorrect", "Success Rate (%)"])
        writer.writeheader()
        for (model, dataset), values in summary_stats.items():
            total = values["total"]
            correct = values["correct"]
            incorrect = total - correct
            rate = f"{(correct / total * 100):.2f}" if total else "0.00"
            writer.writerow({
                "Model": model,
                "Dataset": dataset,
                "Total Tests": total,
                "Syntax Correct": correct,
                "Syntax Incorrect": incorrect,
                "Success Rate (%)": rate
            })

    print("Step 1 complete: Syntax analysis finished.")

if __name__ == "__main__":
    main()
