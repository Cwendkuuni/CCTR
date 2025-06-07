# Test-Aware Cognitive Complexity (CCTR) — Motivating Example

This folder contains the implementation of our **Test-Aware Cognitive Complexity (CCTR)** metric and a reproduction of the illustration examples from Section V-D of our paper:

---

## Objective

We demonstrate that traditional complexity metrics—such as SonarSource’s Cognitive Complexity and PMD’s implementation—fail to reflect the structural and semantic effort required to understand unit tests.

We introduce **CCTR**, a lightweight, test-specific metric that accounts for:

- Control-flow nesting (Sonar-inspired)
- Assertion density (`assert`, `fail`)
- Mocking constructs (`mock`, `verify`, `when`)
- Annotation semantics (`@Test`, `@ParameterizedTest`, etc.)

---

## Folder Structure

```
Approach/
├── Deeply_Nested_Trivial_Code/      # Manual nested example
├── EvoSuite_Generated/              # EvoSuite test suite
├── GPT_Generated/                   # GPT-4o test suite
├── rulesets/                        # PMD rulesets (cognitive.xml, cyclomatic.xml)
├── readability_model/               # rsm.jar + model files
├── pmd-bin-7.0.0-rc4/               # PMD CLI tools
├── compare_complexity_all.py        # Main script (CCTR + PMD + Readability)
└── outputs/
    └── complexity_summary.csv       # Results: Sonar, PMD, CCTR, Readability
```

---

## Test Cases

We evaluate two test suites with one deeply nested dummy example introduced in the paper:

| File                     | Origin     | Description                           |
|--------------------------|------------|---------------------------------------|
| `ExampleTest.java`       | Manual     | Deep nesting with trivial logic       |
| `CommandLineTest.java`   | GPT-4o     | Semantically structured test suite    |
| `CommandLine_ESTest.java`| EvoSuite   | Fragmented, synthetic test class      |

---

## Metrics Computed

| Metric         | Source               | Purpose                                           |
|----------------|----------------------|---------------------------------------------------|
| **Sonar**      | Tree-sitter AST      | Baseline cognitive complexity                     |
| **PMD Cog**    | PMD 7.0.0-rc4        | Cognitive complexity via XML ruleset              |
| **Cyclomatic** | PMD                  | McCabe’s control-flow complexity                  |
| **Readability**| Scalabrino et al.    | Surface-level readability using ML (rsm.jar)      |
| **CCTR (ours)**| Tree-sitter + Heuristics | Test-aware structural and semantic effort     |

---

## Running the Analysis

Requirements:

- Python ≥ 3.8
- Java ≥ 11

Install Python dependencies:

```bash
pip install -r requirements.txt
```

Run the analysis:

```bash
python3 compare_complexity_all.py
```

This will output the following file:

```
outputs/complexity_summary.csv
```

And print a summary like:

```
File                          | Sonar | TestAware | PMD Cog | Cyclo | Readability
------------------------------------------------------------------------------------
ExampleTest.java              |   12  |     12    |    6    |   4   | 0.95
CommandLine_ESTest.java       |    0  |     35    |    0    |  17   | 0.54
CommandLineTest.java          |    0  |     12    |    0    |  13   | 0.77
```

---

## CCTR Metric

CCTR is computed as:

```
CCTR = N + A + M + T
```

Where:

- **N** = Nesting-based control-flow complexity (Sonar-style)
- **A** = Number of assertion statements (`assert`, `fail`)
- **M** = Number of mocking constructs (`mock`, `verify`, `when`)
- **T** = Annotation semantics (`@Test` = +1, `@ParameterizedTest` = +2, etc.)

All weights are fixed to 1.0 for simplicity and interpretability.

---

## References

- Scalabrino, S., Linares-Vásquez, M., Oliveto, R., & Poshyvanyk, D. (2018).  
  *A comprehensive model for code readability.*  
  Journal of Software: Evolution and Process, 30(6), e1958. Wiley.

- Grano, G., Scalabrino, S., Gall, H. C., & Oliveto, R. (2018).  
  *An empirical investigation on the readability of manual and generated test cases.*  
  In *Proceedings of the 26th Conference on Program Comprehension* (pp. 348–351).

- Winkler, D., Urbanke, P., & Ramler, R. (2024).  
  *Investigating the readability of test code.*  
  Empirical Software Engineering, 29(2), Article 53.
