package com.clase.engenios_manuelimdbapp.models;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMoviesDatabase extends SQLiteOpenHelper {

    // Nombre de la base de datos y tabla
    private static final String DATABASE_NAME = "favorites.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "favorites";

    // Columnas de la tabla
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_MOVIE_ID = "movie_id";
    private static final String COLUMN_IMAGE_URL = "image_url";

    public FavoriteMoviesDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_MOVIE_ID + " TEXT NOT NULL, "
                + COLUMN_IMAGE_URL + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Método para insertar un registro en la tabla
    public long insertFavorite(String movieId, String imageUrl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MOVIE_ID, movieId);
        values.put(COLUMN_IMAGE_URL, imageUrl);

        return db.insert(TABLE_NAME, null, values);
    }

    public List<Movie> getAllFavorites() {
        List<Movie> favoriteMovies = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String movieId = cursor.getString(cursor.getColumnIndex(COLUMN_MOVIE_ID));
                @SuppressLint("Range") String imageUrl = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_URL));

                Movie movie = new Movie();
                movie.setId(movieId);
                movie.setImageUrl(imageUrl);
                favoriteMovies.add(movie);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return favoriteMovies;
    }


    public int deleteFavorite(String movieId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_MOVIE_ID + " = ?", new String[]{movieId});
    }

    public boolean isFavorite(String movieId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_MOVIE_ID + " = ?",
                new String[]{movieId}, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
}
