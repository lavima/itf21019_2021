package no.hiof.larseknu.playingwithfirebase


import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_movie_list.*
import no.hiof.larseknu.playingwithfirebase.adapter.MovieAdapter
import no.hiof.larseknu.playingwithfirebase.model.Movie
import java.util.*


class FavouriteMovieListFragment : Fragment() {
    private var movieList: MutableList<Movie> = ArrayList()
    private var movieUidList: MutableList<String> = ArrayList()

    private lateinit var movieAdapter: MovieAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }




    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.signOut -> {
                return true
            }
        }

        return false
    }


    private fun setUpRecyclerView() {
        movieAdapter = MovieAdapter(movieList, View.OnClickListener{ view ->
            val position = movieRecyclerView.getChildAdapterPosition(view)

            val movie = movieList[position]

            Toast.makeText(context, "Movie clicked " + movie.title, Toast.LENGTH_SHORT).show()

            val action = FavouriteMovieListFragmentDirections.actionGoToMovieDetail(movieuid = movie.uid)

            findNavController().navigate(action)
        })

        movieRecyclerView.adapter = movieAdapter
        movieRecyclerView.layoutManager = GridLayoutManager(context, 3)
    }

    fun generateTestData() {
        val movies = ArrayList<Movie>()
        movies.add(Movie("Iron Man 3", "When Tony Stark's world is torn apart by a formidable terrorist called the Mandarin, he starts an odyssey of rebuilding and retribution.", "2013-04-18"));
        movies.add(Movie("Donnie Darko", "A troubled teenager is plagued by visions of a man in a large rabbit suit who manipulates him to commit a series of crimes, after he narrowly escapes a bizarre accident.", "2001-10-26"));
        movies.add(Movie("Pulp Fiction", "A burger-loving hit man, his philosophical partner, a drug-addled gangster's moll and a washed-up boxer converge in this sprawling, comedic crime caper. Their adventures unfurl in three stories that ingeniously trip back and forth in time.", "1994-10-14"));
        movies.add(Movie("Spirited Away", "During her family's move to the suburbs, a sullen 10-year-old girl wanders into a world ruled by gods, witches, and spirits, and where humans are changed into beasts.", "2001-06-20"));
        movies.add(Movie("Star Wars: The Force Awakens", "Thirty years after defeating the Galactic Empire, Han Solo and his allies face a new threat from the evil Kylo Ren and his army of Stormtroopers.", "2015-12-14"));
        movies.add(Movie("Up", "Seventy-eight year old Carl Fredricksen travels to Paradise Falls in his home equipped with balloons, inadvertently taking a young stowaway.", "2009-09-25"));
        movies.add(Movie("The Hobbit", "Bilbo Baggins, a hobbit enjoying his quiet life, is swept into an epic quest by Gandalf the Grey and thirteen dwarves who seek to reclaim their mountain home from Smaug, the dragon.", "2012-12-06"));
        movies.add(Movie("Captain America: Civil War", "Following the events of Age of Ultron, the collective governments of the world pass an act designed to regulate all superhuman activity. This polarizes opinion amongst the Avengers, causing two factions to side with Iron Man or Captain America, which causes an epic battle between former allies.", "2016-04-12"));
        movies.add(Movie("Deadpool", "Deadpool tells the origin story of former Special Forces operative turned mercenary Wade Wilson, who after being subjected to a rogue experiment that leaves him with accelerated healing powers, adopts the alter ego Deadpool. Armed with his new abilities and a dark, twisted sense of humor, Deadpool hunts down the man who nearly destroyed his life.", "2016-02-12"));


        for (aMovie in movies) {

        }
    }

    companion object {
        const val RC_SIGN_IN = 1
        private val LOGTAG = MainActivity::class.java.simpleName
    }
}
