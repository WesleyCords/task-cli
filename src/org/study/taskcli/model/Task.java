package org.study.taskcli.model;

import org.study.taskcli.enums.Status;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record Task(
        int id,
        String description,
        Status status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public Task {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or empty.");
        }
    }

    public static Task createNew(int id, String description) {
        LocalDateTime now = LocalDateTime.now();
        return new Task(id, description, Status.TODO, now, now);
    }

    public Task markInProgress() {
        return new Task(this.id, this.description, Status.IN_PROGRESS, this.createdAt, LocalDateTime.now());
    }

    public Task markDone() {
        return new Task(this.id, this.description, Status.DONE, this.createdAt, LocalDateTime.now());
    }

    public Task updateDescription(String newDescription) {
        return new Task(this.id, newDescription, this.status, this.createdAt, LocalDateTime.now());
    }

    public String toJson() {
        return String.format(
                "{\"id\":\"%d\",\"description\":\"%s\",\"status\":\"%s\",\"createdAt\":\"%s\",\"updatedAt\":\"%s\"}",
                id, description, status, createdAt.format(FORMATTER), updatedAt.format(FORMATTER)
        );
    }

    public static Task fromJson(String json) {
        String idStr = extractValue(json, "id");
        String desc = extractValue(json, "description");
        String statusStr = extractValue(json, "status");
        String createdAtStr = extractValue(json, "createdAt");
        String updatedAtStr = extractValue(json, "updatedAt");

        return new Task(
            Integer.parseInt(idStr),
            desc,
            Status.valueOf(statusStr.toUpperCase().replace(" ", "_")),
            LocalDateTime.parse(createdAtStr, FORMATTER),
            LocalDateTime.parse(updatedAtStr, FORMATTER)
        );
    }

    private static String extractValue(String json, String key) {
        String anchor = "\"" + key + "\":\"";
        int start = json.indexOf(anchor) + anchor.length();
        int end = json.indexOf("\"", start);
        return json.substring(start, end);
    }
}