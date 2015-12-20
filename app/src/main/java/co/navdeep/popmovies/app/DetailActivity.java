package co.navdeep.popmovies.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import co.navdeep.popmovies.adapter.Movie;
import co.navdeep.popmovies.R;
import co.navdeep.popmovies.utils.PopMoviesConstants;

/**
 * Created by Navdeep Sekhon on 12/19/2015.
 * www.navdeep.co
 */
public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if(getIntent() != null && getIntent().hasExtra(PopMoviesConstants.INTENT_KEY_MOVIE)){
            Movie movie = (Movie) getIntent().getParcelableExtra(PopMoviesConstants.INTENT_KEY_MOVIE);
            Bundle args = new Bundle();
            args.putParcelable(PopMoviesConstants.INTENT_KEY_MOVIE, movie);

            DetailFragment df = new DetailFragment();
            df.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_detail, df).commit();
        }

    }
}
