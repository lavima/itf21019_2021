package no.hiof.larseknu.playingwithnavigation


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Checks if we actually get some arguments
        if (arguments != null) {
            // Retreives a reference to the TextView
            val usernameTextView: TextView = view.findViewById(R.id.textViewUsername)

            // Retreives the username from arguments through the Safe Args generated file HomeFragmentArgs
            val username = HomeFragmentArgs.fromBundle(arguments!!).username

            // Sets the username
            usernameTextView.text = username
        }
    }
}
