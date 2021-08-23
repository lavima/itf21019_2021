package no.hiof.larseknu.playingwithfirebase


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import kotlinx.android.synthetic.main.fragment_movie_list.*
import no.hiof.larseknu.playingwithfirebase.model.Movie

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

        val movieUid = MovieDetailFragmentArgs.fromBundle(arguments!!).movieuid

        val documentReference =  FirebaseFirestore.getInstance().collection("movies").document(movieUid)

        // If you only want to retrieve data once, without updates later on
        // you can use an onCompleteListener on the collection or documentReference
        documentReference.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val documentSnapshot = task.result!!
                val movie = documentSnapshot.toObject<Movie>(Movie::class.java)!!
                movie.uid = documentSnapshot.id

                movieTitleTextView.text = movie.title
                movieYearTextView.text = movie.releaseDate
                summaryTextView.text = movie.description

                if (movie.posterUrl.isNotEmpty()) {
                    Glide.with(this)
                        .load(movie.posterUrl)
                        .into(moviePosterImageView)
                }

            } else {
                Log.d("MovieDetailFragment", "Get failed with", task.exception)
            }
        }
    }
}
