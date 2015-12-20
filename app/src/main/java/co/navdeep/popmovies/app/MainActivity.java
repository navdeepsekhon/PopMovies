package co.navdeep.popmovies.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import co.navdeep.popmovies.adapter.Movie;
import co.navdeep.popmovies.R;
import co.navdeep.popmovies.utils.PopMoviesConstants;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.MainActivityFragmentCallBack{

    private boolean mTablet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (findViewById(R.id.fragment_detail) != null) {
            mTablet = true;
        } else {
            mTablet = false;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_order, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onSortOrderChange(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    private void onSortOrderChange(int order){
        MainActivityFragment mf = (MainActivityFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_main);
        if(mf == null)
            return;
        if(order == 0)
            mf.loadMostPopularMovies();
        else if(order == 1)
            mf.loadTopRatedMovies();
        else if(order == 2)
            mf.loadNowPlayingMovies();
        else if(order == 3)
            mf.loadUpcomingMovies();
        else if(order == 4)
            mf.loadFavouriteMovies();
    }

    @Override
    public void onItemSelected(Movie movie) {
        if(!mTablet) {
            Intent detailIntent = new Intent(this, DetailActivity.class);
            detailIntent.putExtra(PopMoviesConstants.INTENT_KEY_MOVIE, movie);
            startActivity(detailIntent);
        }else{
            Bundle args = new Bundle();
            args.putParcelable(PopMoviesConstants.INTENT_KEY_MOVIE, movie);
            DetailFragment df = new DetailFragment();
            df.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_detail, df).commit();
        }
    }
}
