package com.srgiovine.seshopping.navigation;

import android.support.annotation.DrawableRes;

import com.srgiovine.seshopping.model.Category;

public class CategoryNavigationItem extends NavigationItemWithIcon {

    private final Category category;

    CategoryNavigationItem(String name, Category category, @DrawableRes int iconRes) {
        super(name, iconRes);
        this.category = category;
    }

    public Category category() {
        return category;
    }
}
