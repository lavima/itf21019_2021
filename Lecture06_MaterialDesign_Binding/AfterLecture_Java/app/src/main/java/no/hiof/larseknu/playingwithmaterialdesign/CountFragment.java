package no.hiof.larseknu.playingwithmaterialdesign;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import no.hiof.larseknu.playingwithmaterialdesign.databinding.FragmentCountBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class CountFragment extends Fragment {
    // Constants
    private static final String COUNT_ID = "COUNT_ID";
    private static final int CHANGE_INCREMENT = 10;

    // Numbers we use to count with
    private int increment = 1;
    private int counter = 0;

    private FragmentCountBinding binding;

    public CountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Creates the binding object we use for handling the different views
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_count, container, false);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("CountFragment", "onViewCreated Called");

        if (savedInstanceState != null) {
            counter = savedInstanceState.getInt(COUNT_ID);
        }

        // Set the counter and increment in
        binding.setCounter(counter);
        binding.setIncrement(increment);

        checkIncrement();

        // Add a clickListener to the button
        binding.countButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkIncrement();

                counter += increment;

                // Set the counter value in the view
                binding.setCounter(counter);
            }
        });
    }

    private void checkIncrement() {
        // Check if we should increase the increment
        if (counter >= CHANGE_INCREMENT) {
            increment = 2;

            // Set the increment value in the view
            binding.setIncrement(increment);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // Inserts the data we want to retain when orientation changes into the bundle
        outState.putInt(COUNT_ID, counter);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("CountFragment", "onStart Called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("CountFragment", "onResume Called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("CountFragment", "onPause Called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("CountFragment", "onStop Called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("CountFragment", "onDestroy Called");
    }
}
