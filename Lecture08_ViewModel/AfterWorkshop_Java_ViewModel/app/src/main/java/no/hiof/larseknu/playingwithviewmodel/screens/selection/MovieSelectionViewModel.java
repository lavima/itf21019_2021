package no.hiof.larseknu.playingwithviewmodel.screens.selection;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import no.hiof.larseknu.playingwithviewmodel.model.Movie;

public class MovieSelectionViewModel extends ViewModel {
    private MutableLiveData<Movie> currentMovie = new MutableLiveData<>();

    private MutableLiveData<Integer> numberAnswered = new MutableLiveData<>();
    private MutableLiveData<Integer> numberSeen = new MutableLiveData<>();

    private ArrayList<Movie> movieList;

    private MutableLiveData<Boolean> eventSelectionDone = new MutableLiveData<>();

    public MovieSelectionViewModel() {
        selectionReset();
    }

    private void nextMovie() {
        if (!movieList.isEmpty()) {
            currentMovie.setValue(movieList.remove(0));
        }
        else
            onSelectionDone();
    }

    private void retrieveMovieList() {
        movieList = Movie.getMovies();
    }

    public void seenMovie() {
        numberSeen.setValue(numberSeen.getValue()+1);
        numberAnswered.setValue(numberAnswered.getValue()+1);
        nextMovie();
    }

    public void notSeenMovie() {
        numberAnswered.setValue(numberAnswered.getValue()+1);
        nextMovie();
    }

    /** Method for when we are done with the selection **/
    public void onSelectionDone() {
        eventSelectionDone.setValue(true);
    }

    public void selectionReset() {
        numberAnswered.setValue(0);
        numberSeen.setValue(0);
        eventSelectionDone.setValue(false);
        retrieveMovieList();
        nextMovie();
    }

    public LiveData<Movie> getCurrentMovie() {
        return currentMovie;
    }

    public LiveData<Integer> getNumberSeen() {
        return numberSeen;
    }

    public LiveData<Integer> getNumberAnswered() {
        return numberAnswered;
    }

    public LiveData<Boolean> getEventSelectionDone() { return eventSelectionDone; }
}
