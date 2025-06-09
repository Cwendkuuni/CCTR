import os
import shutil

# === Source and target directories ===
REORG_DIR = "LLM-GENERATED-PROCESSED-REORG"
TARGET_DEFECTS4J = "Defects4J-Compilable-OK"
TARGET_SF110 = "SF110-Compilable-OK"

def copy_compilable_tests():
    total_checked = 0
    total_copied = 0

    for root, _, files in os.walk(REORG_DIR):
        java_files = [f for f in files if f.endswith(".java")]
        for java_file in java_files:
            class_file = java_file.replace(".java", ".class")
            if class_file in files:
                total_checked += 1
                rel_path = os.path.relpath(root, REORG_DIR)
                parts = rel_path.split(os.sep)

                if len(parts) < 3:
                    continue

                model = parts[0]         # GPT or MISTRAL
                dataset = parts[1]       # Defects4J or SF110

                if dataset == "Defects4J":
                    dest_root = os.path.join(TARGET_DEFECTS4J, model, *parts[2:])
                elif dataset == "SF110":
                    dest_root = os.path.join(TARGET_SF110, model, *parts[2:])
                else:
                    continue

                os.makedirs(dest_root, exist_ok=True)

                for f in files:
                    if f.endswith(".java") or f.endswith(".class"):
                        src_file = os.path.join(root, f)
                        dst_file = os.path.join(dest_root, f)
                        shutil.copy2(src_file, dst_file)

                print(f"Copied: {java_file} → {dest_root}")
                total_copied += 1

    print("\nStep 3 complete: Copying compilable tests.")
    print(f"Total compilable classes detected: {total_checked}")
    print(f"Total successfully copied: {total_copied}")

if __name__ == "__main__":
    copy_compilable_tests()
