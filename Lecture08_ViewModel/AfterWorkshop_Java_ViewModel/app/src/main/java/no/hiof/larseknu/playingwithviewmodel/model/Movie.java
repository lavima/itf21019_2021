package no.hiof.larseknu.playingwithviewmodel.model;

import java.util.ArrayList;

import no.hiof.larseknu.playingwithviewmodel.R;

public class Movie {
    private int uid;
    private String title;
    private String description;
    private int posterUrl;

    public Movie(int uid, String title, String description, int posterUrl) {
        this.uid = uid;
        this.title = title;
        this.description = description;
        this.posterUrl = posterUrl;
    }

    public int getUid() {
        return uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(int posterUrl) {
        this.posterUrl = posterUrl;
    }

    public static ArrayList<Movie> getMovies() {
        ArrayList data = new ArrayList<Movie>();

        int[] posters = new int[]{
                R.drawable.captain_america_civil_war,
                R.drawable.donnie_darko,
                R.drawable.iron_man_3,
                R.drawable.spirited_away,
                R.drawable.star_wars_the_force_awakens,
                R.drawable.the_hobbit,
                R.drawable.up,
                R.drawable.pulp_fiction,
                R.drawable.coco,
                R.drawable.deadpool,
                R.drawable.inside_out,
                R.drawable.into_the_wild,
                R.drawable.the_hateful_eight,
                R.drawable.the_intouchables,
                R.drawable.the_lion_king
        };

        String[] titles = new String[]{
                "Captain America Civil War",
                "Donnie Darko",
                "Iron Man 3",
                "Spirited Away",
                "Star Wars The Force Awakens",
                "The Hobbit",
                "Up",
                "Pulp Fiction",
                "Coco",
                "Deadpool",
                "Inside Out",
                "Into The Wild",
                "The Hateful Eight",
                "The Intouchables",
                "The Lion King"
        };

        for(int i = 0; i < posters.length; i++) {
            Movie aMovie = new Movie(i, titles[i], titles[i] + " is a good movie.", posters[i]);
            data.add(aMovie);
        }

        return data;
    }

}
