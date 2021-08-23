package no.hiof.larseknu.movietime;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import no.hiof.larseknu.movietime.adapter.MovieRecyclerAdapter;
import no.hiof.larseknu.movietime.model.Movie;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private List<Movie> movieList;
    private List<String> movieUidList;

    private RecyclerView recyclerView;
    private MovieRecyclerAdapter movieAdapter;

    private FirebaseFirestore firestoreDb;
    private CollectionReference movieCollectionReference;
    private ListenerRegistration fireStoreListenerRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieList = new ArrayList<>();
        movieUidList = new ArrayList<>();

        firestoreDb = FirebaseFirestore.getInstance();

        movieCollectionReference = firestoreDb.collection("movies");

        setUpRecyclerView();

        //generateTestData();
    }

    private void createFireStoreReadListener() {
        /*movieCollectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Movie movie = documentSnapshot.toObject(Movie.class);
                        movie.setUid(documentSnapshot.getId());
                        movieList.add(movie);
                        movieUidList.add(movie.getUid());
                    }
                    movieAdapter.notifyDataSetChanged();
                } else {
                    Log.d(TAG, "Error getting documents: " + task.getException());
                }
            }
        });*/

        fireStoreListenerRegistration = movieCollectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                for (DocumentChange documentChange : querySnapshot.getDocumentChanges()) {
                    QueryDocumentSnapshot documentSnapshot = documentChange.getDocument();
                    Movie movie = documentSnapshot.toObject(Movie.class);
                    movie.setUid(documentSnapshot.getId());
                    int pos = movieUidList.indexOf(movie.getUid());

                    switch (documentChange.getType()) {
                        case ADDED:
                            movieList.add(movie);
                            movieUidList.add(movie.getUid());
                            movieAdapter.notifyItemInserted(movieList.size()-1);
                            break;
                        case REMOVED:
                            movieList.remove(pos);
                            movieUidList.remove(pos);
                            movieAdapter.notifyItemRemoved(pos);
                            break;
                        case MODIFIED:
                            movieList.set(pos, movie);
                            movieAdapter.notifyItemChanged(pos);
                            break;
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        createFireStoreReadListener();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (fireStoreListenerRegistration != null)
            fireStoreListenerRegistration.remove();
    }

    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.movieRecyclerView);
        movieAdapter = new MovieRecyclerAdapter(this, movieList);

        movieAdapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = recyclerView.getChildAdapterPosition(view);

                Movie movie = movieList.get(position);

                Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.MOVIE_UID, movie.getUid());

                startActivity(intent);
            }
        });

        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sign_out_item:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void generateTestData() {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Iron Man 3", "When Tony Stark's world is torn apart by a formidable terrorist called the Mandarin, he starts an odyssey of rebuilding and retribution.", "2013-04-18"));
        movies.add(new Movie("Donnie Darko", "A troubled teenager is plagued by visions of a man in a large rabbit suit who manipulates him to commit a series of crimes, after he narrowly escapes a bizarre accident.", "2001-10-26"));
        movies.add(new Movie("Pulp Fiction", "A burger-loving hit man, his philosophical partner, a drug-addled gangster's moll and a washed-up boxer converge in this sprawling, comedic crime caper. Their adventures unfurl in three stories that ingeniously trip back and forth in time.", "1994-10-14"));
        movies.add(new Movie("Spirited Away", "During her family's move to the suburbs, a sullen 10-year-old girl wanders into a world ruled by gods, witches, and spirits, and where humans are changed into beasts.", "2001-06-20"));
        movies.add(new Movie("Star Wars: The Force Awakens", "Thirty years after defeating the Galactic Empire, Han Solo and his allies face a new threat from the evil Kylo Ren and his army of Stormtroopers.", "2015-12-14"));
        movies.add(new Movie("Up", "Seventy-eight year old Carl Fredricksen travels to Paradise Falls in his home equipped with balloons, inadvertently taking a young stowaway.", "2009-09-25"));
        movies.add(new Movie("The Hobbit", "Bilbo Baggins, a hobbit enjoying his quiet life, is swept into an epic quest by Gandalf the Grey and thirteen dwarves who seek to reclaim their mountain home from Smaug, the dragon.", "2012-12-06"));
        movies.add(new Movie("Captain America: Civil War", "Following the events of Age of Ultron, the collective governments of the world pass an act designed to regulate all superhuman activity. This polarizes opinion amongst the Avengers, causing two factions to side with Iron Man or Captain America, which causes an epic battle between former allies.", "2016-04-12"));
        movies.add(new Movie("Deadpool", "Deadpool tells the origin story of former Special Forces operative turned mercenary Wade Wilson, who after being subjected to a rogue experiment that leaves him with accelerated healing powers, adopts the alter ego Deadpool. Armed with his new abilities and a dark, twisted sense of humor, Deadpool hunts down the man who nearly destroyed his life.", "2016-02-12"));

        for (Movie aMovie : movies) {
            movieCollectionReference.add(aMovie);
        }
    }
}
