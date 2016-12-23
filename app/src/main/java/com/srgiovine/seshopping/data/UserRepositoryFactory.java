package com.srgiovine.seshopping.data;

import android.content.Context;

public final class UserRepositoryFactory {

    private UserRepositoryFactory() {
    }

    public static UserRepository create(Context context) {
        return new DBUserRepository(context);
    }
}
