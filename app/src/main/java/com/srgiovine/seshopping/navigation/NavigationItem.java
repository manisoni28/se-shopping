package com.srgiovine.seshopping.navigation;

abstract class NavigationItem {

    private final String name;

    public NavigationItem(String name) {
        this.name = name;
    }

    String name() {
        return name;
    }

}
