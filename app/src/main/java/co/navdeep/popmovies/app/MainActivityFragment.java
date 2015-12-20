package co.navdeep.popmovies.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import co.navdeep.popmovies.adapter.Movie;
import co.navdeep.popmovies.adapter.MovieGridAdapter;
import co.navdeep.popmovies.R;
import co.navdeep.popmovies.data.FavouritesDataSource;
import co.navdeep.popmovies.tmdb.api.TmdbApiService;
import co.navdeep.popmovies.tmdb.model.DiscoverMovieResponse;
import co.navdeep.popmovies.tmdb.model.Result;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements Callback<DiscoverMovieResponse>{
    private final String TAG = getClass().getSimpleName();
    private TmdbApiService tmdbApiService;
    private MovieGridAdapter mMovieAdapter;
    private FavouritesDataSource mDatabase;

    private GridView mGrid;
    private MainActivityFragmentCallBack mCallback;

    public MainActivityFragment() {
    }

    public interface MainActivityFragmentCallBack{
        public void onItemSelected(Movie movie);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mCallback = (MainActivityFragmentCallBack) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement MainActivityFragmentCallBack for MainActivityFragment");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mDatabase.open();
    }

    @Override
    public void onPause() {
        super.onPause();
        mDatabase.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview =  inflater.inflate(R.layout.fragment_main, container, false);
        mDatabase = new FavouritesDataSource(getActivity());
        mDatabase.open();

        mGrid = (GridView)rootview;
        mMovieAdapter = new MovieGridAdapter(getActivity(), R.layout.grid_item_movie, new ArrayList<Movie>());
        mGrid.setAdapter(mMovieAdapter);
        mGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = (Movie)view.getTag();
                mCallback.onItemSelected(movie);
            }
        });
        tmdbApiService = new TmdbApiService();
        loadMostPopularMovies();
        return rootview;
    }

    public void loadMostPopularMovies(){
        tmdbApiService.getMostPopularMovies(this);
    }


    public void loadNowPlayingMovies(){
        tmdbApiService.getNowPlayingMovies(this);
    }


    public void loadTopRatedMovies(){
        tmdbApiService.getTopRatedMovies(this);
    }


    public void loadUpcomingMovies(){
        tmdbApiService.getUpcomingMovies(this);
    }


    public void loadFavouriteMovies(){
        ArrayList<Movie> movies = mDatabase.getAllFavourites();
        updateAdapter(movies);
    }

    private void updateAdapter(ArrayList<Movie> movies){
        mMovieAdapter.clear();
        mMovieAdapter.addAll(movies);
    }

    @Override
    public void onResponse(Response<DiscoverMovieResponse> response, Retrofit retrofit) {
        DiscoverMovieResponse movieResponse = response.body();
        ArrayList<Movie> movies = new ArrayList<Movie>();
        for(Result result : movieResponse.getResults()){
            movies.add(new Movie(result));
        }
        updateAdapter(movies);
    }

    @Override
    public void onFailure(Throwable t) {
        Log.e(TAG, "Error while fetching movies", t);
    }
}
