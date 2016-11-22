package com.srgiovine.seshopping.model;

public class Book implements Purchasable {

    private final long id;
    private final String title;
    private final String author;
    private final String thumbnailImageUrl;
    private final String bookUrl;
    private final int price;

    private Book(String author, long id, String title, String thumbnailImageUrl, String bookUrl, int price) {
        this.author = author;
        this.id = id;
        this.title = title;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.bookUrl = bookUrl;
        this.price = price;
    }

    public String author() {
        return author;
    }

    public String bookUrl() {
        return bookUrl;
    }

    public long id() {
        return id;
    }

    public int price() {
        return price;
    }

    public String thumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public String title() {
        return title;
    }

    @Override
    public int purchasePrice() {
        return price();
    }

    public static class Builder {

        private String author;
        private long id;
        private String title;
        private String thumbnailImageUrl;
        private String bookUrl;
        private int price;

        public Builder setAuthor(String author) {
            this.author = author;
            return this;
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setThumbnailImageUrl(String thumbnailImageUrl) {
            this.thumbnailImageUrl = thumbnailImageUrl;
            return this;
        }

        public Builder setBookUrl(String bookUrl) {
            this.bookUrl = bookUrl;
            return this;
        }

        public Builder setPrice(int price) {
            this.price = price;
            return this;
        }

        public Book createBook() {
            return new Book(author, id, title, thumbnailImageUrl, bookUrl, price);
        }
    }
}
