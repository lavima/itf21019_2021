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
    private lateinit var binding: FragmentMovieSelectionBinding

    private lateinit var viewModel: MovieSelectionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_selection, container, false)


        Log.i("SelectionFragment", "Called ViewModelProviders.of")
        viewModel = ViewModelProviders.of(this).get(MovieSelectionViewModel::class.java)
        binding.selectionViewModel = viewModel
        binding.lifecycleOwner = this


        // We don't need these when we have set up databinding between ViewModel and View
        // And have set the binding.lifecycleOwner to this fragment
        /*viewModel.numberSeen.observe(this, Observer {newNumberSeen ->
            binding.numberSeenOfAnsweredText.text = getString(R.string.number_seen_of_answered, newNumberSeen, viewModel.numberAnswered.value)
        })

        viewModel.numberAnswered.observe(this, Observer {newNumberAnswered ->
            binding.numberSeenOfAnsweredText.text = getString(R.string.number_seen_of_answered, viewModel.numberSeen.value, newNumberAnswered)
        })

        viewModel.currentMovie.observe(this, Observer { newMovie ->
            binding.movieTitle.text = newMovie.title
            binding.moviePoster.setImageResource(newMovie.posterUrl)
        })*/

        viewModel.eventSelectionDone.observe(this, Observer {selectionDone ->
            if (selectionDone)
                movieSelectionFinished()
        })

        // Not needed when we have set it up with binding to the ViewModel
        /*binding.seenButton.setOnClickListener { seenMovie() }
        binding.notSeenButton.setOnClickListener { notSeenMovie() }
        binding.doneButton.setOnClickListener { movieSelectionFinished() }*/

        // Not needed with observer
        //updateMovieInUI()

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun movieSelectionFinished() {
        Toast.makeText(activity, "Movie selection done", Toast.LENGTH_SHORT).show()
        val action = MovieSelectionFragmentDirections.actionMovieSelectionToSummary()
        action.numberAnswered = viewModel.numberAnswered.value?:0
        action.numberSeen = viewModel.numberSeen.value?:0
        NavHostFragment.findNavController(this).navigate(action)
        viewModel.onSelectionDoneReset()
    }

    /**
     * These click-handler methods are not needed when we have set it up with binding to the ViewModel
     */
    /*
    private fun seenMovie() {
    viewModel.seenMovie()
    //updateMovieInUI()
    }

    private fun notSeenMovie() {
    viewModel.notSeenMovie()
    //updateMovieInUI()
    }*/

    /**
    * These are not needed when we use an observer
    */
    /*
    private fun updateMovieInUI() {
    updatePoster()
    updateTitle()
    updateSeenText()
    }

    private fun updateSeenText() {
    binding.numberSeenOfAnsweredText.text = getString(R.string.number_seen_of_answered, viewModel.numberSeen.value, viewModel.numberAnswered.value)
    }*

    private fun updateTitle() {
    binding.movieTitle.text = viewModel.currentMovie.value?.title
    }

    private fun updatePoster() {
    binding.moviePoster.setImageResource(viewModel.currentMovie.value?.posterUrl!!)
    }

    */
}
