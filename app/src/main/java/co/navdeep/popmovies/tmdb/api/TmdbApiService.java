package co.navdeep.popmovies.tmdb.api;

import java.util.Calendar;

import co.navdeep.popmovies.tmdb.model.DiscoverMovieResponse;
import co.navdeep.popmovies.tmdb.model.MovieResponse;
import co.navdeep.popmovies.tmdb.model.MovieVideosResponse;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Navdeep Sekhon on 12/19/2015.
 * www.navdeep.co
 */
public class TmdbApiService {
    private static final String TMDB_API_KEY ="YOUR_API_KEY_HERE";
    private static final String TMDB_BASE_URL ="http://api.themoviedb.org";
    private TmdbApi api;

    public TmdbApiService(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(TMDB_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        api = retrofit.create(TmdbApi.class);
    }

    public void getMostPopularMovies( Callback<DiscoverMovieResponse> callBack){
        Call<DiscoverMovieResponse> call = api.getPopularMovies(TMDB_API_KEY);
        call.enqueue(callBack);
    }

    public void getNowPlayingMovies( Callback<DiscoverMovieResponse> callBack){
        Call<DiscoverMovieResponse> call = api.getNowPlayingMovies(TMDB_API_KEY);
        call.enqueue(callBack);
    }

    public void getTopRatedMovies( Callback<DiscoverMovieResponse> callBack){
        Call<DiscoverMovieResponse> call = api.getTopRatedMovies(TMDB_API_KEY);
        call.enqueue(callBack);
    }

    public void getUpcomingMovies( Callback<DiscoverMovieResponse> callBack){
        Call<DiscoverMovieResponse> call = api.getUpcomingMovies(TMDB_API_KEY);
        call.enqueue(callBack);
    }

    public void getMovieDetails(String id, Callback<MovieResponse> callback){
        Call<MovieResponse> call = api.getMovieDetails(id, TMDB_API_KEY);
        call.enqueue(callback);
    }

    public void getMovieVideos(String id, Callback<MovieVideosResponse> callback){
        Call<MovieVideosResponse> call = api.getMovieVideos(id, TMDB_API_KEY);
        call.enqueue(callback);
    }
}
