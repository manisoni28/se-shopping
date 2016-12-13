package com.srgiovine.seshopping.model;

public class User {

    private final String email;
    private final String password;
    private final Name name;
    private final Address address;
    private final CreditCardInfo creditCardInfo;

    private User(Address address, String email, String password, Name name, CreditCardInfo creditCardInfo) {
        this.address = address;
        this.email = email;
        this.password = password;
        this.name = name;
        this.creditCardInfo = creditCardInfo;
    }

    public String email() {
        return email;
    }

    public String password() {
        return password;
    }

    public Address address() {
        return address;
    }

    public CreditCardInfo creditCardInfo() {
        return creditCardInfo;
    }

    public Name name() {
        return name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Address address;
        private String email;
        private String password;
        private Name name;
        private CreditCardInfo creditCardInfo;

        public Builder setAddress(Address address) {
            this.address = address;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setName(Name name) {
            this.name = name;
            return this;
        }

        public Builder setCreditCardInfo(CreditCardInfo creditCardInfo) {
            this.creditCardInfo = creditCardInfo;
            return this;
        }

        public User build() {
            return new User(address, email, password, name, creditCardInfo);
        }
    }
}
