package com.srgiovine.seshopping;

import android.app.Application;
import android.content.SharedPreferences;

import com.srgiovine.seshopping.account.AccountManager;
import com.srgiovine.seshopping.cart.CartManager;
import com.srgiovine.seshopping.data.ItemRepository;
import com.srgiovine.seshopping.data.ItemRepositoryFactory;
import com.srgiovine.seshopping.data.UserRepository;
import com.srgiovine.seshopping.data.UserRepositoryFactory;

public class SEApplication extends Application {

    private static final String SHARED_PREFS_NAME = SEApplication.class.getSimpleName();

    private AccountManager accountManager;

    private ItemRepository itemRepository;

    private CartManager cartManager;

    @Override
    public void onCreate() {
        super.onCreate();
        UserRepository userRepository = UserRepositoryFactory.create();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);

        accountManager = AccountManager.create(userRepository, sharedPreferences);
        itemRepository = ItemRepositoryFactory.create();
        cartManager = CartManager.create(itemRepository, sharedPreferences);
    }

    AccountManager accountManager() {
        return accountManager;
    }

    ItemRepository itemRepository() {
        return itemRepository;
    }

    CartManager cartManager() {
        return cartManager;
    }
}
