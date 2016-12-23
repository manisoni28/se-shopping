package com.srgiovine.seshopping.model;

import android.text.TextUtils;

import java.util.Arrays;

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

    @Override
    public String toString() {
        if (!TextUtils.isEmpty(first) && !TextUtils.isEmpty(last)) {
            return first + " " + last;
        }
        return "";
    }

    public static Builder builder() {
        return new Builder();
    }

    public static boolean isValidFullName(String fullName) {
        return fullName.split(" ").length > 1;
    }

    public static Name fromFullName(String fullName) {
        String[] nameChunks = fullName.split(" ");

        String[] firstChunks = Arrays.copyOfRange(nameChunks, 0, nameChunks.length - 1);
        String[] lastChunks = Arrays.copyOfRange(nameChunks, nameChunks.length - 1, nameChunks.length);

        return builder()
                .setFirst(join(" ", firstChunks))
                .setLast(join(" ", lastChunks))
                .build();
    }

    private static String join(String delimeter, String... strings) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            builder.append(strings[i]);
            if (i < strings.length - 1) {
                builder.append(delimeter);
            }
        }
        return builder.toString();
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
