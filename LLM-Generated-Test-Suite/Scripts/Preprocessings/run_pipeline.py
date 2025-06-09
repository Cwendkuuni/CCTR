import subprocess
import sys
from pathlib import Path

# === Script paths ===
SCRIPT_1 = Path("llm_syntax_analysis.py")
SCRIPT_2 = Path("reorganize_and_compile.py")
SCRIPT_3 = Path("copy_compilable_tests.py")

def run_step(step_number, description, script_path):
    print(f"\n[Step {step_number}] {description}")
    if not script_path.is_file():
        print(f"[Error] Script not found: {script_path}")
        sys.exit(1)

    try:
        subprocess.run(["python", str(script_path)], check=True)
        print(f"[OK] {description} completed.")
    except subprocess.CalledProcessError as e:
        print(f"[Failed] {description} failed with exit code {e.returncode}")
        sys.exit(e.returncode)

def main():
    print("=== LLM Test Processing Pipeline ===")

    run_step(1, "Syntax analysis and Java extraction", SCRIPT_1)
    run_step(2, "File reorganization and compilation", SCRIPT_2)
    run_step(3, "Copy of compilable tests", SCRIPT_3)

    print("\n[Done] All steps completed successfully.")

if __name__ == "__main__":
    main()
