# CCTR: Test-Aware Cognitive Complexity for Unit Tests

This repository contains the complete implementation and evaluation datasets for our paper:

> **"Rethinking Cognitive Complexity for Unit Tests: Toward a Readability-Aware Metric Grounded in Developer Perception"**

We introduce **CCTR (Cognitive Complexity for Test Readability)**, a novel metric specifically designed to capture the structural and semantic complexity of unit test code, addressing the limitations of traditional complexity metrics when applied to test suites.

---

## Overview

Traditional complexity metrics like SonarSource's Cognitive Complexity and PMD's implementation fail to accurately reflect the cognitive effort required to understand unit tests. Our research demonstrates that:

- **Deeply nested trivial code** receives high complexity scores despite being simple to understand
- **Semantically complex test suites** (with mocking, assertions, parameterization) receive low scores
- **Generated test suites** often exhibit complexity patterns not captured by existing metrics

**CCTR** addresses these limitations by incorporating test-specific constructs:
- Control-flow nesting (Sonar-inspired)
- Assertion density (`assert`, `fail`, `assertEquals`)
- Mocking complexity (`mock`, `verify`, `when`)
- Test annotation semantics (`@Test`, `@ParameterizedTest`, etc.)

---

## Repository Structure

```
CCTR/
├── Approach/                           # Core CCTR implementation & motivating examples
│   ├── Deeply_Nested_Trivial_Code/     # Manual nested example from paper
│   ├── EvoSuite_Generated/             # EvoSuite-generated test suite
│   ├── GPT_Generated/                  # GPT-4o-generated test suite
│   ├── compare_complexity_all.py       # Main analysis script
│   ├── readability_model/              # Scalabrino et al. readability model
│   ├── rulesets/                       # PMD XML rulesets
│   └── outputs/                        # Analysis results
├── Dataset/                            # Research datasets
│   ├── Defects4J_dataset.json          # Defects4J test suite metadata
│   └── SF110_dataset.json              # SF110 dataset metadata
├── EvoSuite-Generated-Test-Suite/      # Complete EvoSuite evaluation
│   ├── Generated-Test-Suite/           # Generated test suites
│   ├── Generated-Test-Suite-Code/      # Raw generated code
│   └── Scripts/                        # Analysis scripts
├── LLM-Generated-Test-Suite/           # LLM-based test generation evaluation
│   ├── Generated-Test-Suite/           # GPT & Mistral generated suites
│   ├── Generated-Test-Suite-Code/      # Raw LLM-generated code
│   ├── Generated-Test-Suite-Compilable/# Compilable filtered suites
│   ├── Generated-Test-Suite-Raw/       # Unprocessed LLM outputs
│   └── Scripts/                        # PMD, preprocessing, readability scripts
├── Motivating-Example/                 # Paper's motivating example reproduction
├── Results/                            # Experimental results and analysis
└── README.md                           # This file
```

---

## Quick Start

### Prerequisites

- **Java 11+** (for PMD and readability analysis)
- **Python 3.8+** (for CCTR implementation)
- Unix-like environment (Linux, macOS, WSL)

### Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-repo/CCTR.git
   cd CCTR
   ```

2. **Install Python dependencies:**
   ```bash
   cd Approach/
   pip install -r requirements.txt
   ```

3. **Run the motivating example:**
   ```bash
   python3 compare_complexity_all.py
   ```

This will analyze the three test suites from our paper and generate:
```
outputs/complexity_summary.csv
```

### Expected Output

```
File                          | Sonar | CCTR  | PMD Cog | Cyclo | Readability
------------------------------------------------------------------------------------
ExampleTest.java              |   12  |   12  |    6    |   4   | 0.95
CommandLine_ESTest.java       |    0  |   35  |    0    |  17   | 0.54
CommandLineTest.java          |    0  |   12  |    0    |  13   | 0.77
```

---

## CCTR Metric

The **CCTR** metric is computed as:

```
CCTR = α·N + β·A + γ·M + δ·T
```

Where:
- **N** = Nesting-based control-flow complexity (Sonar-inspired)
- **A** = Assertion statement count (`assert*`, `fail`, `assertTrue`, etc.)
- **M** = Mocking construct count (`mock`, `verify`, `when`, `spy`)
- **T** = Test annotation semantics (`@Test` = +1, `@ParameterizedTest` = +2, etc.)

**Default weights:** α = β = γ = δ = 1.0 (for interpretability and simplicity)

### Key Features

**Test-aware:** Recognizes test-specific constructs  
**Lightweight:** Simple linear combination of four factors  
**Interpretable:** Each component has clear semantic meaning  
**Extensible:** Weights can be tuned based on empirical studies  

---

## Experiments & Evaluation

### 1. Motivating Example (`Motivating-Example/`)

Reproduces the examples from Section V-D of our paper, comparing:
- Manual deeply nested trivial code
- GPT-4o generated test suite
- EvoSuite generated test suite

**Run:** `cd Motivating-Example && python3 cognitive_Cyclomatic_Complexity_Sonar_PMD.py`

### 2. Large-Scale LLM Evaluation (`LLM-Generated-Test-Suite/`)

Evaluates CCTR on test suites generated by:
- **GPT-4o**
- **Mistral**

Includes preprocessing scripts for:
- Syntax validation and compilation checking
- PMD complexity analysis
- Readability assessment

### 3. EvoSuite Evaluation (`EvoSuite-Generated-Test-Suite/`)

Evaluation on automatically generated test suites using EvoSuite, comparing CCTR against traditional metrics.

### 4. Benchmark Datasets (`Dataset/`)

- **Defects4J:** Real-world Java projects with developer-written tests
- **SF110:** Comprehensive Java class dataset

---

## Results Summary

Our evaluation demonstrates that:

1. **CCTR correlates better with developer-perceived complexity** than traditional metrics
2. **Traditional metrics underestimate complexity** of semantically rich test suites
3. **CCTR provides more balanced scoring** across different test generation approaches
4. **Mocking and assertion patterns significantly impact** test comprehension effort

Detailed results are available in the `Results/` directory.


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

- **SonarSource Cognitive Complexity:** [White Paper](https://www.sonarsource.com/docs/CognitiveComplexity.pdf)
- **PMD Static Analysis:** [PMD Project](https://pmd.github.io/)
- **Tree-sitter Parser:** [Tree-sitter](https://tree-sitter.github.io/tree-sitter/)