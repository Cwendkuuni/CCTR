import os
import subprocess
import logging
from concurrent.futures import ThreadPoolExecutor, as_completed

# Logging setup
logging.basicConfig(level=logging.INFO, filename='pmd_llm_analysis.log',
                    format='%(asctime)s - %(levelname)s - %(message)s',
                    filemode='a')

def run_pmd(java_file, ruleset_path, report_path):
    """Execute PMD on a Java file with a given ruleset."""
    command = [
        "pmd", "check", "-d", java_file, "-R", ruleset_path, "-f", "xml", "-r", report_path
    ]
    try:
        subprocess.run(command, check=True)
        logging.info(f"PMD analysis completed: {report_path}")
    except subprocess.CalledProcessError as e:
        logging.error(f"PMD failed for {java_file}: {e}")

def analyze_dataset(dataset_name, dataset_path, complexities, report_base_path):
    """Recursively process all .java files for a dataset and run PMD analysis."""
    for root, _, files in os.walk(dataset_path):
        for file in files:
            if file.endswith(".java") and "scaffolding" not in file:
                java_file_path = os.path.join(root, file)
                parts = java_file_path.split(os.path.sep)

                try:
                    idx = parts.index(f"{dataset_name}-Compilable-OK")
                except ValueError:
                    logging.warning(f"Skipping unrecognized path: {java_file_path}")
                    continue

                try:
                    model = parts[idx + 1]
                    project = parts[idx + 2]
                    class_name = parts[idx + 3]
                    if dataset_name.lower() == "defects4j":
                        bug_id = parts[idx + 4]
                        iteration = parts[idx + 5]
                    else:
                        bug_id = None
                        iteration = parts[idx + 4]
                except IndexError:
                    logging.warning(f"Invalid structure in path: {java_file_path}")
                    continue

                class_file = os.path.splitext(os.path.basename(java_file_path))[0]
                class_iteration_id = f"{class_name}-{iteration.split('_')[-1]}"

                output_path_parts = [report_base_path, dataset_name.lower(), model, project]
                if bug_id:
                    output_path_parts.append(bug_id)
                output_path_parts.append(class_iteration_id)

                # Cyclomatic complexity
                cyclomatic_dir = os.path.join(*output_path_parts, "cyclomatic")
                os.makedirs(cyclomatic_dir, exist_ok=True)
                cyclomatic_filename = f"{dataset_name.lower()}_{model}_{project}_{bug_id + '_' if bug_id else ''}{class_name}_cyclomatic.xml"
                cyclomatic_report_path = os.path.join(cyclomatic_dir, cyclomatic_filename)
                run_pmd(java_file_path, complexities["cyclomatic"], cyclomatic_report_path)

                # Cognitive complexities
                for level, ruleset_path in complexities["cognitive"].items():
                    cognitive_dir = os.path.join(*output_path_parts, "cognitive", level)
                    os.makedirs(cognitive_dir, exist_ok=True)
                    cognitive_filename = f"{dataset_name.lower()}_{model}_{project}_{bug_id + '_' if bug_id else ''}{class_name}_cognitive_{level}.xml"
                    cognitive_report_path = os.path.join(cognitive_dir, cognitive_filename)
                    run_pmd(java_file_path, ruleset_path, cognitive_report_path)

def main():
    # PMD installation path
    pmd_bin_path = "pmd-bin-7.0.0-rc4/bin"
    os.environ["PATH"] += os.pathsep + pmd_bin_path

    # Datasets
    model_dataset_paths = {
        "Defects4J": "Defects4J-Compilable-OK",
        "SF110": "SF110-Compilable-OK"
    }

    # Ruleset paths
    base_ruleset_path = "RULESETS"
    complexities = {
        "cognitive": {
            "low": os.path.join(base_ruleset_path, "cognitive_low_ruleset.xml"),
            "moderate": os.path.join(base_ruleset_path, "cognitive_moderate_ruleset.xml"),
            "high": os.path.join(base_ruleset_path, "cognitive_high_ruleset.xml"),
            "very_high": os.path.join(base_ruleset_path, "cognitive_very_high_ruleset.xml")
        },
        "cyclomatic": os.path.join(base_ruleset_path, "cyclomatic_ruleset.xml")
    }

    # Output base path
    report_base_path = "."
    os.makedirs(report_base_path, exist_ok=True)

    # Run analyses in parallel
    with ThreadPoolExecutor() as executor:
        futures = [
            executor.submit(analyze_dataset, name, path, complexities, report_base_path)
            for name, path in model_dataset_paths.items()
        ]
        for future in as_completed(futures):
            try:
                future.result()
            except Exception as e:
                logging.error(f"Error during parallel processing: {e}")

if __name__ == "__main__":
    main()



