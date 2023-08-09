package org.example.repo;

import org.example.model.Department;
import org.example.model.Employee;
import org.example.model.Manager;

public interface HRRepo extends Repo<Employee, String> {

    @Override
    default void save(Employee employee) {

    }

    @Override
    default void lookup(String s) {

    }

    void updateManager(Employee employee, Manager manager);

    void updateDepartment(Employee employee, Department department);
}
