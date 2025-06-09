import os
import xml.etree.ElementTree as ET
import csv
import statistics

def extract_cognitive_complexity(file_path):
    """
    Extracts cognitive complexity values from a PMD XML report.
    """
    complexities = []

    try:
        tree = ET.parse(file_path)
        root = tree.getroot()
        namespaces = {'ns': 'http://pmd.sourceforge.net/report/2.0.0'}

        for violation in root.findall('.//ns:violation[@rule="CognitiveComplexity"]', namespaces):
            try:
                value = float(violation.text.strip().split()[-1])
                complexities.append(value)
            except ValueError:
                continue

    except ET.ParseError:
        return []

    return complexities

def aggregate_cognitive_complexity(report_base_path):
    """
    Aggregates cognitive complexity metrics by dataset, project, bug ID, class, and evaluation level.
    """
    aggregated = {}

    for root, _, files in os.walk(report_base_path):
        for file in files:
            if file.endswith('.xml'):
                file_path = os.path.join(root, file)
                complexities = extract_cognitive_complexity(file_path)

                if not complexities:
                    continue

                root_parts = root.split(os.path.sep)
                base_parts = report_base_path.rstrip(os.path.sep).split(os.path.sep)
                relative_parts = root_parts[len(base_parts):]

                dataset = relative_parts[0] if len(relative_parts) > 0 else ''
                project = relative_parts[1] if len(relative_parts) > 1 else ''
                bug_id = ''
                fqdn_class_iteration = ''
                level = ''

                if dataset == 'defects4j':
                    bug_id = relative_parts[2] if len(relative_parts) > 2 else ''
                    fqdn_class_iteration = relative_parts[3] if len(relative_parts) > 3 else ''
                    if 'cognitive' in relative_parts:
                        level_idx = relative_parts.index('cognitive') + 1
                        if level_idx < len(relative_parts):
                            level = relative_parts[level_idx]
                        else:
                            level = ''
                elif dataset == 'sf110':
                    fqdn_class_iteration = relative_parts[2] if len(relative_parts) > 2 else ''
                    if 'cognitive' in relative_parts:
                        level_idx = relative_parts.index('cognitive') + 1
                        if level_idx < len(relative_parts):
                            level = relative_parts[level_idx]

                class_name = fqdn_class_iteration.split('.')[-1].split('-')[0]

                key = (dataset, project, bug_id, class_name, level)
                if key not in aggregated:
                    aggregated[key] = {
                        'Total Complexity': 0,
                        'Total Classes': 0,
                        'Complexity Values': [],
                        'Min': float('inf'),
                        'Max': float('-inf')
                    }

                total = sum(complexities)
                aggregated[key]['Total Complexity'] += total
                aggregated[key]['Total Classes'] += 1
                aggregated[key]['Complexity Values'].extend(complexities)
                aggregated[key]['Min'] = min(aggregated[key]['Min'], min(complexities))
                aggregated[key]['Max'] = max(aggregated[key]['Max'], max(complexities))

    return aggregated

def save_cognitive_complexity_summary(aggregated, output_csv_path):
    """
    Saves the aggregated cognitive complexity statistics to a CSV file.
    """
    os.makedirs(os.path.dirname(output_csv_path), exist_ok=True)

    with open(output_csv_path, mode='w', newline='', encoding='utf-8') as file:
        fieldnames = [
            'Dataset', 'Project', 'Bug-ID', 'Class', 'Complexity Level', 'Total Classes',
            'Average Cognitive Complexity', 'Min Cognitive Complexity',
            'Max Cognitive Complexity', 'Median Cognitive Complexity',
            'Total Cognitive Complexity'
        ]
        writer = csv.DictWriter(file, fieldnames=fieldnames)
        writer.writeheader()

        for key, values in aggregated.items():
            dataset, project, bug_id, class_name, level = key
            complexities = values['Complexity Values']
            writer.writerow({
                'Dataset': dataset,
                'Project': project,
                'Bug-ID': bug_id,
                'Class': class_name,
                'Complexity Level': level,
                'Total Classes': values['Total Classes'],
                'Average Cognitive Complexity': statistics.mean(complexities),
                'Min Cognitive Complexity': values['Min'],
                'Max Cognitive Complexity': values['Max'],
                'Median Cognitive Complexity': statistics.median(complexities),
                'Total Cognitive Complexity': values['Total Complexity']
            })

def main():
    report_base_path = "/reports"
    output_csv_path = "cognitive_analysis_summary.csv"

    aggregated_data = aggregate_cognitive_complexity(report_base_path)
    save_cognitive_complexity_summary(aggregated_data, output_csv_path)

    print("Cognitive complexity analysis completed.")
    print(f"Results saved to: {output_csv_path}")

if __name__ == "__main__":
    main()
