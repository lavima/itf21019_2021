package no.hiof.larseknu.playingwithrecyclerview


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_movie_list.*
import no.hiof.larseknu.playingwithrecyclerview.adapter.MovieAdapter
import no.hiof.larseknu.playingwithrecyclerview.model.Movie

class MovieListFragment : Fragment() {
    private var movieList : ArrayList<Movie> = Movie.getMovies()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecycleView()
    }

    private fun setUpRecycleView() {
        // Set our own adapter to be used in the RecycleView, and sends it the data and creates the OnClickListener
        // With the listener gets called when an item in the list is clicked
        movieRecyclerView.adapter = MovieAdapter(movieList,
            View.OnClickListener { view ->
                // Gets the position of the item that's clicked
                val position = movieRecyclerView.getChildAdapterPosition(view)

                // Gets the movie based on which item got clicked
                val clickedMovie = movieList[position]

                // Creates the navigation action, including the uid argument
                val action = MovieListFragmentDirections.actionMovieListToMovieDetailFragment(clickedMovie.uid)

                // Calls the navigat action, taking us to the MovieDetailFragment
                findNavController().navigate(action)

                // Creates a toast with the movie that got clicked
                Toast.makeText(view.context, clickedMovie.title + " clicked", Toast.LENGTH_LONG).show();
            })

        // Sets the layoutmanager we want to use
        movieRecyclerView.layoutManager = GridLayoutManager(context, 3)
        //movieRecyclerView.layoutManager = GridLayoutManager(context, 2)
        //movieRecyclerView.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)
        //movieRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)
        //movieRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        //movieRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}
