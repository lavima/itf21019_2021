package no.hiof.larseknu.playingwithviewmodel.screens.summary;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import no.hiof.larseknu.playingwithviewmodel.R;
import no.hiof.larseknu.playingwithviewmodel.databinding.FragmentMovieSelectionBinding;
import no.hiof.larseknu.playingwithviewmodel.databinding.FragmentMovieSummaryBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieSummaryFragment extends Fragment {

    private MovieSummaryViewModel viewModel;

    public MovieSummaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MovieSummaryFragmentArgs args = MovieSummaryFragmentArgs.fromBundle(getArguments());

        viewModel = ViewModelProviders.of(this).get(MovieSummaryViewModel.class);
        viewModel.setNumberAnswered(args.getNumberAnswered());
        viewModel.setNumberSeen(args.getNumberSeen());

        FragmentMovieSummaryBinding binding = FragmentMovieSummaryBinding.inflate(getLayoutInflater());

        binding.numberSeenOfAnsweredText.setText(getString(R.string.number_seen_of_answered, viewModel.getNumberSeen(), viewModel.getNumberAnswered()));

        binding.progressBar.setMax(viewModel.getNumberAnswered());
        binding.progressBar.setProgress(viewModel.getNumberSeen());

        binding.tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.actionStartSelectionAgainFromSummary);
            }
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

}
