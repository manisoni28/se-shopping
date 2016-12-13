package com.srgiovine.seshopping.navigation;

import com.srgiovine.seshopping.model.Category;

import srgiovine.com.seshopping.R;

public final class NavigationItemFactory {

    public static NavigationItem[] createNavigationItems() {
        return new NavigationItem[]{
                new FilterNavigationItem("Mens", Category.Mens, R.drawable.ic_man),
                new FilterNavigationItem("Womens", Category.Womens, R.drawable.ic_woman),
                new FilterNavigationItem("Hats", Category.Hats, R.drawable.ic_hat),
                new FilterNavigationItem("Sweaters", Category.Sweaters, R.drawable.ic_sweater),
                new FilterNavigationItem("T-Shirts", Category.Tshirts, R.drawable.ic_shirt),
                new FilterNavigationItem("Jeans", Category.Jeans, R.drawable.ic_jeans),
                new FilterNavigationItem("Purses", Category.Purses, R.drawable.ic_purse),
                new FilterNavigationItem("Sunglasses", Category.Sunglasses, R.drawable.ic_glasses),
                new FilterNavigationItem("Shoes", Category.Shoes, R.drawable.ic_shoe),
                new SettingsNavigationItem("Settings", R.drawable.ic_settings)
        };
    }
}
