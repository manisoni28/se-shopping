package com.srgiovine.seshopping.model;

public class User {

    private final long id;
    private final String email;
    private final String password;

    private User(String email, long id, String password) {
        this.email = email;
        this.id = id;
        this.password = password;
    }

    public String email() {
        return email;
    }

    public long id() {
        return id;
    }

    public String password() {
        return password;
    }

    public static class Builder {

        private String email;
        private long id;
        private String password;

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public User createUser() {
            return new User(email, id, password);
        }
    }
}
