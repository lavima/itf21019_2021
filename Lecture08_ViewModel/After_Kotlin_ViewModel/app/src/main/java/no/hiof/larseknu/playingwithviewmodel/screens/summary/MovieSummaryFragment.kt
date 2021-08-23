package no.hiof.larseknu.playingwithviewmodel.screens.summary


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavHostController
import androidx.navigation.fragment.NavHostFragment
import no.hiof.larseknu.playingwithviewmodel.R
import no.hiof.larseknu.playingwithviewmodel.databinding.FragmentMovieSummaryBinding

/**
 * A simple [Fragment] subclass.
 */
class MovieSummaryFragment : Fragment() {
    private lateinit var viewModel: SummaryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val args = MovieSummaryFragmentArgs.fromBundle(arguments!!)

        viewModel = ViewModelProviders.of(this).get(SummaryViewModel::class.java)
        viewModel.numberAnswered = args.numberAnswered
        viewModel.numberSeen = args.numberSeen

        val binding: FragmentMovieSummaryBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_movie_summary,
            container,
            false)

        binding.numberSeenOfAnsweredText.text = getString(R.string.number_seen_of_answered, viewModel.numberSeen, viewModel.numberAnswered)

        binding.progressBar.max = viewModel.numberAnswered
        binding.progressBar.progress = viewModel.numberSeen

        binding.tryAgainButton.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.actionStartSelectionAgainFromSummary)
        }

        // Inflate the layout for this fragment
        return binding.root
    }


}
