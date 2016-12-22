package com.srgiovine.seshopping.data;

import com.srgiovine.seshopping.model.User;
import com.srgiovine.seshopping.task.BackgroundAsyncTask;
import com.srgiovine.seshopping.task.BackgroundTask;
import com.srgiovine.seshopping.task.Callback;

class FakeUserRepository implements UserRepository {

    @Override
    public BackgroundTask createUser(User user, final Callback<User> callback) {
        BackgroundAsyncTask<User> backgroundTask = new BackgroundAsyncTask<User>() {
            @Override
            protected User doInBackground() {
                return User.builder().setEmail("email").build();
            }

            @Override
            protected void onPostExecute(User user) {
                super.onPostExecute(user);
                callback.onSuccess(user);
            }
        };
        backgroundTask.execute();
        return backgroundTask;
    }

    @Override
    public BackgroundTask getUser(String email, String password, Callback<User> callback) {
        return createUser(null, callback);
    }
}
