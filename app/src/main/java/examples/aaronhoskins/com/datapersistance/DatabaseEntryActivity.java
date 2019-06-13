package examples.aaronhoskins.com.datapersistance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class DatabaseEntryActivity extends AppCompatActivity {
    EditText etTitle;
    EditText etGenre;
    EditText etDate;
    EditText etArtist;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_entry);
        etTitle = findViewById(R.id.etTitle);
        etGenre = findViewById(R.id.etGenre);
        etDate = findViewById(R.id.etDate);
        etArtist = findViewById(R.id.etArtist);
        databaseHelper = new DatabaseHelper(this);
    }

    public void onClick(View view) {
        String title = etTitle.getText().toString();
        String genre = etGenre.getText().toString();
        String date = etDate.getText().toString();
        String artist = etArtist.getText().toString();

        if(!(title.isEmpty() || genre.isEmpty() || date.isEmpty() || artist.isEmpty())) {
            databaseHelper.insertMusic(new Music(title, genre, artist, date));
        }

        for(Music music : databaseHelper.queryForAllMusicRecords()){
            Log.d("TAG", String.format(Locale.US,
                    "%s %s %s %s",
                    music.getTitle(), music.getGenre(), music.getDate(), music.getArtist()));
        }
    }
}
