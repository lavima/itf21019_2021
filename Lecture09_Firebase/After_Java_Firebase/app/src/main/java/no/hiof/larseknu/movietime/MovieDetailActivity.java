package no.hiof.larseknu.movietime;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import no.hiof.larseknu.movietime.model.Movie;

public class MovieDetailActivity extends AppCompatActivity {
    public static final String MOVIE_UID = "movie_uid";
    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    private TextView movieTitleTextView;
    private TextView movieReleaseDateTextView;
    private TextView movieSummaryTextView;
    private ImageView moviePosterImageView;
    private RatingBar movieRatingBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_activity);

        movieTitleTextView = findViewById(R.id.movieTitleTextView);
        movieReleaseDateTextView = findViewById(R.id.movieYearTextView);
        movieSummaryTextView = findViewById(R.id.summaryTextView);
        movieTitleTextView = findViewById(R.id.movieTitleTextView);
        movieRatingBar = findViewById(R.id.movieRatingBar);
        moviePosterImageView = findViewById(R.id.moviePosterImageView);

        final String movieUid = getIntent().getStringExtra(MOVIE_UID);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        final DocumentReference documentReference = firebaseFirestore.collection("movies").document(movieUid);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    Movie movie = documentSnapshot.toObject(Movie.class);
                    movie.setUid(documentSnapshot.getId());

                    movieTitleTextView.setText(movie.getTitle());
                    movieReleaseDateTextView.setText(movie.getReleaseDate());
                    movieSummaryTextView.setText(movie.getDescription());

                    if (movie.getPosterUrl() != null && !movie.getPosterUrl().isEmpty()) {
                        Glide.with(MovieDetailActivity.this)
                                .load(movie.getPosterUrl())
                                .into(moviePosterImageView);
                    }
                }
                else
                {
                    Log.d(TAG, "Get failed with", task.getException());
                }
            }
        });

    }

}

