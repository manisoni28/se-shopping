package com.srgiovine.seshopping;

import android.app.Application;
import android.content.SharedPreferences;

import com.srgiovine.seshopping.data.ItemRepository;
import com.srgiovine.seshopping.data.ItemRepositoryFactory;
import com.srgiovine.seshopping.data.UserRepository;
import com.srgiovine.seshopping.data.UserRepositoryFactory;
import com.srgiovine.seshopping.account.AccountManager;

public class SEApplication extends Application {

    private static final String SHARED_PREFS_NAME = SEApplication.class.getSimpleName();

    private AccountManager accountManager;

    private ItemRepository itemRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        UserRepository userRepository = UserRepositoryFactory.create();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);

        accountManager = AccountManager.create(userRepository, sharedPreferences);
        itemRepository = ItemRepositoryFactory.create();
    }

    public AccountManager accountManager() {
        return accountManager;
    }

    public ItemRepository itemRepository() {
        return itemRepository;
    }
}
