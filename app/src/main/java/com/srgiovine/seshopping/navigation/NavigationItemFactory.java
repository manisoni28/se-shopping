package com.srgiovine.seshopping.navigation;

import srgiovine.com.seshopping.R;

import static com.srgiovine.seshopping.navigation.NavigationItem.Type;
import static com.srgiovine.seshopping.navigation.NavigationItem.builder;

public final class NavigationItemFactory {

    public static NavigationItem[] creteNavigationItems() {
        return new NavigationItem[]{
                builder().setName("Mens").setType(Type.FILTER)
                        .setIconRes(R.drawable.ic_man).build(),
                builder().setName("Womens").setType(Type.FILTER)
                        .setIconRes(R.drawable.ic_woman).build(),
                builder().setName("Hats").setType(Type.FILTER)
                        .setIconRes(R.drawable.ic_hat).build(),
                builder().setName("Sweaters").setType(Type.FILTER)
                        .setIconRes(R.drawable.ic_sweater).build(),
                builder().setName("T-shirts").setType(Type.FILTER)
                        .setIconRes(R.drawable.ic_shirt).build(),
                builder().setName("Jeans").setType(Type.FILTER)
                        .setIconRes(R.drawable.ic_jeans).build(),
                builder().setName("Purses").setType(Type.FILTER)
                        .setIconRes(R.drawable.ic_purse).build(),
                builder().setName("Sunglasses").setType(Type.FILTER)
                        .setIconRes(R.drawable.ic_glasses).build(),
                builder().setName("Shoes").setType(Type.FILTER)
                        .setIconRes(R.drawable.ic_shoe).build(),
                builder().setName("Logout").setType(Type.LOGOUT)
                        .setIconRes(R.drawable.ic_logout).build()
        };
    }
}
