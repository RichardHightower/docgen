package org.example.model;

public class Department {
    private final Manager manager;

    public Department(Manager manager) {
        this.manager = manager;
    }

    public Manager getManager() {
        return manager;
    }
}
