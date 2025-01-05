package com.clase.engenios_manuelimdbapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Manuel
 * @version 1.0*/

public class Movie implements Parcelable {
    // Declaro todas las variables necesarias
    private String id; // Id de la película
    private String title; // Titulo de la película
    private String originalTitle; // Título original de la película
    private String posterPath; // Url de la portada de la película
    private String releaseDate; // Fecha de publicación de la película
    private String descripcion; // Descripción de la película
    private String valoracion; // Valoración de la película

    /**
     * Constructor vacio*/
    public Movie() {
    }

    /**
     * @param releaseDate
     * @param imageUrl
     * @param id
     * @param title
     * @param originalTitle
     * Constructor con todas las variables de la clase*/
    public Movie(String id, String title, String originalTitle, String imageUrl, String releaseDate) {
        this.id = id;
        this.title = title;
        this.originalTitle = originalTitle;
        this.posterPath = imageUrl;
        this.releaseDate = releaseDate;
    }

    /**
     * @param in
     * Clase protegida que nos permite leer el objeto parceado*/
    protected Movie(Parcel in) {
        id = in.readString();
        title = in.readString();
        originalTitle = in.readString();
        posterPath = in.readString();
        releaseDate = in.readString();
        descripcion = in.readString();
        valoracion = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    /**
     * @return
     * Método para obtener el valor del id*/
    public String getId() {
        return id;
    }

    /**
     * @param id
     * Método para establecer el valor del id*/
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return
     * Método para obtener el título*/
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     * Método para establecer el valor del título*/
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return
     * Método para obtener el título original*/
    public String getOriginalTitle() {
        return originalTitle;
    }

    /**
     * @param originalTitle
     * Método para establecer el valor del título original*/
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    /**
     * @return
     * Método para obtener la url de la portada*/
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * @param posterPath
     * Método para establecer el valor de la url de la portada*/
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    /**
     * @return
     * Método para obtener el valor de la fecha de publicación*/
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * @param releaseDate
     * Método para establecer el valor a la fecha de publicación*/
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getValoracion() {
        return valoracion;
    }

    public void setValoracion(String valoracion) {
        this.valoracion = valoracion;
    }

    /**
     * @param node
     * Método para actualizar los valores de la clase de tipo Movie basandonos en la información
     * que obtenemos de la clase de tipo PopularMovieResponse, para posibles actualizaciones de datos
     * de la clase Movie*/
    public void updateFromNode(PopularMovieResponse.Node node) {
        this.title = node.getTitleText().getText();
        this.originalTitle = node.getTitleText().getText();
        this.posterPath = node.getPrimaryImage().getUrl();
        this.releaseDate = String.valueOf(node.getReleaseDate().getYear())+"/"+String.valueOf(node.getReleaseDate().getMonth())+"/"+
                node.getReleaseDate().getDay();
    }

    /**
     * @return
     * Método de los objetos parceables que nos permite describir que tipo de contenido
     * vamos a escribir y pasar en un parceable*/
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * @param dest
     * @param flags
     * Método para poder escribir el objeto parceado*/
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(originalTitle);
        dest.writeString(posterPath);
        dest.writeString(releaseDate);
        dest.writeString(descripcion);
        dest.writeString(valoracion);
    }

    // Clase que creo y uso para utiliza el titulo de la película
    public static class TitleText implements Parcelable {
        // Variable que contiene el nombre de la película
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

        /**
         * @return
         * Método para obtener el valor del nombre de la película*/
        public String getText() {
            return text;
        }

        /**
         * @param text
         * Método para establecer el nombre de la película*/
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

    public static class PrimaryImage implements Parcelable {
        private String url;

        public PrimaryImage() {}

        protected PrimaryImage(Parcel in) {
            url = in.readString();
        }

        public static final Creator<PrimaryImage> CREATOR = new Creator<PrimaryImage>() {
            @Override
            public PrimaryImage createFromParcel(Parcel in) {
                return new PrimaryImage(in);
            }

            @Override
            public PrimaryImage[] newArray(int size) {
                return new PrimaryImage[size];
            }
        };

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(url);
        }
    }

    public static class MeterRanking implements Parcelable {
        private int currentRank;

        public MeterRanking() {}

        protected MeterRanking(Parcel in) {
            currentRank = in.readInt();
        }

        public static final Creator<MeterRanking> CREATOR = new Creator<MeterRanking>() {
            @Override
            public MeterRanking createFromParcel(Parcel in) {
                return new MeterRanking(in);
            }

            @Override
            public MeterRanking[] newArray(int size) {
                return new MeterRanking[size];
            }
        };

        public int getCurrentRank() {
            return currentRank;
        }

        public void setCurrentRank(int currentRank) {
            this.currentRank = currentRank;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(currentRank);
        }
    }
}