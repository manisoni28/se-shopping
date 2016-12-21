package com.srgiovine.seshopping.navigation;

import com.srgiovine.seshopping.model.Category;
import com.srgiovine.seshopping.model.Gender;

import srgiovine.com.seshopping.R;

public final class NavigationItemFactory {

    public static NavigationItem[] createNavigationItems() {
        return new NavigationItem[]{
                new SeparatorNavigationItem("Gender"),
                new GenderNavigationItem("Mens", Gender.Mens, R.drawable.ic_man),
                new GenderNavigationItem("Womens", Gender.Womens, R.drawable.ic_woman),

                new SeparatorNavigationItem("Category"),
                new CategoryNavigationItem("Hats", Category.Hats, R.drawable.ic_hat),
                new CategoryNavigationItem("Sweaters", Category.Sweaters, R.drawable.ic_sweater),
                new CategoryNavigationItem("T-Shirts", Category.Tshirts, R.drawable.ic_shirt),
                new CategoryNavigationItem("Jeans", Category.Jeans, R.drawable.ic_jeans),
                new CategoryNavigationItem("Purses", Category.Purses, R.drawable.ic_purse),
                new CategoryNavigationItem("Sunglasses", Category.Sunglasses, R.drawable.ic_glasses),
                new CategoryNavigationItem("Shoes", Category.Shoes, R.drawable.ic_shoe),

                new SeparatorNavigationItem("Settings"),
                new SettingsNavigationItem("Settings", R.drawable.ic_settings)
        };
    }
}
