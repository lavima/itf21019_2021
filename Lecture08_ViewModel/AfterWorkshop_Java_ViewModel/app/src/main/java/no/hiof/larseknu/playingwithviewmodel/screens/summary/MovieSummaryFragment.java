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
import no.hiof.larseknu.playingwithviewmodel.screens.selection.MovieSelectionViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieSummaryFragment extends Fragment {

    private MovieSelectionViewModel viewModel;

    public MovieSummaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewModel = ViewModelProviders.of(requireActivity()).get(MovieSelectionViewModel.class);

        FragmentMovieSummaryBinding binding = FragmentMovieSummaryBinding.inflate(getLayoutInflater());

        binding.numberSeenOfAnsweredText.setText(getString(R.string.number_seen_of_answered, viewModel.getNumberSeen().getValue(), viewModel.getNumberAnswered().getValue()));

        binding.progressBar.setMax(viewModel.getNumberAnswered().getValue());
        binding.progressBar.setProgress(viewModel.getNumberSeen().getValue());

        binding.tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.actionStartSelectionAgainFromSummary);
                viewModel.selectionReset();
            }
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

}
