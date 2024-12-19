package com.clase.engenios_manuelimdbapp.models;

public class Movie {
    private String id;
    private String title;
    private String originalTitle;
    private String imageUrl;
    private int releaseYear;

    public Movie() {
    }

    public Movie(String id, String title, String originalTitle, String imageUrl, int releaseYear) {
        this.id = id;
        this.title = title;
        this.originalTitle = originalTitle;
        this.imageUrl = imageUrl;
        this.releaseYear = releaseYear;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void updateFromNode(PopularMovieResponse.Node node) {
        this.title = node.getTitleText().getText();

        this.originalTitle = node.getTitleText().getText();

        this.imageUrl = node.getPrimaryImage().getUrl();

        this.releaseYear = 0;
    }

    public static class TitleText {
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public static class PrimaryImage {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class MeterRanking {
        private int currentRank;

        public int getCurrentRank() {
            return currentRank;
        }

        public void setCurrentRank(int currentRank) {
            this.currentRank = currentRank;
        }
    }
}
