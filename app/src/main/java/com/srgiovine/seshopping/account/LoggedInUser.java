package com.srgiovine.seshopping.account;

import android.content.SharedPreferences;

class LoggedInUser {

    private static final String EMAIL = "user.email";

    private final SharedPreferences sharedPreferences;

    private String email;

    LoggedInUser(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    void save(String email) {
        this.email = email;
        sharedPreferences.edit().putString(EMAIL, email).apply();
    }

    void restore() {
        this.email = sharedPreferences.getString(EMAIL, null);
    }

    void logout() {
        save(null);
    }

    String email() {
        return email;
    }

    boolean isPresent() {
        return email != null;
    }
}
