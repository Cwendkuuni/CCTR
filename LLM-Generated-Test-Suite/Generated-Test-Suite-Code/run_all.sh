#!/bin/bash

# Enable strict mode: fail on error, undefined variable, or pipeline failure
set -euo pipefail

# List of Python scripts to execute
SCRIPTS=(
    "gpt_test_suite_generation-1.py"
    "gpt_test_suite_generation-2.py"
    "gpt_test_suite_generation-3.py"
    "mistral_test_suite_generation-1.py"
    "mistral_test_suite_generation-2.py"
    "mistral_test_suite_generation-3.py"
)

# Directory for storing logs
LOG_DIR="./logs"
mkdir -p "$LOG_DIR"

# Function to run a script in the background
run_script() {
    script_name=$1
    log_file="$LOG_DIR/${script_name%.py}.log"

    echo "Starting $script_name..."
    echo "Logs will be saved to $log_file"

    start_time=$(date +%s)

    # Execute the script in the background and capture the PID
    python3 "$script_name" > "$log_file" 2>&1 &
    pid=$!

    echo "$pid $script_name" >> "$LOG_DIR/pids.log"

    echo "$script_name launched with PID $pid."
}

# Launch all scripts in parallel
for script in "${SCRIPTS[@]}"; do
    if [ -f "$script" ]; then
        run_script "$script"
    else
        echo "Warning: Script not found: $script. Skipping..."
    fi
done

echo "Waiting for all scripts to complete..."

# Wait for all background processes to finish
wait

echo "All scripts have completed execution."
