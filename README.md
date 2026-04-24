# Task CLI Application

This is a simple command-line interface (CLI) application for managing tasks. You can add, update, delete, mark, and list tasks directly from the terminal.

## Features

- **Add a Task:** Add a new task with a description.
- **Update a Task:** Update the description of an existing task.
- **Delete a Task:** Remove a task by its ID.
- **Mark a Task:** Mark a task as "in progress" or "done."
- **List Tasks:** List all tasks or filter them by status (e.g., `todo`, `in progress`, `done`).

## Installation

1. **Clone the repository:**

   ```bash
   git clone https://github.com/WesleyCords/task-cli.git
   cd task-cli

2. **Create file task-cli.sh:**
    ```bash
   touch task-cli.sh && nano task-cli

3. **Add content/script to file:**
   ```bash
   #!/bin/bash
   
   PROJECT_ROOT="/add/dir/root/project"
   OUT_DIR="$PROJECT_ROOT/TaskCLI"
   SRC_DIR="$PROJECT_ROOT/src"
   MAIN_CLASS="Main"
   
   echo -e "\033[34mCompilando fontes...\033[0m"
   find "$SRC_DIR" -name "*.java" | xargs javac -d "$OUT_DIR" -cp "$OUT_DIR"
   
   if [ ! -d "$OUT_DIR" ]; then
   echo -e "\033[31mErro: Diretório de saída não encontrado em: $OUT_DIR\033[0m"
   exit 1
   fi
   
   echo -e "\033[32mExecutando $MAIN_CLASS...\033[0m\n"
   
   java -cp "$OUT_DIR" "$MAIN_CLASS" "$@"
   
4. **Permission to file:**
   ```bash
    chmod +x task-cli.sh

5. **Run the application:**
    ```bash
   task-cli <command> [arguments]
   ```
## Usage
```bash
# Adding a new task
task-cli add "Buy groceries"
# Output: Task added successfully (ID: 1)

# Updating a task
task-cli update 1 "Buy groceries and cook dinner"
# Output: Task updated successfully (ID: 1)

# Deleting a task
task-cli delete 1
# Output: Task deleted successfully (ID: 1)

# Marking a task as in progress
task-cli mark-in-progress 1
# Output: Task marked as in progress (ID: 1)

# Marking a task as done
task-cli mark-done 1
# Output: Task marked as done (ID: 1)

# Listing all tasks
task-cli list
# Output: List of all tasks

# Listing tasks by status
task-cli list todo
task-cli list in-progress
task-cli list done

```

## Link
   Link to project https://roadmap.sh/projects/task-tracker
