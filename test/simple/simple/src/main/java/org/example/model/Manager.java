package org.example.model;

import java.util.List;

public class Manager {

    private final Employee manager;
    private final List<Employee> employees;

    public Manager(Employee manager, List<Employee> employees) {
        this.manager = manager;
        this.employees = employees;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Manager{");
        sb.append("manager=").append(manager);
        sb.append(", employees=[\n");
        for (var employee : employees) {
            sb.append("\t").append(employee).append(",\n");
        }
        sb.append("]\n");
        sb.append('}');
        return sb.toString();
    }

}
