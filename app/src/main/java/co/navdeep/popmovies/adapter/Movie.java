package co.navdeep.popmovies.adapter;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import co.navdeep.popmovies.data.PopMoviesContract;
import co.navdeep.popmovies.tmdb.model.Result;
import co.navdeep.popmovies.utils.PopMoviesConstants;
import co.navdeep.popmovies.utils.Utility;

/**
 * Created by Navdeep Sekhon on 12/18/2015.
 * www.navdeep.co
 */
public class Movie implements Parcelable{
    private String id;
    private String posterUrl;
    private String overview;
    private String title;
    private String averageRating;
    private String year;

    public Movie(String id, String posterUrl, String overview, String title) {
        this.id = id;
        this.posterUrl = posterUrl;
        this.overview = overview;
        this.title = title;
    }

    public Movie(Result result){
        this.id = Integer.toString(result.getId());
        this.posterUrl = Utility.getPosterUrl(result.getPosterPath());
        this.overview = result.getOverview();
        this.title = result.getTitle();
        this.averageRating = String.format(PopMoviesConstants.MOVIE_RATING_STRING, result.getVoteAverage());
        this.year = result.getReleaseDate().substring(0, 4);

    }
    public Movie(){
        super();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public String getId() {

        return id;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getOverview() {
        return overview;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public ContentValues toContentValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(PopMoviesContract.FavouriteEntry.COLUMN_MOVIE_ID, id);
        contentValues.put(PopMoviesContract.FavouriteEntry.COLUMN_MOVIE_DESC, overview);
        contentValues.put(PopMoviesContract.FavouriteEntry.COLUMN_MOVIE_POSTER_URL, posterUrl);
        contentValues.put(PopMoviesContract.FavouriteEntry.COLUMN_MOVIE_RATING, averageRating);
        contentValues.put(PopMoviesContract.FavouriteEntry.COLUMN_MOVIE_YEAR, year);
        contentValues.put(PopMoviesContract.FavouriteEntry.COLUMN_MOVIE_TITLE, title);
        return contentValues;
    }
    public Movie(Parcel parcel){
        id = parcel.readString();
        posterUrl = parcel.readString();
        overview = parcel.readString();
        title = parcel.readString();
        averageRating = parcel.readString();
        year = parcel.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(posterUrl);
        dest.writeString(overview);
        dest.writeString(title);
        dest.writeString(averageRating);
        dest.writeString(year);
    }

    public static Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
