package com.srgiovine.seshopping.navigation;

import android.support.annotation.DrawableRes;

import com.srgiovine.seshopping.model.Gender;

public class GenderNavigationItem extends NavigationItemWithIcon {

    private final Gender gender;

    GenderNavigationItem(String name, Gender gender, @DrawableRes int iconRes) {
        super(name, iconRes);
        this.gender = gender;
    }

    public Gender gender() {
        return gender;
    }
}
