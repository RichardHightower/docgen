package org.example.service;

import org.example.model.Department;
import org.example.model.Employee;
import org.example.model.Manager;
import org.example.repo.HRRepo;
import org.example.repo.HRRepoException;

import java.util.logging.Level;
import java.util.logging.Logger;


@Service
public class HRService {
    private final HRRepo repo;
    private final WorkflowService workflow;

    private final Logger logger = Logger.getLogger(HRService.class.getName());

    public HRService(HRRepo repo, WorkflowService workflow) {
        this.repo = repo;
        this.workflow = workflow;
    }


    public void updateEmployeesManager(final Employee employee, final Manager manager) {
        if (!employee.isNewHire()) {
            workflow.initiateManagerChangeWorkflow(employee, manager);
        } else {
            try {
                repo.updateManager(employee, manager);
            } catch (HRRepoException hrRepoException) {
                logger.log(Level.SEVERE, "Unable to handle update employee operations",
                        hrRepoException);
            }
        }
    }

    public void changeEmployeeDepartment(final Employee employee, final Department department) {
        if (!employee.isNewHire()) {
            workflow.initiateChangeDepartmentWorkflow(employee, department);
        } else {
            try {
                repo.updateManager(employee, department.getManager());
                repo.updateDepartment(employee, department);
            } catch (HRRepoException hrRepoException) {
                logger.log(Level.SEVERE, "Unable to handle update employee operations",
                        hrRepoException);
            }
        }
    }



}
