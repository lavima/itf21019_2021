package no.hiof.larseknu.playingwithviewmodel.screen.selection;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import no.hiof.larseknu.playingwithviewmodel.R;
import no.hiof.larseknu.playingwithviewmodel.model.Movie;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieSelectionFragment extends Fragment {
    private Movie currentMovie;

    private int numberAnswered = 0;
    private int numberSeen = 0;

    private ArrayList<Movie> movieList;

    public MovieSelectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_selection, container, false);
    }

}
