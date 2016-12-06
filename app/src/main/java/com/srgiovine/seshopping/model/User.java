package com.srgiovine.seshopping.model;

public class User {

    private final String email;
    private final String password;
    private final String firstName;
    private final String lastName;

    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String email() {
        return email;
    }

    public String password() {
        return password;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String email;
        private String password;
        private String firstName;
        private String lastName;

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setId(long id) {
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public User createUser() {
            return new User(email, password, firstName, lastName);
        }
    }
}
