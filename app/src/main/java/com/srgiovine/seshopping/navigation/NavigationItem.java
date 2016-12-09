package com.srgiovine.seshopping.navigation;

import android.support.annotation.DrawableRes;

public class NavigationItem {

    @DrawableRes
    private final int iconRes;

    private final String name;

    private final Type type;

    private boolean isChecked;

    private NavigationItem(int iconRes, String name, Type type) {
        this.iconRes = iconRes;
        this.name = name;
        this.type = type;
    }

    void setChecked(boolean checked) {
        isChecked = checked;
    }

    int iconRes() {
        return iconRes;
    }

    String name() {
        return name;
    }

    public Type type() {
        return type;
    }

    boolean isChecked() {
        return isChecked;
    }

    static Builder builder() {
        return new Builder();
    }

    static class Builder {

        private int iconRes;
        private String name;
        private Type type;

        Builder setIconRes(int iconRes) {
            this.iconRes = iconRes;
            return this;
        }

        Builder setName(String name) {
            this.name = name;
            return this;
        }

        Builder setType(Type type) {
            this.type = type;
            return this;
        }

        NavigationItem build() {
            return new NavigationItem(iconRes, name, type);
        }
    }

    public enum Type {
        LOGOUT, FILTER;

        static Type fromOrdinal(int ordinal) {
            return Type.values()[ordinal];
        }
    }
}
