package no.hiof.larseknu.playingwithfirebase.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.movie_list_item.view.*
import no.hiof.larseknu.playingwithfirebase.R
import no.hiof.larseknu.playingwithfirebase.model.Movie

class MovieAdapter(private val items: MutableList<Movie>, var clickListener: View.OnClickListener) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    override fun getItemCount(): Int {
        return items.size
    }

    // Called when there's a need for a new ViewHolder (a new item in the list/grid)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        // Log so we can see when the onCreateViewHolder method is called
        Log.d("MovieAdapter", "Creating View")

        // Inflates the movie_list_item.xml to a view for us
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false)

        // Create the viewholder with the corresponding view (list item)
        return MovieViewHolder(itemView)
    }

    // Called when data is bound to a specific ViewHolder (and item in the list/grid)
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        // Log so we can see when the bind method is called
        Log.d("MovieAdapter", "Binding View $position")

        // Gets the movie data we are going to use at the given position
        val currentMovie = items[position]

        // Gives the movie data and clickListener to the ViewHolder
        // Makes it fill up the given position with the new data and add the clicklistener to the view
        holder.bind(currentMovie, clickListener)
    }

    class MovieViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        // Gets a reference to all the specific views we are going to use or fill with data
        private val moviePosterImageView : ImageView = view.moviePosterImageView
        private val movieTitleTextView : TextView = view.movieTitleTextView

        fun bind(item: Movie, clickListener: View.OnClickListener) {
            if (item.posterUrl.isNotEmpty()) {
                Glide.with(moviePosterImageView.getContext())
                    .load(item.posterUrl)
                    .into(moviePosterImageView)
            } else
                moviePosterImageView.setImageResource(R.drawable.poster_placeholder)

            // Fills the views with the given data
            movieTitleTextView.text = item.title
            // Sets the onClickListener
            this.itemView.setOnClickListener(clickListener)
        }
    }
}