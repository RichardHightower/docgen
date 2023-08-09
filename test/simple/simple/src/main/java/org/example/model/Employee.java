package org.example.model;

public class Employee extends Person {

    private final boolean newHire;

    public Employee(String firstName, String lastName, String workEmail, String email,
                    boolean newHire, Address address) {
        super(firstName, lastName, workEmail, email, address);
        this.newHire = newHire;
    }

    public boolean isNewHire() {
        return newHire;
    }
}
