import os
import subprocess
import xml.etree.ElementTree as ET
import re
import csv
from pathlib import Path
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
RSM_DIR = os.path.join(SCRIPT_DIR, "readability_model")
RSM_JAR = os.path.join(RSM_DIR, "rsm.jar")

OUTPUT_CSV = os.path.join(SCRIPT_DIR, "outputs", "complexity_summary.csv")
os.makedirs(os.path.dirname(OUTPUT_CSV), exist_ok=True)

# === Sonar Cognitive Complexity Calculator ===
class CognitiveComplexityCalculatorSonar:
    def __init__(self, code, method_name=None):
        self.code = code.encode("utf-8")
        self.complexity = 0
        self.nesting_level = 0
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
        self.complexity += 1 + self.nesting_level
        self.nesting_level += 1
        self._analyze_node(node)
        self.nesting_level -= 1

    def _is_recursive_call(self, text):
        return self.method_name and self.method_name in text

# === Test-Aware Cognitive Complexity Calculator ===
class CognitiveComplexityCalculatorTestAware:
    def __init__(self, code, method_name=None):
        self.code = code.encode("utf-8")
        self.method_name = method_name
        self.complexity = 0
        self.nesting_level = 0
        self.parser = parser

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
            elif kind == "method_invocation":
                if self._is_recursive_call(text):
                    self.complexity += 1
                if any(x in text for x in ["mock(", "when(", "verify("]):
                    self.complexity += 1
                if "assert" in text or "fail(" in text:
                    self.complexity += 1
            elif kind == "annotation":
                if "@Test" in text:
                    self.complexity += 1
                elif "@ParameterizedTest" in text:
                    self.complexity += 2
                elif "@BeforeEach" in text or "@AfterEach" in text:
                    self.complexity += 1
            self._analyze_node(child)

    def _increment(self, node):
        self.complexity += 1 + self.nesting_level
        self.nesting_level += 1
        self._analyze_node(node)
        self.nesting_level -= 1

    def _is_recursive_call(self, text):
        return self.method_name and self.method_name in text

# === PMD Execution and Parsing ===
def run_pmd(java_file, ruleset, report_file):
    command = [PMD_BIN, "check", "-d", java_file, "-R", ruleset, "-f", "xml", "-r", report_file]
    subprocess.run(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE)

def extract_complexity_value(text, rule_name):
    pattern = r"cognitive complexity of (\d+)" if rule_name == "CognitiveComplexity" else r"cyclomatic complexity of (\d+)"
    match = re.search(pattern, text)
    return int(match.group(1)) if match else 0

def extract_pmd_complexity(report_file, rule_name):
    try:
        tree = ET.parse(report_file)
        root = tree.getroot()
        violations = root.findall('.//{http://pmd.sourceforge.net/report/2.0.0}violation')
        values = [extract_complexity_value(v.text.strip(), rule_name)
                  for v in violations if v.attrib.get('rule') == rule_name]
        return sum(values)
    except Exception:
        return 0

# === Readability Score via Scalabrino RSM ===
def compute_readability_score(java_file_path):
    try:
        abs_path = os.path.abspath(java_file_path)
        result = subprocess.run(['java', '-jar', 'rsm.jar', abs_path],
                                cwd=RSM_DIR, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)
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

    sonar = CognitiveComplexityCalculatorSonar(code).compute_complexity()
    testaware = CognitiveComplexityCalculatorTestAware(code).compute_complexity()

    tmp_cog = "tmp_pmd_cog.xml"
    tmp_cyclo = "tmp_pmd_cyclo.xml"
    run_pmd(file_path, RULESET_COG, tmp_cog)
    run_pmd(file_path, RULESET_CYCLO, tmp_cyclo)

    pmd_cog = extract_pmd_complexity(tmp_cog, "CognitiveComplexity")
    cyclo = extract_pmd_complexity(tmp_cyclo, "CyclomaticComplexity")
    readability = compute_readability_score(file_path)

    return {
        "file": os.path.basename(file_path),
        "sonar": sonar,
        "testaware": testaware,
        "pmd_cog": pmd_cog,
        "cyclo": cyclo,
        "readability": readability if readability is not None else "N/A"
    }

# === Scan and Process All Java Files in Directories ===
def scan_and_analyze(base_dirs):
    results = []
    for base_dir in base_dirs:
        for root, _, files in os.walk(base_dir):
            for f in files:
                if f.endswith(".java") and "scaffolding" not in f:
                    path = os.path.join(root, f)
                    result = process_java_file(path)
                    results.append(result)
    return results

# === Display and Save Results ===
def display_results(results):
    print(f"{'File':<40} | {'Sonar':>5} | {'TestAware':>9} | {'PMD Cog':>8} | {'Cyclo':>6} | {'Readability':>11}")
    print("-" * 90)
    for r in results:
        print(f"{r['file']:<40} | {r['sonar']:>5} | {r['testaware']:>9} | {r['pmd_cog']:>8} | {r['cyclo']:>6} | {r['readability']:>11}")

def save_csv(results, path):
    with open(path, "w", newline="", encoding="utf-8") as f:
        writer = csv.writer(f)
        writer.writerow(["File", "Sonar", "TestAware", "PMD_Cog", "Cyclo", "Readability"])
        for r in results:
            writer.writerow([r['file'], r['sonar'], r['testaware'], r['pmd_cog'], r['cyclo'], r['readability']])

# === Main Execution ===
if __name__ == "__main__":
    base_dirs = ["Deeply_Nested_Trivial_Code", "EvoSuite_Generated", "GPT_Generated"]
    results = scan_and_analyze(base_dirs)
    display_results(results)
    save_csv(results, OUTPUT_CSV)
    print(f"\nResults saved to {OUTPUT_CSV}")
