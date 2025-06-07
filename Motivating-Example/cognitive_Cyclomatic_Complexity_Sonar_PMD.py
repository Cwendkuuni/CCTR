import os
import subprocess
import xml.etree.ElementTree as ET
import re
from tree_sitter import Language, Parser
import tree_sitter_java as tsjava

# === Tree-sitter Java Initialization ===
JAVA_LANGUAGE = Language(tsjava.language())
parser = Parser(JAVA_LANGUAGE)

# === Resource Paths ===
SCRIPT_DIR = os.path.dirname(os.path.abspath(__file__))
PMD_BIN = os.path.join(SCRIPT_DIR, "pmd-bin-7.0.0-rc4", "bin", "pmd")
RULESET_COG = os.path.join(SCRIPT_DIR, "rulesets", "cognitive_ruleset.xml")
RULESET_CYCLO = os.path.join(SCRIPT_DIR, "rulesets", "cyclomatic_ruleset.xml")
PMD_TMP_REPORT = "pmd_tmp.xml"
RSM_DIR = os.path.join(SCRIPT_DIR, "readability_model")
RSM_JAR = os.path.join(RSM_DIR, "rsm.jar")

# === Sonar Cognitive Complexity Implementation ===
class CognitiveComplexityCalculatorSonar:
    def __init__(self, code, method_name=None):
        self.code = code.encode("utf-8")
        self.complexity = 0
        self.nest_level = 0
        self.parser = parser
        self.method_name = method_name

    def compute_complexity(self):
        tree = self.parser.parse(self.code)
        self._analyze_node(tree.root_node)
        return self.complexity

    def _analyze_node(self, node):
        for child in node.children:
            kind = child.type
            text = self.code[child.start_byte:child.end_byte].decode("utf-8")
            if kind in ["if_statement", "for_statement", "while_statement", "do_statement", "switch_statement", "catch_clause"]:
                self._increment(child)
            elif kind == "binary_expression" and ("&&" in text or "||" in text):
                self.complexity += 1
            elif kind == "labeled_statement" and any(k in text for k in ["break", "continue", "goto"]):
                self.complexity += 1
            elif kind == "method_invocation" and self._is_recursive_call(text):
                self.complexity += 1
            self._analyze_node(child)

    def _increment(self, node):
        self.complexity += 1 + self.nest_level
        self.nest_level += 1
        self._analyze_node(node)
        self.nest_level -= 1

    def _is_recursive_call(self, invocation_text):
        return self.method_name and self.method_name in invocation_text

# === PMD Analysis ===
def run_pmd(java_file, ruleset):
    command = [PMD_BIN, "check", "-d", java_file, "-R", ruleset, "-f", "xml", "-r", PMD_TMP_REPORT]
    subprocess.run(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE)

def extract_complexity_value(text, rule_name):
    pattern = r"cognitive complexity of (\d+)" if rule_name == "CognitiveComplexity" else r"cyclomatic complexity of (\d+)"
    match = re.search(pattern, text)
    return int(match.group(1)) if match else 0

def extract_pmd_complexity(rule_name):
    try:
        tree = ET.parse(PMD_TMP_REPORT)
        root = tree.getroot()
        violations = root.findall('.//{http://pmd.sourceforge.net/report/2.0.0}violation')
        values = [extract_complexity_value(v.text.strip(), rule_name)
                  for v in violations if v.attrib.get('rule') == rule_name]
        return sum(values), values
    except Exception:
        return 0.0, []

# === Readability Analysis using RSM ===
def compute_readability_score(java_file_path):
    try:
        abs_path = os.path.abspath(java_file_path)
        command = ['java', '-jar', 'rsm.jar', abs_path]

        result = subprocess.run(
            command,
            cwd=RSM_DIR,
            stdout=subprocess.PIPE,
            stderr=subprocess.PIPE,
            text=True
        )

        if result.returncode != 0:
            return None

        for line in result.stdout.strip().split('\n'):
            if '\t' in line and not line.startswith('file') and not line.startswith('[INFO]'):
                try:
                    _, score_str = line.strip().split('\t')
                    return float(score_str.strip())
                except ValueError:
                    continue
        return None
    except Exception:
        return None

# === Process a Single Java File ===
def process_java_file(file_path):
    with open(file_path, 'r', encoding='utf-8') as f:
        code = f.read()

    sonar_calc = CognitiveComplexityCalculatorSonar(code)
    sonar = sonar_calc.compute_complexity()

    run_pmd(file_path, RULESET_COG)
    pmd_cog, _ = extract_pmd_complexity("CognitiveComplexity")

    run_pmd(file_path, RULESET_CYCLO)
    cyclo, _ = extract_pmd_complexity("CyclomaticComplexity")

    readability = compute_readability_score(file_path)

    return {
        "file": os.path.basename(file_path),
        "sonar_cognitive": sonar,
        "pmd_cognitive": pmd_cog,
        "cyclomatic": cyclo,
        "readability": readability if readability is not None else "N/A"
    }

# === Process All Java Files in a Folder ===
def scan_directory_and_analyze(base_dir):
    results = []
    for root, _, files in os.walk(base_dir):
        for f in files:
            if f.endswith(".java") and "scaffolding" not in f:
                path = os.path.join(root, f)
                result = process_java_file(path)
                results.append(result)
    return results

# === Print Results in a Table ===
def display_results(results):
    print(f"{'File':<40} | {'Sonar':>6} | {'PMD Cog':>8} | {'Cyclo':>6} | {'Readability':>11}")
    print("-" * 85)
    for r in results:
        print(f"{r['file']:<40} | {r['sonar_cognitive']:>6} | {r['pmd_cognitive']:>8} | {r['cyclomatic']:>6} | {r['readability']:>11}")

# === Entry Point ===
if __name__ == "__main__":
    for folder in ["Deeply_Nested_Trivial_Code", "EvoSuite_Generated", "GPT_Generated"]:
        print(f"\nAnalyzing folder: {folder}")
        folder_results = scan_directory_and_analyze(folder)
        display_results(folder_results)



