package co.navdeep.popmovies.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import co.navdeep.popmovies.adapter.Movie;
import co.navdeep.popmovies.R;
import co.navdeep.popmovies.data.FavouritesDataSource;
import co.navdeep.popmovies.tmdb.api.TmdbApiService;
import co.navdeep.popmovies.tmdb.model.MovieResponse;
import co.navdeep.popmovies.tmdb.model.MovieVideosResponse;
import co.navdeep.popmovies.tmdb.model.MovieVideosResult;
import co.navdeep.popmovies.utils.PopMoviesConstants;
import co.navdeep.popmovies.utils.Utility;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Navdeep Sekhon on 12/19/2015.
 * www.navdeep.co
 */
public class DetailFragment extends Fragment implements Callback<MovieResponse>{
    private  final String TAG = getClass().getSimpleName();
    private TmdbApiService mApiService;
    private TextView mDuration;
    LinearLayout mRootView;
    LayoutInflater mInflater;
    FavouritesDataSource mDatabase;
    Button mAddToFavouriteBtn;
    Button mRemoveFromFavouriteBtn;

    @Override
    public void onPause() {
        super.onPause();
        mDatabase.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        mDatabase.open();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mInflater = inflater;
        mDatabase = new FavouritesDataSource(getActivity());
        mDatabase.open();

        View rootView =  inflater.inflate(R.layout.fragment_detail, container, false);
        TextView title = (TextView)rootView.findViewById(R.id.detail_movie_title);
        ImageView poster = (ImageView) rootView.findViewById(R.id.detail_movie_poster);
        TextView overview = (TextView) rootView.findViewById(R.id.detail_movie_description);
        TextView rating = (TextView) rootView.findViewById((R.id.detail_movie_rating));
        TextView year = (TextView) rootView.findViewById(R.id.detail_movie_year);
        mDuration = (TextView) rootView.findViewById(R.id.detail_movie_duration);
        mAddToFavouriteBtn = (Button) rootView.findViewById(R.id.detail_add_to_favourite_button);
        mRemoveFromFavouriteBtn = (Button) rootView.findViewById(R.id.detail_remove_from_favourite_button);

        if(getArguments() != null && getArguments().containsKey(PopMoviesConstants.INTENT_KEY_MOVIE)){
            final Movie movie = (Movie)getArguments().getParcelable(PopMoviesConstants.INTENT_KEY_MOVIE) ;
            title.setText(movie.getTitle());
            overview.setText(movie.getOverview());
            rating.setText(movie.getAverageRating());
            year.setText(movie.getYear());
            mApiService = new TmdbApiService();
            mApiService.getMovieDetails(movie.getId(), this);
            Picasso.with(getActivity()).load(movie.getPosterUrl()).into(poster);

            setFavouriteBtnVisibility(movie.getId());

            addListenerToFavouriteButtons(movie);

            mApiService.getMovieVideos(movie.getId(), new Callback<MovieVideosResponse>() {
                @Override
                public void onResponse(Response<MovieVideosResponse> response, Retrofit retrofit) {
                    for(MovieVideosResult trailer : response.body().getResults()) {
                        if ("youtube".equalsIgnoreCase(trailer.getSite()))
                            addTrailerLink(trailer);
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e(TAG, "Failed to fetch movie videos from tmdb", t);
                }
            });
        }

        mRootView = (LinearLayout)rootView.findViewById(R.id.detail_root_linearlayout);

        return rootView;
    }

    private void addListenerToFavouriteButtons(final Movie movie) {
        mAddToFavouriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFavourites(movie);
            }
        });

        mRemoveFromFavouriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFromFavourites(movie.getId());
            }
        });
    }


    private void setFavouriteBtnVisibility(String movieId){
        if(mDatabase.isFavourite(movieId)){
            mAddToFavouriteBtn.setVisibility(View.GONE);
            mRemoveFromFavouriteBtn.setVisibility(View.VISIBLE);
        } else{
            mAddToFavouriteBtn.setVisibility(View.VISIBLE);
            mRemoveFromFavouriteBtn.setVisibility(View.GONE);
        }
    }
    private void addToFavourites(Movie movie){
        mDatabase.insertFavourite(movie);
        mAddToFavouriteBtn.setVisibility(View.GONE);
        mRemoveFromFavouriteBtn.setVisibility(View.VISIBLE);
    }

    private void removeFromFavourites(String movieId){
        mDatabase.deleteFavourite(movieId);
        mRemoveFromFavouriteBtn.setVisibility(View.GONE);
        mAddToFavouriteBtn.setVisibility(View.VISIBLE);
    }

    private void addTrailerLink(final MovieVideosResult trailer){
        LinearLayout link = (LinearLayout)mInflater.inflate(R.layout.list_item_trailer, null);
        TextView text = (TextView)link.findViewById(R.id.detail_trailer_title);
        text.setText(trailer.getName());
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watchYoutubeVideo(trailer.getKey());
            }
        });
        mRootView.addView(link);

    }

    public  void watchYoutubeVideo(String id){
        Intent intent=new Intent(Intent.ACTION_VIEW,
                Uri.parse(Utility.getYoutubeUrlFromVideoId(id)));
        getActivity().startActivity(intent);

    }
    @Override
    public void onResponse(Response<MovieResponse> response, Retrofit retrofit) {
        mDuration.setText(Utility.getFormattedRuntime(response.body().getRuntime()));
    }

    @Override
    public void onFailure(Throwable t) {
        Log.e(TAG, "Failed to fetch movie details from tmdb", t);
    }

}
