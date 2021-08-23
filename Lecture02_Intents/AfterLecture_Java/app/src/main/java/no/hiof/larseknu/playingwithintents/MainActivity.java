package no.hiof.larseknu.playingwithintents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    //2.4: Create a constant (public static final) to represent the key in the extra for the intent
    public static final String KEY_NAME = "name";

    //1.3: Create a field variable for the button navigateToOtherButton
    private Button navigateToOtherButton;
    //2.2: Create a field variable (nameEditText) for the EditText view
    private EditText nameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2.3: Instantiate nameEditText
        nameEditText = findViewById(R.id.nameEditText);

        // 1.4: Instantiate navigateToOtherButton
        navigateToOtherButton = findViewById(R.id.navigateToOtherButton);

        // 1.5: Add a onClickListener to the navigateToOtherButton
        navigateToOtherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1.6: Create an Intent to start OtherActivity in the navigate... method
                Intent intent = new Intent(view.getContext(), OtherActivity.class);

                // 2.5: Get the text from the EditText when navigateToOtherActivity is ran
                String name = nameEditText.getText().toString();

                // 2.6: Put the text from the EditText into the extra information in the Intent
                intent.putExtra(KEY_NAME, name);

                // 1.7: Start OtherActivity with the intent
                startActivity(intent);
            }
        });

    }

    // 3.3: Create openWebPage(View view) method to handle the click
    public void openWebPage(View view) {
        // 3.4: Create an Intent
        Intent intent = new Intent();
        // 3.5: Set action ACTION_VIEW to the intent
        intent.setAction(Intent.ACTION_VIEW);
        // 3.6: Create an URI to a webpage and set it as data for the intent
        Uri uri = Uri.parse("http://hiof.no");
        intent.setData(uri);
        // 3.7: Start the activity with the intent
        startActivity(intent);
    }

    // 4.3: Create openMaps(View view) method to handle the click
    public void openMaps(View view) {
        // 4.4: Create an URI to https://maps.google.com?q=59.128708,11.353176
        //Uri uri = Uri.parse("https://maps.google.com?q=59.128708,11.353176");

        // 4.7: Create an URI with the geo:lan,lot instead
        Uri uri = Uri.parse("geo:59.128708,11.353176");

        // 4.5: Create an Intent with ACTION_VIEW and the URI
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        // 4.6: Start the activity with the intent
        startActivity(intent);
    }
}
