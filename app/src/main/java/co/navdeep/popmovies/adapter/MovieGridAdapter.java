package co.navdeep.popmovies.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import co.navdeep.popmovies.R;

/**
 * Created by Navdeep Sekhon on 12/18/2015.
 * www.navdeep.co
 */
public class MovieGridAdapter extends ArrayAdapter<Movie> {

    private Context mContext;
    public MovieGridAdapter(Context context, int resource, List<Movie> objects) {
        super(context, resource, objects);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.grid_item_movie, null);
        }

        Picasso.with(mContext).load(movie.getPosterUrl()).placeholder(R.mipmap.ic_launcher).into((ImageView)convertView.findViewById(R.id.movie_image));
        convertView.setTag(movie);

        return convertView;
    }
}
