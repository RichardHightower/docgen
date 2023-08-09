package org.example.model;

public class Customer extends Person {
    private final boolean elite;

    public Customer(String firstName, String lastName, String workEmail, String email, Address address, boolean elite) {
        super(firstName, lastName, workEmail, email, address);
        this.elite = elite;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "elite=" + elite +
                '}';
    }
}
