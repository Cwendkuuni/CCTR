import os
import logging
import csv
import statistics
from concurrent.futures import ThreadPoolExecutor, as_completed
from tree_sitter import Language, Parser
import tree_sitter_java as tsjava

# Initialize logging
logging.basicConfig(level=logging.INFO, filename='cognitive_analysis_llm.log',
                    format='%(asctime)s - %(levelname)s - %(message)s',
                    filemode='a')

# Tree-sitter parser setup
JAVA_LANGUAGE = Language(tsjava.language())
parser = Parser(JAVA_LANGUAGE)


# Cognitive complexity calculator
class CognitiveComplexityCalculator:
    def __init__(self, code):
        self.code = code.encode("utf-8")
        self.complexity = 0
        self.nest_level = 0
        self.parser = parser

    def compute_complexity(self):
        tree = self.parser.parse(self.code)
        self._analyze_node(tree.root_node)
        return self.complexity

    def _analyze_node(self, node):
        for child in node.children:
            child_text = self.code[child.start_byte:child.end_byte].decode("utf-8", errors="ignore")

            if child.type in ["if_statement", "for_statement", "while_statement", "switch_statement"]:
                self.complexity += 1 + self.nest_level
                self.nest_level += 1
                self._analyze_node(child)
                self.nest_level -= 1
            elif child.type == "annotation":
                if "@Test" in child_text:
                    self.complexity += 1
                elif "@ParameterizedTest" in child_text:
                    self.complexity += 2
            elif child.type == "expression_statement":
                if "assert" in child_text:
                    self.complexity += 1
                elif "throw" in child_text or "catch" in child_text:
                    self.complexity += 1
            elif child.type == "method_invocation":
                self.complexity += child_text.count(".")
            else:
                self._analyze_node(child)

class CognitiveComplexityCalculatorTestAware:
    def __init__(self, code, method_name=None):
        self.code = code.encode("utf-8")
        self.method_name = method_name
        self.complexity = 0
        self.nest_level = 0
        self.parser = parser


    def compute_complexity(self):
        tree = self.parser.parse(self.code)
        root_node = tree.root_node
        self._analyze_node(root_node)
        return self.complexity

    def _analyze_node(self, node):
        for child in node.children:
            kind = child.type
            text = self.code[child.start_byte:child.end_byte].decode("utf-8")

            # Base: Sonar-style control flow + nesting
            if kind in ["if_statement", "for_statement", "while_statement", "do_statement", "switch_statement", "catch_clause"]:
                self._increment("control", child)
            elif kind == "binary_expression" and ("&&" in text or "||" in text):
                self.complexity += 1
            elif kind == "labeled_statement":
                if any(k in text for k in ["break", "continue", "goto"]):
                    self.complexity += 1
            elif kind == "method_invocation":
                if self._is_recursive_call(text):
                    self.complexity += 1
                if any(x in text for x in ["mock(", "when(", "verify("]):
                    self.complexity += 1  # mocking complexity
                if "assert" in text or "fail(" in text:
                    self.complexity += 1  # assertion logic
            elif kind == "annotation":
                if "@Test" in text:
                    self.complexity += 1
                elif "@ParameterizedTest" in text:
                    self.complexity += 2
                elif "@BeforeEach" in text or "@AfterEach" in text:
                    self.complexity += 1
            # Continue recursion
            self._analyze_node(child)

    def _increment(self, kind, node):
        self.complexity += 1 + self.nest_level
        self.nest_level += 1
        self._analyze_node(node)
        self.nest_level -= 1

    def _is_recursive_call(self, text):
        return self.method_name and self.method_name in text


class CognitiveComplexityCalculatorSonar:
    def __init__(self, code, method_name=None):
        self.code = code.encode("utf-8")
        self.complexity = 0
        self.nest_level = 0
        self.parser = parser
        self.method_name = method_name

    def compute_complexity(self):
        tree = self.parser.parse(self.code)
        root_node = tree.root_node
        self._analyze_node(root_node)
        return self.complexity

    def _analyze_node(self, node):
        for child in node.children:
            kind = child.type
            text = self.code[child.start_byte:child.end_byte].decode("utf-8")

            # Core control flow constructs
            if kind in ["if_statement", "for_statement", "while_statement", "do_statement", "switch_statement", "catch_clause"]:
                self._increment("control", child)
            elif kind == "binary_expression" and ("&&" in text or "||" in text):
                self.complexity += 1  # sequence of logical operators
            elif kind == "labeled_statement":
                if "break" in text or "continue" in text or "goto" in text:
                    self.complexity += 1  # labeled jumps
            elif kind == "method_invocation":
                if self._is_recursive_call(text):
                    self.complexity += 1  # recursion
            # Recurse deeper
            self._analyze_node(child)

    def _increment(self, kind, node):
        # +1 base increment + nesting penalty
        self.complexity += 1 + self.nest_level
        self.nest_level += 1
        self._analyze_node(node)
        self.nest_level -= 1

    def _is_recursive_call(self, invocation_text):
        if not self.method_name:
            return False
        # Basic check: does the method call contain the method name
        return self.method_name in invocation_text



