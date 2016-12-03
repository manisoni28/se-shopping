package com.srgiovine.seshopping.model;

public class User {

    private final String email;
    private final String password;

    private User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String email() {
        return email;
    }

    public String password() {
        return password;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String email;
        private String password;

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

        public User createUser() {
            return new User(email, password);
        }
    }
}
