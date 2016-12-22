package com.srgiovine.seshopping.account;

import android.content.SharedPreferences;

import com.srgiovine.seshopping.data.UserRepository;
import com.srgiovine.seshopping.model.User;
import com.srgiovine.seshopping.task.BackgroundAsyncTask;
import com.srgiovine.seshopping.task.BackgroundTask;
import com.srgiovine.seshopping.task.Callback;
import com.srgiovine.seshopping.task.SimpleCallback;

public class AccountManager {

    private final UserRepository userRepository;

    private final LoggedInUser loggedInUser;

    private AccountManager(LoggedInUser loggedInUser, UserRepository userRepository) {
        this.loggedInUser = loggedInUser;
        this.userRepository = userRepository;
    }

    public BackgroundTask requestPasswordRecovery(String email, Callback<Void> callback) {
        // TODO replace with real code
        BackgroundAsyncTask<Void> backgroundTask = new BackgroundAsyncTask<Void>() {
            @Override
            protected Void doInBackground() {
                return null;
            }
        };
        backgroundTask.execute();

        return backgroundTask;
    }

    public BackgroundTask signup(User user, Callback<User> callback) {
        return userRepository.createUser(user, new SignupUserCallback(callback));
    }

    public BackgroundTask login(String email, String password, Callback<User> callback) {
        return userRepository.getUser(email, password, new AuthenticatUserCallback(callback));
    }

    public void logout() {
        loggedInUser.logout();
    }

    public boolean loggedInUserExist() {
        return loggedInUser.isPresent();
    }

    private class AuthenticatUserCallback extends SimpleCallback<User> {

        private final Callback<User> callback;

        private AuthenticatUserCallback(Callback<User> callback) {
            this.callback = callback;
        }

        @Override
        public void onSuccess(User user) {
            loggedInUser.save(user.email());
            callback.onSuccess(user);
        }

        @Override
        public void onFailed() {
            callback.onFailed();
        }
    }

    private class SignupUserCallback extends AuthenticatUserCallback {
        public SignupUserCallback(Callback<User> callback) {
            super(callback);
        }

        @Override
        public void onSuccess(User user) {
            super.onSuccess(user);
            // TODO send signup success email
        }
    }

    public static AccountManager create(UserRepository userRepository, SharedPreferences sharedPreferences) {
        LoggedInUser loggedInUser = new LoggedInUser(sharedPreferences);
        loggedInUser.restore();
        return new AccountManager(loggedInUser, userRepository);
    }
}
