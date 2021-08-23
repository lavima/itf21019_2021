package no.hiof.larseknu.playingwithnavigation;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get a reference to the loginButton
        Button loginButton = view.findViewById(R.id.loginButton);

        // We can set the onClickListener that's returned from the createNavigateOnClickListener method
        //loginButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_homeFragment));

        // But if we want som logic other than just navigating, we can use the lambda expression to set to the onClickListener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retreives a reference to the EditText fields
                EditText editTextUsername = getView().findViewById(R.id.editTextUserName);
                EditText editTextPassword = getView().findViewById(R.id.editTextPassword);

                // Retrevies the text from the EditText fields
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                // Checks if the inputfields have any data
                if (!username.isEmpty() && !password.isEmpty()) {
                    // Gets a reference to the navController
                    NavController navController = Navigation.findNavController(view);

                    // Creates the action we want to perform
                    LoginFragmentDirections.ActionLoginFragmentToHomeFragment action = LoginFragmentDirections.actionLoginFragmentToHomeFragment();

                    // Insert the arguments we want to send
                    action.setUsername(username);

                    // Navigates to the HomeFragment
                    navController.navigate(action);
                }
                else
                    Toast.makeText(view.getContext(), "Invalid credentials", Toast.LENGTH_LONG).show();
            }
        });
    }
}
