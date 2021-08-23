package no.hiof.larseknu.playingwithviewmodel.screens.selection


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import kotlinx.coroutines.selects.select
import no.hiof.larseknu.playingwithviewmodel.R
import no.hiof.larseknu.playingwithviewmodel.databinding.FragmentMovieSelectionBinding
import no.hiof.larseknu.playingwithviewmodel.model.Movie

/**
 * A simple [Fragment] subclass.
 */
class MovieSelectionFragment : Fragment() {
    private lateinit var currentMovie : Movie

    private var numberAnswered = 0
    private var numberSeen = 0

    private lateinit var movieList : MutableList<Movie>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_movie_selection, container, false)

        return view
    }

}
