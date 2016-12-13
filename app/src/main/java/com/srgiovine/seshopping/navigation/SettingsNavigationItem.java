package com.srgiovine.seshopping.navigation;

import android.support.annotation.DrawableRes;

public class SettingsNavigationItem extends NavigationItem {

    @DrawableRes
    private final int iconRes;

    public SettingsNavigationItem(String name, int iconRes) {
        super(name);
        this.iconRes = iconRes;
    }

    public int iconRes() {
        return iconRes;
    }
}
