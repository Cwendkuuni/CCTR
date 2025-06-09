import os
import logging
from concurrent.futures import ThreadPoolExecutor, as_completed
import subprocess
import csv
import statistics

# Logging configuration
logging.basicConfig(
    level=logging.INFO,
    filename='readability_analysis.log',
    format='%(asctime)s - %(levelname)s - %(message)s',
    filemode='a'
)

# Paths to RSM jar and classifier
RSM_DIR = 'Readability-model'
RSM_JAR = os.path.join(RSM_DIR, 'rsm.jar')

def analyze_file(java_file_path):
    """Runs rsm.jar on a Java file and returns the readability score."""
    cmd = ['java', '-jar', RSM_JAR, java_file_path]
    result = subprocess.run(
        cmd, cwd=RSM_DIR,
        stdout=subprocess.PIPE, stderr=subprocess.PIPE,
        text=True
    )
    if result.returncode != 0:
        logging.error(f"[Readability] {java_file_path} → ERR: {result.stderr.strip()}")
        return None

    for line in result.stdout.splitlines():
        if '\t' not in line:
            continue
        fname, score_str = map(str.strip, line.split('\t', 1))
        if os.path.basename(fname) == os.path.basename(java_file_path):
            try:
                score = float(score_str)
                logging.info(f"[Readability] {java_file_path} → {score}")
                return score
            except ValueError:
                logging.error(f"[Readability] Invalid float '{score_str}' in {java_file_path}")
                return None

    logging.error(f"[Readability] No score found for {java_file_path}")
    return None

def extract_metadata(java_file_path, base_root):
    """
    Extracts metadata: dataset, model, project, bug_id, class_name.
    """
    parts = java_file_path.split(os.sep)
    idx = next(i for i, p in enumerate(parts) if p.endswith('-Compilable-OK'))
    dataset = parts[idx].split('-')[0]
    model = parts[idx + 1]
    project = parts[idx + 2]
    class_dir = parts[idx + 3]
    bug_id = parts[idx + 4] if dataset == 'Defects4J' else ''
    class_name = os.path.splitext(os.path.basename(java_file_path))[0]
    return dataset, model, project, bug_id, class_name

def analyze_dataset(base_root, detailed, aggregated):
    """
    Traverses all .java files under base_root and computes readability scores.
    """
    for root, _, files in os.walk(base_root):
        for filename in files:
            if not filename.endswith('.java') or 'scaffolding' in filename:
                continue

            file_path = os.path.join(root, filename)
            dataset, model, project, bug_id, class_name = extract_metadata(file_path, base_root)
            score = analyze_file(file_path)
            if score is None:
                continue

            # Detailed results
            detailed.append({
                'Dataset': dataset,
                'Model': model,
                'Project': project,
                'Bug-ID': bug_id,
                'Class': class_name,
                'File': file_path,
                'Readability': score
            })

            # Aggregated results by (dataset, model, project)
            key = (dataset, model, project)
            entry = aggregated.setdefault(key, {
                'TotalFiles': 0,
                'TotalScore': 0.0,
                'Scores': [],
                'Min': float('inf'),
                'Max': float('-inf'),
            })
            entry['TotalFiles'] += 1
            entry['TotalScore'] += score
            entry['Scores'].append(score)
            entry['Min'] = min(entry['Min'], score)
            entry['Max'] = max(entry['Max'], score)

def save_detailed_csv(detailed, output_csv):
    os.makedirs(os.path.dirname(output_csv), exist_ok=True)
    with open(output_csv, 'w', newline='', encoding='utf-8') as f:
        writer = csv.DictWriter(f, fieldnames=[
            'Dataset', 'Model', 'Project', 'Bug-ID', 'Class', 'File', 'Readability'
        ])
        writer.writeheader()
        writer.writerows(detailed)

def save_summary_csv(aggregated, output_csv):
    os.makedirs(os.path.dirname(output_csv), exist_ok=True)
    with open(output_csv, 'w', newline='', encoding='utf-8') as f:
        writer = csv.DictWriter(f, fieldnames=[
            'Dataset', 'Model', 'Project', 'Total Files',
            'Average', 'Min', 'Max', 'Median', 'Total Score'
        ])
        writer.writeheader()
        for (dataset, model, project), stats in aggregated.items():
            writer.writerow({
                'Dataset': dataset,
                'Model': model,
                'Project': project,
                'Total Files': stats['TotalFiles'],
                'Average': statistics.mean(stats['Scores']),
                'Min': stats['Min'],
                'Max': stats['Max'],
                'Median': statistics.median(stats['Scores']),
                'Total Score': stats['TotalScore']
            })

def main():
    if not os.path.isfile(RSM_JAR):
        logging.error(f"Missing rsm.jar at {RSM_JAR}")
        return

    base_paths = [
        'Defects4J-Compilable-OK/GPT',
        'Defects4J-Compilable-OK/MISTRAL',
        'SF110-Compilable-OK/GPT',
        'SF110-Compilable-OK/MISTRAL',
    ]

    detailed_results = []
    aggregated_results = {}

    with ThreadPoolExecutor() as executor:
        futures = [executor.submit(analyze_dataset, path, detailed_results, aggregated_results) for path in base_paths]
        for future in as_completed(futures):
            if future.exception():
                logging.error(f"Thread error: {future.exception()}")

    save_detailed_csv(
        detailed_results,
        'readability_detailed.csv'
    )
    save_summary_csv(
        aggregated_results,
        'readability_summary.csv'
    )
    print("Analysis complete.")

if __name__ == '__main__':
    main()
