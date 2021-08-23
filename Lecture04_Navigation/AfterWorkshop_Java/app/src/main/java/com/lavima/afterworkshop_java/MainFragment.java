package com.lavima.afterworkshop_java;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainFragment extends Fragment {

    public static final String KEY_NAME = "name";

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editTextName = getActivity().findViewById(R.id.editTextName);
                // If we are in dual view we can update textViewName directly
                if (((MainActivity)getActivity()).IsDualView) {
                    TextView textView = getActivity().findViewById(R.id.textViewName);
                    textView.setText(editTextName.getText().toString());
                }
                // If not dual
                else {
                    // Create other fragment
                    OtherFragment otherFragment = new OtherFragment();

                    // Pass name in argument bundle
                    Bundle arguments = new Bundle();
                    arguments.putString(KEY_NAME, editTextName.getText().toString());
                    otherFragment.setArguments(arguments);

                    // Create transaction, replace fragment, add the current (previous) to back stack,
                    // then commit
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, otherFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });
    }
}