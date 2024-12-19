package com.clase.engenios_manuelimdbapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MovieOverviewResponse implements Parcelable {
    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("year")
    private String year;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("ranking")
    private String ranking;

    @SerializedName("plot")
    private Plot plot; // Campo para la descripción

    @SerializedName("ratingsSummary")
    private RatingsSummary ratingsSummary; // Campo para la valoración

    public MovieOverviewResponse() {}

    public MovieOverviewResponse(String id, String title, String year, String imageUrl, String ranking) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.imageUrl = imageUrl;
        this.ranking = ranking;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public Plot getPlot() {
        return plot;
    }

    public void setPlot(Plot plot) {
        this.plot = plot;
    }

    public RatingsSummary getRatingsSummary() {
        return ratingsSummary;
    }

    public void setRatingsSummary(RatingsSummary ratingsSummary) {
        this.ratingsSummary = ratingsSummary;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(year);
        dest.writeString(imageUrl);
        dest.writeString(ranking);
    }

    protected MovieOverviewResponse(Parcel in) {
        id = in.readString();
        title = in.readString();
        year = in.readString();
        imageUrl = in.readString();
        ranking = in.readString();
    }

    public static final Creator<MovieOverviewResponse> CREATOR = new Creator<MovieOverviewResponse>() {
        @Override
        public MovieOverviewResponse createFromParcel(Parcel in) {
            return new MovieOverviewResponse(in);
        }

        @Override
        public MovieOverviewResponse[] newArray(int size) {
            return new MovieOverviewResponse[size];
        }
    };

    public static class Plot {
        @SerializedName("plotText")
        private PlotText plotText;

        public PlotText getPlotText() {
            return plotText;
        }

        public void setPlotText(PlotText plotText) {
            this.plotText = plotText;
        }

        public static class PlotText {
            @SerializedName("plainText")
            private String plainText;

            public String getPlainText() {
                return plainText;
            }

            public void setPlainText(String plainText) {
                this.plainText = plainText;
            }
        }
    }

    public static class RatingsSummary {
        @SerializedName("aggregateRating")
        private double aggregateRating;

        public double getAggregateRating() {
            return aggregateRating;
        }

        public void setAggregateRating(double aggregateRating) {
            this.aggregateRating = aggregateRating;
        }
    }
}
