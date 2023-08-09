package org.example.model;

public class Address {

    private final String street;
    private final String town;

    private final String country;

    private final String postalCode;

    public Address(String street, String town, String country, String postalCode) {
        this.street = street;
        this.town = town;
        this.country = country;
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Address{");
        sb.append("street='").append(street).append('\'');
        sb.append(", town='").append(town).append('\'');
        sb.append(", country='").append(country).append('\'');
        sb.append(", postalCode='").append(postalCode).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
