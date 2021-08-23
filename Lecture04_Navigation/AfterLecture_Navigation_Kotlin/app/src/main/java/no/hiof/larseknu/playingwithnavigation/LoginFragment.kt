package no.hiof.larseknu.playingwithnavigation


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get a reference to the loginButton. Done automagically with kotlin extension
        //val loginButton = view.findViewById<Button>(R.id.loginButton)

        // We can set the onClickListener that's returned from the createNavigateOnClickListener method
        //loginButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_login_to_home, null));

        // But if we want som logic other than just navigating, we can use the lambda expression to set to the onClickListener
        loginButton.setOnClickListener {
            // Checks if the inputfield have any data
            if (editTextUserName.text.toString().isNotEmpty() && editTextPassword.text.toString().isNotEmpty()) {
                // Retreives the username
                val username = editTextUserName.text.toString()

                // Sets the username as an argument to be sent to the HomeFragment through the action
                val action = LoginFragmentDirections.actionLoginToHome(username)

                // Navigates to the HomeFragment
                it.findNavController().navigate(action)
            }
            else
            // Displays a toast when we can't login
                Toast.makeText(it.context, "Invalid credentials", Toast.LENGTH_LONG).show()
        }
    }
}
