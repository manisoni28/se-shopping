package com.srgiovine.seshopping.navigation;

import android.support.annotation.DrawableRes;

abstract class NavigationItemWithIcon extends NavigationItem {

    @DrawableRes
    private final int iconRes;

    public NavigationItemWithIcon(String name, @DrawableRes int iconRes) {
        super(name);
        this.iconRes = iconRes;
    }

    @DrawableRes
    public int iconRes() {
        return iconRes;
    }
}