# Analyze a single file
def analyze_file(java_file_path):
    try:
        with open(java_file_path, 'r', encoding='utf-8') as file:
            code = file.read()
        #calculator = CognitiveComplexityCalculator(code)
        calculator = CognitiveComplexityCalculatorSonar(code)
        #calculator = CognitiveComplexityCalculatorTestAware(code)
        return calculator.compute_complexity()
    except Exception as e:
        logging.error(f"Failed to analyze {java_file_path}: {e}")
        return None

# Extract info from path
def extract_info(java_file_path):
    parts = java_file_path.split(os.path.sep)
    try:
        if "Defects4J-Compilable-OK" in parts:
            idx = parts.index("Defects4J-Compilable-OK")
            dataset = "Defects4J"
        elif "SF110-Compilable-OK" in parts:
            idx = parts.index("SF110-Compilable-OK")
            dataset = "SF110"
        else:
            return None

        model = parts[idx + 1]
        project = parts[idx + 2]
        clazz = parts[idx + 3]
        if dataset == "Defects4J":
            bug_id = parts[idx + 4]
            iteration = parts[idx + 5]
        else:
            bug_id = None
            iteration = parts[idx + 4]

        filename = os.path.basename(java_file_path)
        return dataset, model, project, clazz, bug_id, iteration, filename
    except Exception as e:
        logging.error(f"Error extracting info from path {java_file_path}: {e}")
        return None

# Analyze a dataset root path
def analyze_dataset(dataset_path, detailed_data, aggregated_data):
    for root, _, files in os.walk(dataset_path):
        for file in files:
            if file.endswith(".java") and "scaffolding" not in file:
                java_path = os.path.join(root, file)
                info = extract_info(java_path)
                if not info:
                    continue
                dataset, model, project, clazz, bug_id, iteration, filename = info
                complexity = analyze_file(java_path)
                if complexity is None:
                    continue

                key = (dataset, model, project)
                detailed_data.append({
                    "Dataset": dataset,
                    "Model": model,
                    "Project": project,
                    "Class": clazz,
                    "Bug-ID": bug_id,
                    "Iteration": iteration,
                    "File": filename,
                    "Cognitive Complexity": complexity
                })

                if key not in aggregated_data:
                    aggregated_data[key] = {
                        "Total Complexity": 0,
                        "Total Classes": 0,
                        "Complexity Values": []
                    }

                aggregated_data[key]["Total Complexity"] += complexity
                aggregated_data[key]["Total Classes"] += 1
                aggregated_data[key]["Complexity Values"].append(complexity)

# Save detailed results
def save_detailed_csv(detailed_data, output_path):
    with open(output_path, 'w', newline='', encoding='utf-8') as f:
        writer = csv.DictWriter(f, fieldnames=detailed_data[0].keys())
        writer.writeheader()
        writer.writerows(detailed_data)

# Save summary results
def save_summary_csv(aggregated_data, output_path):
    with open(output_path, 'w', newline='', encoding='utf-8') as f:
        fieldnames = ["Dataset", "Model", "Project", "Total Classes",
                      "Average Cognitive Complexity", "Min", "Max", "Median", "Total"]
        writer = csv.DictWriter(f, fieldnames=fieldnames)
        writer.writeheader()
        for (dataset, model, project), stats in aggregated_data.items():
            values = stats["Complexity Values"]
            writer.writerow({
                "Dataset": dataset,
                "Model": model,
                "Project": project,
                "Total Classes": stats["Total Classes"],
                "Average Cognitive Complexity": statistics.mean(values),
                "Min": min(values),
                "Max": max(values),
                "Median": statistics.median(values),
                "Total": stats["Total Complexity"]
            })

# Paths
base_path = "Preprocessings"
output_dir = "."

detailed_output = os.path.join(output_dir, "llm-cognitive_analysis_detailed-Sonar.csv")
summary_output = os.path.join(output_dir, "llm-cognitive_analysis_summary-Sonar.csv")




# Main analysis
detailed_data = []
aggregated_data = {}

with ThreadPoolExecutor() as executor:
    futures = []
    for dataset_name in ["Defects4J-Compilable-OK", "SF110-Compilable-OK"]:
        dataset_path = os.path.join(base_path, dataset_name)
        futures.append(executor.submit(analyze_dataset, dataset_path, detailed_data, aggregated_data))

    for future in as_completed(futures):
        future.result()

# Save output
save_detailed_csv(detailed_data, detailed_output)
save_summary_csv(aggregated_data, summary_output)



