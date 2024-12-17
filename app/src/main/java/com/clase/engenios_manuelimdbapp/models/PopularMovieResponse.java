package com.clase.engenios_manuelimdbapp.models;

import java.util.List;

public class PopularMovieResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private TopMeterTitles topMeterTitles;

        public TopMeterTitles getTopMeterTitles() {
            return topMeterTitles;
        }

        public void setTopMeterTitles(TopMeterTitles topMeterTitles) {
            this.topMeterTitles = topMeterTitles;
        }
    }

    public static class TopMeterTitles {
        private List<Edge> edges;

        public List<Edge> getEdges() {
            return edges;
        }

        public void setEdges(List<Edge> edges) {
            this.edges = edges;
        }
    }

    public static class Edge {
        private Node node;

        public Node getNode() {
            return node;
        }

        public void setNode(Node node) {
            this.node = node;
        }
    }

    public static class Node {
        private String id;
        private TitleText titleText;
        private PrimaryImage primaryImage;
        private MeterRanking meterRanking;
        private ReleaseDate releaseDate;

        public TitleText getTitleText() {
            return titleText;
        }

        public void setTitleText(TitleText titleText) {
            this.titleText = titleText;
        }

        public PrimaryImage getPrimaryImage() {
            return primaryImage;
        }

        public void setPrimaryImage(PrimaryImage primaryImage) {
            this.primaryImage = primaryImage;
        }

        public MeterRanking getMeterRanking() {
            return meterRanking;
        }

        public void setMeterRanking(MeterRanking meterRanking) {
            this.meterRanking = meterRanking;
        }

        public ReleaseDate getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(ReleaseDate releaseDate) {
            this.releaseDate = releaseDate;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
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

    public static class ReleaseDate {
        private int year;
        private int month;
        private int day;

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        @Override
        public String toString() {
            return year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day); // Formato YYYY-MM-DD
        }
    }
}
