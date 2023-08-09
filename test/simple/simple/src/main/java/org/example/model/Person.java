package org.example.model;

import java.util.Objects;

public class Person {

    private final String firstName;
    private final String lastName;

    private final String workEmail;

    private final String email;

    private final Address address;


    public Person(String firstName, String lastName, String workEmail, String email,
                  Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.workEmail = workEmail;
        this.email = email;
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(workEmail, person.workEmail) && Objects.equals(email, person.email) && Objects.equals(address, person.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, workEmail, email, address);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Person{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", workEmail='").append(workEmail).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", address=").append(address);
        sb.append('}');
        return sb.toString();
    }

}
