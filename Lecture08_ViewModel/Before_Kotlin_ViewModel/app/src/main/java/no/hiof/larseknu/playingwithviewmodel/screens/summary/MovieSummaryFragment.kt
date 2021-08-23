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
import no.hiof.larseknu.playingwithviewmodel.model.Movie

/**
 * A simple [Fragment] subclass.
 */
class MovieSummaryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_summary, container, false)
    }


}
