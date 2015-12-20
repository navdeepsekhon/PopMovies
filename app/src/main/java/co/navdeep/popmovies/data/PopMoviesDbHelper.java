package co.navdeep.popmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Navdeep Sekhon on 12/19/2015.
 * www.navdeep.co
 */
public class PopMoviesDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "popmovies.db";

    public PopMoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(favouritesTableCreateQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PopMoviesContract.FavouriteEntry.TABLE_NAME);
        onCreate(db);
    }

    private String favouritesTableCreateQuery(){
        final String SQL_CREATE_FAVOURITES_TABLE = "CREATE TABLE " + PopMoviesContract.FavouriteEntry.TABLE_NAME +" (" +
                PopMoviesContract.FavouriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PopMoviesContract.FavouriteEntry.COLUMN_MOVIE_ID + " TEXT UNIQUE ON CONFLICT REPLACE NOT NULL, " +
                PopMoviesContract.FavouriteEntry.COLUMN_MOVIE_POSTER_URL + " TEXT NOT NULL, " +
                PopMoviesContract.FavouriteEntry.COLUMN_MOVIE_DESC + " TEXT, " +
                PopMoviesContract.FavouriteEntry.COLUMN_MOVIE_RATING + " TEXT, " +
                PopMoviesContract.FavouriteEntry.COLUMN_MOVIE_YEAR + " TEXT, " +
                PopMoviesContract.FavouriteEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL" +
                ");";
        return SQL_CREATE_FAVOURITES_TABLE;
    }

}
