package com.srgiovine.seshopping.model;

public class Address {

    private final String street;
    private final String city;
    private final String state;
    private final String zip;
    private final String country;

    private Address(String city, String street, String state, String zip, String country) {
        this.city = city;
        this.street = street;
        this.state = state;
        this.zip = zip;
        this.country = country;
    }

    public String city() {
        return city;
    }

    public String country() {
        return country;
    }

    public String state() {
        return state;
    }

    public String street() {
        return street;
    }

    public String zip() {
        return zip;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String city;
        private String street;
        private String state;
        private String zip;
        private String country;

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setStreet(String street) {
            this.street = street;
            return this;
        }

        public Builder setState(String state) {
            this.state = state;
            return this;
        }

        public Builder setZip(String zip) {
            this.zip = zip;
            return this;
        }

        public Builder setCountry(String country) {
            this.country = country;
            return this;
        }

        public Address build() {
            return new Address(city, street, state, zip, country);
        }
    }

}
