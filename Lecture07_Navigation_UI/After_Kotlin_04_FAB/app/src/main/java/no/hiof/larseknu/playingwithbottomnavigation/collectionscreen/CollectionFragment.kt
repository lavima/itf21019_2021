package no.hiof.larseknu.playingwithbottomnavigation.collectionscreen


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_collection.*
import no.hiof.larseknu.playingwithbottomnavigation.R
import no.hiof.larseknu.playingwithbottomnavigation.model.Movie

/**
 * A simple [Fragment] subclass.
 */
class CollectionFragment : Fragment() {
    private var movieList : ArrayList<Movie> = Movie.getMovies()
    private lateinit var fab : FloatingActionButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecycleView()

        fab = view.findViewById<FloatingActionButton>(R.id.floating_action_button)

        // Sets a clicklistener to the Floating Action Button
        fab.setOnClickListener {
            // Creates a snackbar
            val snackbar = Snackbar.make(view, "Adding new movie...", Snackbar.LENGTH_LONG)

            // Enables an undo action to the snacbar
            snackbar.setAction("Undo", View.OnClickListener { view ->
                // Creates and shows a new snackbar if "Undo" is pressed
                Snackbar.make(view, "Undid the creation of a new movie", Snackbar.LENGTH_SHORT).show()
            })

            // Shows the snackbar
            snackbar.show()
        }
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
                //val action = MovieListFragmentDirections.actionMovieListToMovieDetailFragment(clickedMovie.uid)

                // Calls the navigat action, taking us to the MovieDetailFragment
                //findNavController().navigate(action)

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

        // Adds a scrollListener to the RecyclerView
        // We are gonna hide the fab if we scroll down, and show it when we scroll up
        movieRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // Scrolling down - hide fab
                if (dy > 0)
                    fab.hide()
                // Scrolling up - show fab
                else if (dy < 0)
                    fab.show()
            }
        })
    }
}
