package co.navdeep.popmovies.utils;


/**
 * Created by Navdeep Sekhon on 12/18/2015.
 * www.navdeep.co
 */
public class Utility {
    public static String getPosterUrl(String relativePath) {
        return String.format(PopMoviesConstants.TMDB_POSTER_BASE_URL, PopMoviesConstants.TMDB_POSTER_SIZE, relativePath);
    }

    public static String getFormattedRuntime(int runtime){
        return String.format(PopMoviesConstants.MOVIE_RUNTIME_STRING, runtime);
    }

    public static String getYoutubeUrlFromVideoId(String id){
        return String.format(PopMoviesConstants.YOUTUBE_URL_STRING, id);
    }
}
