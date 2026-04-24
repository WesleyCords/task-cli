package org.study.taskcli;

import org.study.taskcli.controller.TaskController;
import org.study.taskcli.enums.Status;
import org.study.taskcli.model.Task;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: task-cli <command> [arguments]");
            return;
        }

        TaskController controll = new TaskController();

        String cmd = args[0];

        switch (cmd) {
            case "add" -> {
                if(args.length < 2) {
                    System.out.println("Usage: Task-cli add <description>");
                    return;
                }
                controll.addTask(args[1]);
            }
            case "delete" -> {
                if(args.length < 2) {
                    System.out.println("Usage: task-cli delete <id>");
                    return;
                } else {
                    try {
                        controll.deleteTask(args[1]);
                    } catch (Exception err) {
                        System.out.println("Error: " + err.getMessage());
                    }
                }

            }
            case "update" -> {
                if(args.length < 3) {
                    System.out.println("Usage: task-cli update <id> <new description>");
                    return;
                }
                controll.updateDescription(args[1], args[2]);
            }
            case "mark-in-progress" -> {
                if(args.length < 2) {
                    System.out.println("Usage: task-cli mark-in-progress <id>");
                    return;
                }
                controll.markInProgress(args[1]);
            }
            case "mark-done" -> {
                if(args.length < 2) {
                    System.out.println("Usage: task-cli mark-done <id>");
                    return;
                }
                controll.markDone(args[1]);
            }
            case "list" -> {
                if(args.length == 1) {
                    controll.listTask("All");
                    return;
                };
                Status type;
                try {
                    type = Status.valueOf(args[1].toUpperCase().replace("-", "_"));
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid status: " + args[1]);
                    return;
                }
                controll.listTask(type.getValue());
            }

            default -> {
                System.out.println("Unkdown command: " + cmd);
            }
        }
    }
}
