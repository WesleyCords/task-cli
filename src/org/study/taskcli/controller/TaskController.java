package org.study.taskcli.controller;

import org.study.taskcli.model.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskController {
    private final Path PATH_FILE_JSON = Path.of("tasks.json");
    public List<Task> tasks;

    public TaskController() {
        this.tasks = load();
    }

    public List<Task> load() {
        List<Task> cacheTask = new ArrayList<>();

        if(!Files.exists(PATH_FILE_JSON)) {
            return new ArrayList<>();
        }
        // I/O vou usar try/cacth
        try {
            String jsonContent = Files.readString(PATH_FILE_JSON).trim();

            if(jsonContent.contains("[]") || jsonContent.isEmpty()) return new ArrayList<>();

            String rawTasks = jsonContent.substring(1, jsonContent.length() - 1).trim();
            if(rawTasks.isEmpty()) return new ArrayList<>();

            String[] tokens = rawTasks.split("(?<=\\}),\\s*(?=\\{)"); // Pesquisei isso

            for(String t : tokens) {
                cacheTask.add(Task.fromJson(t.trim()));
            }
        } catch (IOException err) {
            return new ArrayList<>();
        }

        return cacheTask;
    }

    public void save() {
        try {
            List<String> jsonTask = new ArrayList<>();

            for(Task t : tasks) {
                jsonTask.add(t.toJson());
            }

            String joinTasks = String.join(",\n", jsonTask);

            String finalJson = "[\n" + joinTasks + "\n]";

            Files.writeString(PATH_FILE_JSON, finalJson);
        } catch(IOException err) {
            System.out.println("Erro ao salvar arquivo: " + err.getMessage());
        }
    }

    public void addTask(String description) {
        if(description.isBlank()) {
            System.out.println("Description must have content!");
            return;
        }
        int lastId = tasks.stream()
                .mapToInt(Task::id)
                .max()
                .orElse(0) + 1;

        Task newTask = Task.createNew(lastId, description);
        tasks.add(newTask);
        System.out.printf("Task added successfully (ID: %d)%n", newTask.id());

        save();
    }

    public void updateDescription(String id, String desc) {
        Task taskToUp = findByID(id).orElseThrow(() -> new IllegalArgumentException("Task not found!"));
        Task updatedTask = taskToUp.updateDescription(desc);

        int index = tasks.indexOf(taskToUp);
        tasks.set(index, updatedTask);

        save();
    }

    public void deleteTask(String id){
        Task task = findByID(id).orElseThrow(() -> new IllegalArgumentException("Task with ID " + id + " not found!"));
        tasks.remove(task);

        save();
    }

    public void markInProgress(String id){
        Task task = findByID(id).orElseThrow(() -> new IllegalArgumentException("Task with ID " + id + " not found!"));
        Task taskUp = task.markInProgress();

        int index = tasks.indexOf(task);
        tasks.set(index, taskUp);

        save();
    }

    public void markDone(String id){
        Task task = findByID(id).orElseThrow(() -> new IllegalArgumentException("Task with ID " + id + " not found!"));
        Task taskUp = task.markDone();

        int index = tasks.indexOf(task);
        tasks.set(index, taskUp);

        save();
    }

    public void listTask(String type) {
        if(tasks.isEmpty()) {
            System.out.println("No tasks recorded.");
            return;
        };

        System.out.println("\nListing tasks...\n");

        if (type.equalsIgnoreCase("All")) {
            tasks.forEach(System.out::println);
            return;
        }
        tasks.stream()
                .filter((t) -> t.status().getValue().equalsIgnoreCase(type))
                .forEach(System.out::println);
    }

    public Optional<Task> findByID(String id) {
        try {
            int idInt = Integer.parseInt(id);
            return tasks.stream().filter((t) -> t.id() == idInt).findFirst();
        } catch (NumberFormatException err) {
            System.out.println("Erro with type of ID: " + err.getMessage());
        }

        return Optional.empty();
    }
}
