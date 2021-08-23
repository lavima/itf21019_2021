package no.hiof.larseknu.movietime.model;

import com.google.firebase.firestore.Exclude;

public class Movie {
    @Exclude
    private String uid;
    private String title;
    private String description;
    private String releaseDate;
    private String posterUrl;

    public Movie() { }

    public Movie(String title, String description, String releaseDate) {
        this(title, description, releaseDate, "");
    }

    public Movie(String title, String description, String releaseDate, String posterUrl) {
        this("", title, description, releaseDate, posterUrl);
    }

    public Movie(String uid, String title, String description, String releaseDate, String posterUrl) {
        this.uid = uid;
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.posterUrl = posterUrl;
    }

    @Exclude
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    @Override
    public String toString() {
        return uid + " " + title;
    }
}
