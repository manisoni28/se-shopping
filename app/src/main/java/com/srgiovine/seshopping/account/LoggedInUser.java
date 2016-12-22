package com.srgiovine.seshopping.account;

import android.content.SharedPreferences;

class LoggedInUser {

    private static final String EMAIL = "user.email";
    private static final String PASSWORD = "user.password";

    private final SharedPreferences sharedPreferences;

    private String email;
    private String password;

    LoggedInUser(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    void save(String email, String password) {
        this.email = email;
        this.password = password;

        sharedPreferences.edit()
                .putString(EMAIL, email)
                .putString(PASSWORD, password)
                .apply();
    }

    void logout() {
        save(null, null);
    }

    String email() {
        return email;
    }

    String password() {
        return password;
    }

    boolean isPresent() {
        return email != null;
    }

    private void restore() {
        email = sharedPreferences.getString(EMAIL, null);
        password = sharedPreferences.getString(PASSWORD, null);
    }
}
