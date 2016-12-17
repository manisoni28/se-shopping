package com.srgiovine.seshopping.model;

import android.support.annotation.DrawableRes;

import java.util.ArrayList;
import java.util.List;

public class Item {

    private final String name;

    private final String description;

    private final List<Category> categories;

    @DrawableRes
    private final int image;

    @DrawableRes
    private final int icon;

    private final int price;

    private Item(String name, String description, List<Category> categories, int price,
                 @DrawableRes int image, @DrawableRes int icon) {
        this.name = name;
        this.description = description;
        this.categories = categories;
        this.price = price;
        this.image = image;
        this.icon = icon;
    }

    public List<Category> categories() {
        return categories;
    }

    public String description() {
        return description;
    }

    @DrawableRes
    public int image() {
        return image;
    }

    @DrawableRes
    public int icon() {
        return icon;
    }

    public String name() {
        return name;
    }

    public int price() {
        return price;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String name;
        private String description;
        private final List<Category> categories = new ArrayList<>();

        private int price;

        @DrawableRes
        private int image;

        @DrawableRes
        private int icon;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder addCategory(Category category) {
            if (!categories.contains(category)) {
                categories.add(category);
            }
            return this;
        }

        public Builder setPrice(int price) {
            this.price = price;
            return this;
        }

        public Builder setImage(@DrawableRes int image) {
            this.image = image;
            return this;
        }

        public Builder setIcon(@DrawableRes int icon) {
            this.icon = icon;
            return this;
        }

        public Item build() {
            return new Item(name, description, categories, price, image, icon);
        }
    }
}
