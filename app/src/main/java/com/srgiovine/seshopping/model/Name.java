package com.srgiovine.seshopping.model;

public class Name {

    private final String first;
    private final String last;

    private Name(String first, String last) {
        this.first = first;
        this.last = last;
    }

    public String first() {
        return first;
    }

    public String last() {
        return last;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String first;
        private String last;

        public Builder setFirst(String first) {
            this.first = first;
            return this;
        }

        public Builder setLast(String last) {
            this.last = last;
            return this;
        }

        public Name build() {
            return new Name(first, last);
        }
    }
}
