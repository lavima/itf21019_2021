package no.hiof.larseknu.playingwithfirebase


import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentChange.Type
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.fragment_movie_list.*
import no.hiof.larseknu.playingwithfirebase.adapter.MovieAdapter
import no.hiof.larseknu.playingwithfirebase.model.Movie
import java.util.ArrayList


class FavouriteMovieListFragment : Fragment() {
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var authStateListener : FirebaseAuth.AuthStateListener

    private var movieList: MutableList<Movie> = ArrayList()
    private var movieUidList: MutableList<String> = ArrayList()

    private lateinit var movieAdapter: MovieAdapter

    private lateinit var firestoreDb: FirebaseFirestore
    private lateinit var movieCollectionReference: CollectionReference
    private lateinit var fireStoreListenerRegistration: ListenerRegistration


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        // Create a reference to the firebase instances
        firebaseAuth = FirebaseAuth.getInstance()
        firestoreDb = FirebaseFirestore.getInstance()

        movieCollectionReference = firestoreDb.collection("movies")
        // Only call this once, it's only to fill the database with the initial data
        // Or else you'll get duplicates
        //generateTestData()

        createAuthenticationListener()

        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
    }

    override fun onResume() {
        super.onResume()

        firebaseAuth.addAuthStateListener(authStateListener)

        createFireStoreReadListener()
    }

    override fun onPause() {
        super.onPause()

        firebaseAuth.removeAuthStateListener(authStateListener)

        fireStoreListenerRegistration.remove()
    }

    fun createFireStoreReadListener() {
        // If you only want to retrieve data once, without updates later on
        // you can use an onCompleteListener on the collectionreference
        /*movieCollectionReference.get().addOnCompleteListener{ task ->
            if (task.isSuccessful) {
                for (documentSnapShot in task.result!!) {
                    val movie = documentSnapShot.toObject(Movie::class.java)
                    movie.uid = documentSnapShot.id
                    movieList.add(movie)
                    movieUidList.add(movie.uid)
                }
                movieAdapter.notifyDataSetChanged()
            }
            else {
                Log.d(LOGTAG, "Error getting documents: " + task.exception)
            }
        }*/

        // If you want to receive updates to the data, add a listener
        fireStoreListenerRegistration = movieCollectionReference.addSnapshotListener { querySnapshot, exception ->
            if (exception != null) {
                Log.w(LOGTAG, "Listen failed.", exception)
                return@addSnapshotListener
            }

            for (documentChange in querySnapshot?.documentChanges!!) {
                val documentSnapshot = documentChange.document
                val movie = documentSnapshot.toObject(Movie::class.java)
                movie.uid = documentSnapshot.id

                val pos = movieUidList.indexOf(movie.uid)

                when (documentChange.type) {
                    Type.ADDED -> {
                        movieList.add(movie)
                        movieUidList.add(movie.uid)
                        movieAdapter.notifyItemInserted(movieList.size-1)
                    }
                    Type.REMOVED -> {
                        movieList.removeAt(pos)
                        movieUidList.removeAt(pos)
                        movieAdapter.notifyItemRemoved(pos)
                    }
                    Type.MODIFIED -> {
                        movieList[pos] = movie
                        movieAdapter.notifyItemChanged(pos)
                    }
                }
            }


        }
    }

    // region Authentication
    private fun createAuthenticationListener() {
        authStateListener = FirebaseAuth.AuthStateListener {
            val firebaseUser = firebaseAuth.currentUser
            if (firebaseUser == null) {
                startActivityForResult(
                    AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders( arrayListOf(
                            AuthUI.IdpConfig.GoogleBuilder().build(),
                            AuthUI.IdpConfig.EmailBuilder().build()))
                        .setIsSmartLockEnabled(false)
                        .build(), RC_SIGN_IN
                )
            }
            else {
                userNameText.text = "Signed in as " + firebaseUser?.displayName
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                val user = firebaseAuth.currentUser
                Toast.makeText(context, "Signed in as " + user?.displayName, Toast.LENGTH_SHORT).show()
                userNameText.text = "Signed in as " + user?.displayName
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(context, "Sign in canceled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.signOut -> {
                AuthUI.getInstance().signOut(context!!)
                return true
            }
        }

        return false
    }

    // endregion


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
            movieCollectionReference.add(aMovie)
        }
    }

    companion object {
        const val RC_SIGN_IN = 1
        private val LOGTAG = MainActivity::class.java.simpleName
    }
}
