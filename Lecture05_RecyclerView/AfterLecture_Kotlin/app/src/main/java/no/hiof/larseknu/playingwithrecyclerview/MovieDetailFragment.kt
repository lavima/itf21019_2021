package no.hiof.larseknu.playingwithrecyclerview


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import kotlinx.android.synthetic.main.movie_list_item.*
import kotlinx.android.synthetic.main.movie_list_item.moviePosterImageView
import kotlinx.android.synthetic.main.movie_list_item.movieTitleTextView
import no.hiof.larseknu.playingwithrecyclerview.model.Movie

/**
 * A simple [Fragment] subclass.
 */
class MovieDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retreives the arguments from the bundle (does some null-check)
        val arguments = arguments?.let { MovieDetailFragmentArgs.fromBundle(it) }

        // Gets the movie with the uid (just a number in the list in this case)
        val movie = Movie.getMovies()[arguments!!.uid]

        // Filles up the views with the movie-information
        movieTitleTextView.text = movie.title
        moviePosterImageView.setImageResource(movie.posterUrl)
        movieDescriptionTextView.text = movie.description
    }
}
