package com.srgiovine.seshopping.model;

import android.support.annotation.DrawableRes;

public class Item {

    private final String name;

    private final String description;

    @DrawableRes
    private final int image;

    private final int price;

    private Item(String name, String description, int price, int image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public String description() {
        return description;
    }

    public int image() {
        return image;
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
        private int price;

        @DrawableRes
        private int image;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
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

        public Item build() {
            return new Item(name, description, price, image);
        }
    }
}
