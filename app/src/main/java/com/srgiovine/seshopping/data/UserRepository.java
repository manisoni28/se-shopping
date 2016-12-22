package com.srgiovine.seshopping.data;

import com.srgiovine.seshopping.model.User;
import com.srgiovine.seshopping.task.BackgroundTask;
import com.srgiovine.seshopping.task.Callback;

public interface UserRepository {

    BackgroundTask createUser(User user, Callback<User> callback);

    BackgroundTask updateUser(User user, Callback<User> callback);

    BackgroundTask getUser(String email, String password, Callback<User> callback);

}
