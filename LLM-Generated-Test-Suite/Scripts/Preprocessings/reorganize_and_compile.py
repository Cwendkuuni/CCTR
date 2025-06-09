import os
import shutil
import subprocess
import csv
import re
from collections import defaultdict

BASE_DIR = "LLM-GENERATED-PROCESSED"
OUTPUT_DIR = "LLM-GENERATED-PROCESSED-REORG"

SOURCES_DEFECTS4J = "DEFECTS4J/SOURCES"
SOURCES_SF110 = "SF-110/SOURCES"

JUNIT_PATH = "JUNIT-HAMCREST/junit4.jar"
HAMCREST_PATH = "JUNIT-HAMCREST/hamcrest-core.jar"
SLF4J_PATH = "slf4j-api-1.6.4.jar"
DEPENDENCY_DIR = "JARS/dependencies"

DETAILED_CSV = "llmgen_compilation_stats_detailed.csv"
SUMMARY_CSV = "llmgen_compilation_stats_summary.csv"
ERROR_CSV = "llmgen_compilation_error_types_summary.csv"
PACKAGE_STATS_CSV = "llmgen_package_injection_summary.csv"

def find_fqdn(dataset, project, clazz, bug_or_iter):
    base_source = SOURCES_DEFECTS4J if dataset == "Defects4J" else SOURCES_SF110
    src_path = os.path.join(base_source, project, bug_or_iter) if dataset == "Defects4J" else os.path.join(base_source, project)
    for root, _, files in os.walk(src_path):
        if f"{clazz}.java" in files:
            return os.path.relpath(root, src_path)
    return None

def ensure_package(java_path, fqdn):
    with open(java_path, 'r', encoding='utf-8') as f:
        content = f.read()
    content = re.sub(r'^\s*package\s+[\w\.]+;\s*', '', content, count=1, flags=re.MULTILINE)
    package_line = f"package {fqdn.replace(os.sep, '.')};\n\n"
    with open(java_path, 'w', encoding='utf-8') as f:
        f.write(package_line + content)

def reorganize_files():
    stats = defaultdict(lambda: defaultdict(int))
    for model in os.listdir(BASE_DIR):
        for dataset in os.listdir(os.path.join(BASE_DIR, model)):
            for project in os.listdir(os.path.join(BASE_DIR, model, dataset)):
                for clazz in os.listdir(os.path.join(BASE_DIR, model, dataset, project)):
                    path = os.path.join(BASE_DIR, model, dataset, project, clazz)
                    for bug_or_iter in os.listdir(path):
                        bug_path = os.path.join(path, bug_or_iter)
                        if dataset == "Defects4J":
                            for iteration in os.listdir(bug_path):
                                iter_path = os.path.join(bug_path, iteration)
                                fqdn = find_fqdn(dataset, project, clazz, bug_or_iter) or ""
                                target = os.path.join(OUTPUT_DIR, model, dataset, project, clazz, bug_or_iter, iteration, fqdn)
                                os.makedirs(target, exist_ok=True)
                                for f in os.listdir(iter_path):
                                    if f.endswith(".java"):
                                        shutil.copy2(os.path.join(iter_path, f), os.path.join(target, f))
                                        if fqdn:
                                            ensure_package(os.path.join(target, f), fqdn)
                                            stats[model][dataset] += 1
                        else:
                            iter_path = bug_path
                            fqdn = find_fqdn(dataset, project, clazz, None) or ""
                            target = os.path.join(OUTPUT_DIR, model, dataset, project, clazz, bug_or_iter, fqdn)
                            os.makedirs(target, exist_ok=True)
                            for f in os.listdir(iter_path):
                                if f.endswith(".java"):
                                    shutil.copy2(os.path.join(iter_path, f), os.path.join(target, f))
                                    if fqdn:
                                        ensure_package(os.path.join(target, f), fqdn)
                                        stats[model][dataset] += 1

    with open(PACKAGE_STATS_CSV, 'w', newline='', encoding='utf-8') as f:
        writer = csv.writer(f)
        writer.writerow(["Model", "Dataset", "Package_Injections"])
        for model, datasets in stats.items():
            for dataset, count in datasets.items():
                writer.writerow([model, dataset, count])

def compile_files():
    detailed = []
    summary = defaultdict(lambda: defaultdict(lambda: defaultdict(lambda: {"total": 0, "success": 0, "failure": 0})))
    error_types = defaultdict(lambda: defaultdict(lambda: defaultdict(lambda: defaultdict(int))))

    for root, _, files in os.walk(OUTPUT_DIR):
        java_files = [os.path.join(root, f) for f in files if f.endswith(".java")]
        if not java_files:
            continue

        parts = os.path.relpath(root, OUTPUT_DIR).split(os.sep)
        if len(parts) < 6:
            continue

        model, dataset = parts[0], parts[1]
        project, clazz = parts[2], parts[3]
        bug_id, iteration = parts[4], parts[5] if dataset == "Defects4J" else ("N/A", parts[4])
        src_dir = os.path.join(SOURCES_DEFECTS4J, project, bug_id) if dataset == "Defects4J" else os.path.join(SOURCES_SF110, project)

        summary[dataset][model][iteration]["total"] += 1

        cp = f".:{src_dir}:{JUNIT_PATH}:{HAMCREST_PATH}:{SLF4J_PATH}:{DEPENDENCY_DIR}/*"
        cmd = ["javac", "-cp", cp] + java_files

        try:
            subprocess.run(cmd, check=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
            summary[dataset][model][iteration]["success"] += 1
            detailed.append([dataset, model, project, clazz, bug_id, iteration, "Success", ""])
        except subprocess.CalledProcessError as e:
            msg = e.stderr.decode("utf-8").splitlines()[0] if e.stderr else "Unknown"
            err_type = msg.split(":")[0].strip()
            error_types[dataset][model][iteration][err_type] += 1
            summary[dataset][model][iteration]["failure"] += 1
            detailed.append([dataset, model, project, clazz, bug_id, iteration, "Failed", msg])

    with open(DETAILED_CSV, 'w', newline='', encoding='utf-8') as f:
        w = csv.writer(f)
        w.writerow(["Dataset", "Model", "Project", "Class", "Bug-ID", "Iteration", "Status", "Error_Message"])
        w.writerows(detailed)

    with open(SUMMARY_CSV, 'w', newline='', encoding='utf-8') as f:
        w = csv.writer(f)
        w.writerow(["Dataset", "Model", "Iteration", "Total", "Success", "Failure"])
        for ds, models in summary.items():
            for model, iterations in models.items():
                for it, stats in iterations.items():
                    w.writerow([ds, model, it, stats["total"], stats["success"], stats["failure"]])

    with open(ERROR_CSV, 'w', newline='', encoding='utf-8') as f:
        w = csv.writer(f)
        w.writerow(["Dataset", "Model", "Iteration", "Error_Type", "Count"])
        for ds, models in error_types.items():
            for model, iters in models.items():
                for it, etypes in iters.items():
                    for err, count in etypes.items():
                        w.writerow([ds, model, it, err, count])

def main():
    print("Step 2: Reorganizing Java files with package injection...")
    reorganize_files()
    print("Step 2: Compilation started...")
    compile_files()
    print("Step 2 complete: Compilation finished.")

if __name__ == "__main__":
    main()
