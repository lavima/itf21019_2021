package no.hiof.larseknu.playingwithmaterialdesign


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment

import no.hiof.larseknu.playingwithmaterialdesign.databinding.FragmentCountBinding


/**
 * A simple [Fragment] subclass.
 */
class CountFragment : Fragment() {

    // Numbers we use to count with
    private var increment = 1
    private var counter = 0

    private lateinit var binding: FragmentCountBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Creates the binding object we use for handling the different views
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_count, container, false)

        // Inflate the layout for this fragment
        return binding.root
    }

    @SuppressLint("DefaultLocale")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("CountFragment", "onViewCreated Called")

        if (savedInstanceState != null) {
            counter = savedInstanceState.getInt(COUNT_ID)
        }

        // Set the counter and increment in
        binding.counter = counter
        binding.increment = increment

        checkIncrement()

        // Add a clickListener to the button
        binding.countButton.setOnClickListener {
            checkIncrement()

            counter += increment

            // Set the counter value in the view
            binding!!.counter = counter
        }
    }

    private fun checkIncrement() {
        // Check if we should increase the increment
        if (counter >= CHANGE_INCREMENT) {
            increment = 2

            // Set the increment value in the view
            binding.increment = increment
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Inserts the data we want to retain when orientation changes into the bundle
        outState.putInt(COUNT_ID, counter)
    }

    override fun onStart() {
        super.onStart()
        Log.i("CountFragment", "onStart Called")
    }

    override fun onResume() {
        super.onResume()
        Log.i("CountFragment", "onResume Called")
    }

    override fun onPause() {
        super.onPause()
        Log.i("CountFragment", "onPause Called")
    }

    override fun onStop() {
        super.onStop()
        Log.i("CountFragment", "onStop Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("CountFragment", "onDestroy Called")
    }

    companion object {
        // Constants
        private val COUNT_ID = "COUNT_ID"
        private val CHANGE_INCREMENT = 10
    }
}// Required empty public constructor
