package no.hiof.larseknu.playingwithrecyclerview.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import no.hiof.larseknu.playingwithrecyclerview.R;
import no.hiof.larseknu.playingwithrecyclerview.model.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder> {

    private List<Movie> data;
    private LayoutInflater mInflater;
    private View.OnClickListener clickListener;

    public MovieRecyclerAdapter(Context context, List<Movie> data, View.OnClickListener clickListener) {
        this.data = data;
        this.mInflater = LayoutInflater.from(context);
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Log so we can see when the onCreateViewHolder method is called
        Log.d("MovieAdapter", "Creating View");

        // Inflates the movie_list_item.xml to a view for us
        View view = mInflater.inflate(R.layout.movie_list_item, parent, false);

        // Create the viewholder with the corresponding view (list item)
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        // Log so we can see when the bind method is called
        Log.d("MovieAdapter", "Binding View $position");

        // Gets the movie data we are going to use at the given position
        Movie currentMovie = data.get(position);

        // Gives the movie data and clickListener to the ViewHolder
        // Makes it fill up the given position with the new data and add the clicklistener to the view
        holder.bind(currentMovie, clickListener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView movieTitleTextView;
        ImageView moviePosterImageView;


        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            // Gets a reference to all the specific views we are going to use or fill with data
            movieTitleTextView = itemView.findViewById(R.id.movieTitleTextView);
            moviePosterImageView = itemView.findViewById(R.id.moviePosterImageView);
        }

        public void bind(Movie currentMovie, View.OnClickListener clickListener) {
            // Fills the views with the given data
            moviePosterImageView.setImageResource(currentMovie.getPosterUrl());
            movieTitleTextView.setText(currentMovie.getTitle());
            // Sets the onClickListener
            this.itemView.setOnClickListener(clickListener);
        }
    }
}
