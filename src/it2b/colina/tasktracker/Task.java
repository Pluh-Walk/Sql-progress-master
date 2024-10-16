package it2b.colina.tasktracker;
import java.util.Scanner;

public class Task {

    private Config config; // Ensure this is Config
    private Scanner sc;

    public Task() {
        config = new Config(); // Initialize Config instance
        sc = new Scanner(System.in);  
    }

    public void taskExec() {
        String response;

        do {
            System.out.println("1. ADD TASK: ");
            System.out.println("2. VIEW TASKS: ");
            System.out.println("3. UPDATE TASK: ");
            System.out.println("4. DELETE TASK: ");
            System.out.println("5. EXIT: ");

            System.out.print("ENTER ACTION: ");
            int action = sc.nextInt();
            sc.nextLine(); 

            switch (action) {
                case 1:
                    addTask();
                    break;
                case 2:
                    viewTasks();
                    break;
                case 3:
                    viewTasks();
                    updateTask();
                    break;
                case 4:
                    viewTasks();
                    deleteTask();
                    viewTasks();
                    break;
                case 5:
                    System.out.println("Exiting task management.");
                    return;  // Exit the method and end the loop
                default:
                    System.out.println("Invalid action. Please try again.");
            }

            System.out.print("Do you want to continue? (Y/N): ");
            response = sc.nextLine();
        } while (response.equalsIgnoreCase("Y"));

        System.out.println("Goodbye!");
    }

    // Adding a task to the database
    public void addTask() {
        System.out.print("Task Name: ");
        String taskName = sc.nextLine();
        System.out.print("Task Description: ");
        String taskDesc = sc.nextLine();
        System.out.print("Assigned Employee ID: ");
        int employeeId = sc.nextInt();
        sc.nextLine();  // Consume newline
        System.out.print("Task Status: ");
        String status = sc.nextLine();
        

        String sql = "INSERT INTO tbl_tasks (task_name, task_desc, employee_id, task_status) VALUES (?, ?, ?, ?)";
        config.addRecord(sql, taskName, taskDesc, employeeId, status);
    }

    private void viewTasks() {
        String taskQuery = "SELECT * FROM tbl_tasks";
        String[] taskHeaders = {"Task ID", "Task Name", "Description", "Employee ID", "Status", "Due Date"};
        String[] taskColumns = {"task_id", "task_name", "task_desc", "employee_id", "task_status", "due_date"};

        config.viewRecords(taskQuery, taskHeaders, taskColumns);
    }

    // Updating a task
    private void updateTask() {
        System.out.print("Enter Task ID to edit: ");
        int taskId = sc.nextInt();
        sc.nextLine();  // Consume newline

        System.out.print("Enter new task name: ");
        String newTaskName = sc.nextLine();

        System.out.print("Enter new task description: ");
        String newTaskDesc = sc.nextLine();

        System.out.print("Enter new task status: ");
        String newStatus = sc.nextLine();

        System.out.print("Enter new due date (YYYY-MM-DD): ");
        String newDueDate = sc.nextLine(); // Storing as a string

        String sql = "UPDATE tbl_tasks SET task_name = ?, task_desc = ?, task_status = ?, due_date = ? WHERE task_id = ?";
        config.updateRecord(sql, newTaskName, newTaskDesc, newStatus, newDueDate, taskId);

        System.out.println("Task updated successfully.");
    }

    private void deleteTask() {
        System.out.print("Enter Task ID to delete: ");
        int taskId = sc.nextInt();
        sc.nextLine();  // Consume newline

        System.out.print("Are you sure you want to delete Task ID " + taskId + "? (Y/N): ");
        String confirmation = sc.nextLine();

        if (confirmation.equalsIgnoreCase("Y")) {
            String sql = "DELETE FROM tbl_tasks WHERE task_id = ?";
            config.deleteRecord(sql, taskId);
            System.out.println("Task deleted successfully.");
        } else {
            System.out.println("Delete action canceled.");
        }
    }
}
