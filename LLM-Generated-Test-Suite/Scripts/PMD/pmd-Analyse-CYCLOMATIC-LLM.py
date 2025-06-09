import os
import xml.etree.ElementTree as ET
import csv
import math
import statistics

def extract_metrics(file_path):
    """
    Parses a PMD XML report to extract cyclomatic complexity, LOC, and comment density.
    """
    complexities = []
    loc = 0
    comment_lines = 0
    java_file_path = None

    try:
        tree = ET.parse(file_path)
        root = tree.getroot()
        namespaces = {'ns': 'http://pmd.sourceforge.net/report/2.0.0'}
        file_elements = root.findall('.//ns:file', namespaces)

        if not file_elements:
            return 0, 0, 0, 0, [], None

        file_element = file_elements[0]
        java_file_path = file_element.get('name')

        if not java_file_path or not os.path.isfile(java_file_path):
            return 0, 0, 0, 0, [], None

        with open(java_file_path, 'r', encoding='utf-8') as java_file:
            for line in java_file:
                stripped = line.strip()
                if stripped:
                    loc += 1
                if stripped.startswith('//') or stripped.startswith('/*') or stripped.endswith('*/'):
                    comment_lines += 1

        for violation in file_element.findall('.//ns:violation[@rule="CyclomaticComplexity"]', namespaces):
            try:
                complexity = float(violation.text.strip().split()[-1])
                complexities.append(complexity)
            except ValueError:
                continue

    except ET.ParseError:
        return 0, 0, 0, 0, [], None

    total_complexity = sum(complexities)
    num_methods = len(complexities)
    comment_density = comment_lines / loc if loc > 0 else 0
    return total_complexity, loc, num_methods, comment_density, complexities, java_file_path

def calculate_maintainability_index(loc, total_complexity, comment_density):
    if loc > 0 and comment_density > 0:
        adjusted_loc = max(loc, 1)
        adjusted_comments = max(comment_density, 0.01)
        try:
            mi = 171 - 5.2 * math.log(adjusted_loc) - 0.23 * total_complexity - 16.2 * math.log(adjusted_comments)
            normalized_mi = (mi * 100) / 171
            return max(0, min(100, normalized_mi))
        except ValueError:
            return 0
    return 0

def parse_metadata_from_path(root, base_path):
    parts = root.split(os.path.sep)
    base_parts = base_path.rstrip(os.path.sep).split(os.path.sep)
    relative_parts = parts[len(base_parts):]

    if len(relative_parts) < 5:
        return None, None, None, None, None

    dataset = relative_parts[0]
    model = relative_parts[1]
    project = relative_parts[2]
    if dataset == 'defects4j':
        bug_id = relative_parts[3]
        class_dir = relative_parts[4]
    else:
        bug_id = ''
        class_dir = relative_parts[3]

    class_name = class_dir.split('-')[0]
    identifier = f"{dataset}_{model}_{project}_{bug_id + '_' if bug_id else ''}{class_name}"
    return dataset, model, project, bug_id, identifier

def aggregate_metrics(report_base_path):
    detailed = []
    aggregated = {}

    for root, _, files in os.walk(report_base_path):
        for file in files:
            if file.endswith('.xml') and 'cyclomatic' in root:
                file_path = os.path.join(root, file)
                total_complexity, loc, num_methods, comment_density, complexities, java_file_path = extract_metrics(file_path)
                maintainability_index = calculate_maintainability_index(loc, total_complexity, comment_density)

                if total_complexity > 0:
                    metadata = parse_metadata_from_path(root, report_base_path)
                    if not metadata:
                        continue
                    dataset, model, project, bug_id, identifier = metadata

                    detailed.append({
                        'Model': model,
                        'Dataset': dataset,
                        'Project': project,
                        'Class': identifier,
                        'Bug-ID': bug_id,
                        'Total Cyclomatic Complexity': total_complexity,
                        'LOC': loc,
                        'Number of Methods': num_methods,
                        'Comment Density': comment_density,
                        'Maintainability Index': maintainability_index
                    })

                    key = (model, dataset, project)
                    agg = aggregated.setdefault(key, {
                        'Total Complexity': 0,
                        'Total LOC': 0,
                        'Classes': 0,
                        'Indices': [],
                        'Min MI': float('inf'),
                        'Max MI': float('-inf'),
                        'Complexities': []
                    })

                    agg['Total Complexity'] += total_complexity
                    agg['Total LOC'] += loc
                    agg['Classes'] += 1
                    agg['Indices'].append(maintainability_index)
                    agg['Complexities'].append(total_complexity)
                    agg['Min MI'] = min(agg['Min MI'], maintainability_index)
                    agg['Max MI'] = max(agg['Max MI'], maintainability_index)

    return detailed, aggregated

def save_csv_outputs(detailed, aggregated, detailed_path, summary_path):
    os.makedirs(os.path.dirname(detailed_path), exist_ok=True)
    os.makedirs(os.path.dirname(summary_path), exist_ok=True)

    with open(detailed_path, 'w', newline='', encoding='utf-8') as f:
        writer = csv.DictWriter(f, fieldnames=[
            'Model', 'Dataset', 'Project', 'Class', 'Bug-ID',
            'Total Cyclomatic Complexity', 'LOC', 'Number of Methods',
            'Comment Density', 'Maintainability Index'
        ])
        writer.writeheader()
        writer.writerows(detailed)

    with open(summary_path, 'w', newline='', encoding='utf-8') as f:
        writer = csv.DictWriter(f, fieldnames=[
            'Model', 'Dataset', 'Project', 'Total Classes',
            'Average Maintainability Index', 'Min Maintainability Index', 'Max Maintainability Index',
            'Median Maintainability Index', 'Average Cyclomatic Complexity',
            'Min Cyclomatic Complexity', 'Max Cyclomatic Complexity', 'Median Cyclomatic Complexity',
            'Total Cyclomatic Complexity', 'Total LOC'
        ])
        writer.writeheader()
        for (model, dataset, project), values in aggregated.items():
            writer.writerow({
                'Model': model,
                'Dataset': dataset,
                'Project': project,
                'Total Classes': values['Classes'],
                'Average Maintainability Index': statistics.mean(values['Indices']),
                'Min Maintainability Index': values['Min MI'],
                'Max Maintainability Index': values['Max MI'],
                'Median Maintainability Index': statistics.median(values['Indices']),
                'Average Cyclomatic Complexity': statistics.mean(values['Complexities']),
                'Min Cyclomatic Complexity': min(values['Complexities']),
                'Max Cyclomatic Complexity': max(values['Complexities']),
                'Median Cyclomatic Complexity': statistics.median(values['Complexities']),
                'Total Cyclomatic Complexity': values['Total Complexity'],
                'Total LOC': values['Total LOC']
            })

if __name__ == "__main__":
    base_path = "."
    detailed_csv = os.path.join(base_path, "maintainability_detailed.csv")
    summary_csv = os.path.join(base_path, "maintainability_summary.csv")

    detailed_data, aggregated_data = aggregate_metrics(base_path)
    save_csv_outputs(detailed_data, aggregated_data, detailed_csv, summary_csv)

    print("Analysis complete.")
    print(f"- Detailed results: {detailed_csv}")
    print(f"- Summary results : {summary_csv}")
