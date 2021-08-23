package no.hiof.larseknu.movietime;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import no.hiof.larseknu.movietime.adapter.MovieRecyclerAdapter;
import no.hiof.larseknu.movietime.model.Movie;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private List<Movie> movieList;
    private List<String> movieUidList;

    private RecyclerView recyclerView;
    private MovieRecyclerAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieList = new ArrayList<>();
        movieUidList = new ArrayList<>();

        setUpRecyclerView();
    }


    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.movieRecyclerView);
        movieAdapter = new MovieRecyclerAdapter(this, movieList);

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


    }
}
