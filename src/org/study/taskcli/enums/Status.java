package org.study.taskcli.enums;

public enum Status {
    DONE("Done"),
    IN_PROGRESS("In progress"),
    TODO("ToDo");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}
