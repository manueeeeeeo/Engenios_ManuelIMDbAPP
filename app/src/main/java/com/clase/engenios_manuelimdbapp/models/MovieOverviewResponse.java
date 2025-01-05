package com.clase.engenios_manuelimdbapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author Manuel
 * @version 1.0*/

public class MovieOverviewResponse implements Parcelable {

    @SerializedName("data")
    private Data data; // Variable que representa la key del JSON que es data

    protected MovieOverviewResponse(Parcel in) {
        data = in.readParcelable(Data.class.getClassLoader());
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

    /**
     * @return
     * Método para obtener el objeto data*/
    public Data getData() {
        return data;
    }

    /**
     * @param data
     * Método para establecer el objeto data*/
    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(data, flags);
    }

    // Clase que he creado y utilizo para hacer referencia a Data
    public static class Data implements Parcelable {
        @SerializedName("title")
        private Title title;

        public Data() {}

        protected Data(Parcel in) {
            title = in.readParcelable(Title.class.getClassLoader());
        }

        public static final Creator<Data> CREATOR = new Creator<Data>() {
            @Override
            public Data createFromParcel(Parcel in) {
                return new Data(in);
            }

            @Override
            public Data[] newArray(int size) {
                return new Data[size];
            }
        };

        public Title getTitle() {
            return title;
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(title, flags);
        }
    }

    public static class Title implements Parcelable {
        @SerializedName("id")
        private String id;

        @SerializedName("titleText")
        private TitleText titleText;

        @SerializedName("releaseDate")
        private ReleaseDate releaseDate;

        @SerializedName("ratingsSummary")
        private RatingsSummary ratingsSummary;

        @SerializedName("plot")
        private Plot plot;

        public Title() {}

        protected Title(Parcel in) {
            id = in.readString();
            titleText = in.readParcelable(TitleText.class.getClassLoader());
            releaseDate = in.readParcelable(ReleaseDate.class.getClassLoader());
            ratingsSummary = in.readParcelable(RatingsSummary.class.getClassLoader());
            plot = in.readParcelable(Plot.class.getClassLoader());
        }

        public static final Creator<Title> CREATOR = new Creator<Title>() {
            @Override
            public Title createFromParcel(Parcel in) {
                return new Title(in);
            }

            @Override
            public Title[] newArray(int size) {
                return new Title[size];
            }
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public TitleText getTitleText() {
            return titleText;
        }

        public void setTitleText(TitleText titleText) {
            this.titleText = titleText;
        }

        public ReleaseDate getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(ReleaseDate releaseDate) {
            this.releaseDate = releaseDate;
        }

        public RatingsSummary getRatingsSummary() {
            return ratingsSummary;
        }

        public void setRatingsSummary(RatingsSummary ratingsSummary) {
            this.ratingsSummary = ratingsSummary;
        }

        public Plot getPlot() {
            return plot;
        }

        public void setPlot(Plot plot) {
            this.plot = plot;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeParcelable(titleText, flags);
            dest.writeParcelable(releaseDate, flags);
            dest.writeParcelable(ratingsSummary, flags);
            dest.writeParcelable(plot, flags);
        }
    }

    public static class TitleText implements Parcelable {
        @SerializedName("text")
        private String text;

        public TitleText() {}

        protected TitleText(Parcel in) {
            text = in.readString();
        }

        public static final Creator<TitleText> CREATOR = new Creator<TitleText>() {
            @Override
            public TitleText createFromParcel(Parcel in) {
                return new TitleText(in);
            }

            @Override
            public TitleText[] newArray(int size) {
                return new TitleText[size];
            }
        };

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(text);
        }
    }

    public static class ReleaseDate implements Parcelable {
        @SerializedName("year")
        private int year;

        @SerializedName("month")
        private int month;

        @SerializedName("day")
        private int day;

        public ReleaseDate() {}

        protected ReleaseDate(Parcel in) {
            year = in.readInt();
            month = in.readInt();
            day = in.readInt();
        }

        public static final Creator<ReleaseDate> CREATOR = new Creator<ReleaseDate>() {
            @Override
            public ReleaseDate createFromParcel(Parcel in) {
                return new ReleaseDate(in);
            }

            @Override
            public ReleaseDate[] newArray(int size) {
                return new ReleaseDate[size];
            }
        };

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
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(year);
            dest.writeInt(month);
            dest.writeInt(day);
        }
    }

    public static class RatingsSummary implements Parcelable {
        @SerializedName("aggregateRating")
        private double aggregateRating;

        public RatingsSummary() {}

        protected RatingsSummary(Parcel in) {
            aggregateRating = in.readDouble();
        }

        public static final Creator<RatingsSummary> CREATOR = new Creator<RatingsSummary>() {
            @Override
            public RatingsSummary createFromParcel(Parcel in) {
                return new RatingsSummary(in);
            }

            @Override
            public RatingsSummary[] newArray(int size) {
                return new RatingsSummary[size];
            }
        };

        public double getAggregateRating() {
            return aggregateRating;
        }

        public void setAggregateRating(double aggregateRating) {
            this.aggregateRating = aggregateRating;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeDouble(aggregateRating);
        }
    }

    public static class Plot implements Parcelable {
        @SerializedName("plotText")
        private PlotText plotText;

        public Plot() {}

        protected Plot(Parcel in) {
            plotText = in.readParcelable(PlotText.class.getClassLoader());
        }

        public static final Creator<Plot> CREATOR = new Creator<Plot>() {
            @Override
            public Plot createFromParcel(Parcel in) {
                return new Plot(in);
            }

            @Override
            public Plot[] newArray(int size) {
                return new Plot[size];
            }
        };

        public PlotText getPlotText() {
            return plotText;
        }

        public void setPlotText(PlotText plotText) {
            this.plotText = plotText;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(plotText, flags);
        }
    }

    public static class PlotText implements Parcelable {
        @SerializedName("plainText")
        private String plainText;

        public PlotText() {}

        protected PlotText(Parcel in) {
            plainText = in.readString();
        }

        public static final Creator<PlotText> CREATOR = new Creator<PlotText>() {
            @Override
            public PlotText createFromParcel(Parcel in) {
                return new PlotText(in);
            }

            @Override
            public PlotText[] newArray(int size) {
                return new PlotText[size];
            }
        };

        public String getPlainText() {
            return plainText;
        }

        public void setPlainText(String plainText) {
            this.plainText = plainText;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(plainText);
        }
    }
}