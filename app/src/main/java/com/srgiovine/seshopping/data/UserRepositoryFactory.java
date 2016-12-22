package com.srgiovine.seshopping.data;

public final class UserRepositoryFactory {

    private UserRepositoryFactory() {
    }

    public static UserRepository create() {
        return new FakeUserRepository();
    }
}
