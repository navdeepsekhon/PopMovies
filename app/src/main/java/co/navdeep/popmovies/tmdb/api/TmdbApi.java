package co.navdeep.popmovies.tmdb.api;

import co.navdeep.popmovies.tmdb.model.DiscoverMovieResponse;
import co.navdeep.popmovies.tmdb.model.MovieResponse;
import co.navdeep.popmovies.tmdb.model.MovieVideosResponse;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Navdeep Sekhon on 12/19/2015.
 * www.navdeep.co
 */
public interface TmdbApi {

    @GET("3/movie/popular")
    Call<DiscoverMovieResponse> getPopularMovies(@Query("api_key") String api_key);

    @GET("3/movie/top_rated")
    Call<DiscoverMovieResponse> getTopRatedMovies(@Query("api_key") String api_key);

    @GET("3/movie/upcoming")
    Call<DiscoverMovieResponse> getUpcomingMovies(@Query("api_key") String api_key);

    @GET("3/movie/now_playing")
    Call<DiscoverMovieResponse> getNowPlayingMovies(@Query("api_key") String api_key);

    @GET("3/movie/{id}")
    Call<MovieResponse> getMovieDetails(@Path("id") String id, @Query("api_key") String api_key);

    @GET("3/movie/{id}/videos")
    Call<MovieVideosResponse> getMovieVideos(@Path("id") String id, @Query("api_key") String api_key);
}
