package com.srgiovine.seshopping.model;

public class CD implements Item {

    private final long id;
    private final String title;
    private final String artist;
    private final String thumbnailImageUrl;
    private final String musicUrl;
    private final int price;

    private CD(long id, String title, String artist, String thumbnailImageUrl, String musicUrl, int price) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.musicUrl = musicUrl;
        this.price = price;
    }

    public long id() {
        return id;
    }

    public String artist() {
        return artist;
    }

    public String musicUrl() {
        return musicUrl;
    }

    public String thumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public String title() {
        return title;
    }

    @Override
    public int price() {
        return price;
    }

    public static class Builder {

        private long id;
        private String title;
        private String artist;
        private String thumbnailImageUrl;
        private String musicUrl;
        private int price;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }


        public Builder setArtist(String artist) {
            this.artist = artist;
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

        public Builder setMusicUrl(String musicUrl) {
            this.musicUrl = musicUrl;
            return this;
        }

        public Builder setPrice(int price) {
            this.price = price;
            return this;
        }

        public CD createCD() {
            return new CD(id, title, artist, thumbnailImageUrl, musicUrl, price);
        }
    }
}
