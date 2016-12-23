package com.srgiovine.seshopping.data;

import android.content.Context;

public final class ItemRepositoryFactory {

    private ItemRepositoryFactory() {
    }

    public static ItemRepository create(Context context) {
        return new DBItemRepository(context);
    }

}
