# Unified Analysis of Cognitive Complexity and Readability for Unit Tests

This repository contains the scripts and artifacts used in the motivating example of our paper, which highlights the limitations of general-purpose Cognitive Complexity metrics—particularly PMD's implementation based on SonarSource—when applied to unit test code.

We compare three test suites:

1. A **manually written nested example** (`ExampleTest.java`)
2. A **GPT-4o-generated test suite**
3. An **EvoSuite-generated test suite**

We analyze each suite using:

- **Sonar-style Cognitive Complexity**, implemented from scratch using Tree-sitter, based on the official whitepaper by SonarSource.
- **PMD's implementation** of Cognitive and Cyclomatic Complexity
- **Readability score** using the trained model from Scalabrino et al.

---

## Reproduction Steps

### Folder Structure

```
.
├── Deeply_Nested_Trivial_Code/
├── EvoSuite_Generated/
├── GPT_Generated/
├── cognitive_Cyclomatic_Complexity_Sonar_PMD.py
├── readability_model/             # Contains rsm.jar and readability.classifier
├── rulesets/                      # Contains PMD rulesets (cognitive + cyclomatic)
├── pmd-bin-7.0.0-rc4/             # PMD distribution
├── Cognitive_Complexity_Sonar_Guide_2023.pdf
```

### Dependencies

- Python ≥ 3.8
- Java ≥ 11
- PMD 7.0.0-rc4
- Tree-sitter with Java language binding

Install Python dependencies:

```bash
pip install -r requirements.txt
```

### Running the Analysis

```bash
python3 cognitive_Cyclomatic_Complexity_Sonar_PMD.py
```

This will print a summary table for each of the three test suites:

| File                   | Sonar | PMD Cog | Cyclo | Readability |
|------------------------|-------|---------|-------|-------------|
| ExampleTest.java       | 12    | 6       | 4     | 95.6        |
| CommandLineTest.java   | 0     | 0       | 13    | 77.6        |
| CommandLine_ESTest.java| 0     | 0       | 17    | 54.3        |

---

## Metric Details

### Cognitive Complexity (Sonar-inspired)

Our custom implementation strictly follows the official SonarSource Cognitive Complexity whitepaper:

`Cognitive_Complexity_Sonar_Guide_2023.pdf`

Key principles:

- +1 for each control-flow structure (if, for, while, catch, etc.)
- Nesting penalties (+1 per level of nesting)
- Extra cost for recursion, logical ops, ternaries, labels
- No penalty for return/break unless nested

We use Tree-sitter to parse Java ASTs and compute complexity scores according to SonarSource's algorithm.

### PMD Analysis

Cognitive and Cyclomatic Complexity are extracted via PMD using custom XML rulesets.

- The PMD implementation is inspired by SonarSource but differs in thresholds and some semantic granularity.
- Output is parsed automatically and integrated into unified reporting

### Readability (Scalabrino et al.)

Readability scores are computed using `rsm.jar` from Scalabrino et al.:

- Output range: **0.0 to 1.0**
- Higher = better readability
- Based on lexical, syntactic, and structural features

**Reference**:  
Scalabrino et al., *A comprehensive model for code readability*, Journal of Software: Evolution and Process, Wiley, 2018.

---

## Motivating Example

Section II of our paper presents three representative test suites and their complexity scores:

| Example                 | PMD Score | Perceived Complexity |
|-------------------------|-----------|-----------------------|
| Nested Trivial Code     | 12        | Low                   |
| GPT-Generated Test Suite| 0         | Medium                |
| EvoSuite Test Suite     | 0         | High                  |

This discrepancy illustrates the need for test-specific complexity metrics.

---

## Replication Notes

- All `.java` files are organized by source type in subfolders.
- Sonar-style complexity is computed via internal AST walking.
- PMD and readability (rsm.jar) are executed externally.
- Final results are normalized and reported in a unified summary.
