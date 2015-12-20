package co.navdeep.popmovies.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import co.navdeep.popmovies.adapter.Movie;


/**
 * Created by Navdeep Sekhon on 12/19/2015.
 * www.navdeep.co
 */
public class FavouritesDataSource {
    private SQLiteDatabase database;
    private PopMoviesDbHelper dbHelper;

    private String[] columns = {
            PopMoviesContract.FavouriteEntry.COLUMN_MOVIE_ID,
            PopMoviesContract.FavouriteEntry.COLUMN_MOVIE_DESC,
            PopMoviesContract.FavouriteEntry.COLUMN_MOVIE_YEAR,
            PopMoviesContract.FavouriteEntry.COLUMN_MOVIE_RATING,
            PopMoviesContract.FavouriteEntry.COLUMN_MOVIE_POSTER_URL,
            PopMoviesContract.FavouriteEntry.COLUMN_MOVIE_TITLE
    };

    public FavouritesDataSource(Context context){
        dbHelper = new PopMoviesDbHelper(context);
    }

    public void open(){
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public void insertFavourite(Movie movie){
        ContentValues values = movie.toContentValues();
        database.insert(PopMoviesContract.FavouriteEntry.TABLE_NAME, null, values);
    }

    public void deleteFavourite(String movieId){
        database.delete(PopMoviesContract.FavouriteEntry.TABLE_NAME, PopMoviesContract.FavouriteEntry.COLUMN_MOVIE_ID + "=" + movieId, null);
    }

    public ArrayList<Movie> getAllFavourites(){
        ArrayList<Movie> movies = new ArrayList<>();
        Cursor cursor = database.query(PopMoviesContract.FavouriteEntry.TABLE_NAME, columns, null, null, null, null, null);
        if ( cursor.moveToFirst() ) {
            do {
                movies.add(convertCursorToMovie(cursor));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return movies;
    }

    public boolean isFavourite(String movieId){
        Cursor cursor = database.query(PopMoviesContract.FavouriteEntry.TABLE_NAME, columns, PopMoviesContract.FavouriteEntry.COLUMN_MOVIE_ID + "=" + movieId, null, null, null , null);
        if(cursor.moveToFirst())
            return true;

        return false;
    }

    /*
            PopMoviesContract.FavouriteEntry.COLUMN_MOVIE_ID,
            PopMoviesContract.FavouriteEntry.COLUMN_MOVIE_DESC,
            PopMoviesContract.FavouriteEntry.COLUMN_MOVIE_YEAR,
            PopMoviesContract.FavouriteEntry.COLUMN_MOVIE_RATING,
            PopMoviesContract.FavouriteEntry.COLUMN_MOVIE_POSTER_URL
            PopMoviesContract.FavouriteEntry.COLUMN_MOVIE_TITLE
     */
    private Movie convertCursorToMovie(Cursor cursor){
        Movie movie = new Movie();
        movie.setId(cursor.getString(0));
        movie.setOverview(cursor.getString(1));
        movie.setYear(cursor.getString(2));
        movie.setAverageRating(cursor.getString(3));
        movie.setPosterUrl(cursor.getString(4));
        movie.setTitle(cursor.getString(5));
        return movie;
    }
}
