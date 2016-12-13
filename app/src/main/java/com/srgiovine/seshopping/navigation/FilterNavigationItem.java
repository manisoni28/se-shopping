package com.srgiovine.seshopping.navigation;

import android.support.annotation.DrawableRes;

import com.srgiovine.seshopping.model.Category;

public class FilterNavigationItem extends NavigationItem {

    private final Category category;

    @DrawableRes
    private final int iconRes;

    public FilterNavigationItem(String name, Category category, int iconRes) {
        super(name);
        this.category = category;
        this.iconRes = iconRes;
    }

    public Category category() {
        return category;
    }

    @DrawableRes
    public int iconRes() {
        return iconRes;
    }
}
