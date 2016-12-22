package com.srgiovine.seshopping;

import android.app.Application;

import com.srgiovine.seshopping.data.ItemProvider;
import com.srgiovine.seshopping.data.ItemProviderFactory;

public class SEApplication extends Application {

    private ItemProvider itemProvider;

    @Override
    public void onCreate() {
        super.onCreate();
        itemProvider = ItemProviderFactory.create();
    }

    public ItemProvider itemProvider() {
        return itemProvider;
    }
}
