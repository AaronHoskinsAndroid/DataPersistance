package examples.aaronhoskins.com.datapersistance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    TextView textView;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("shared_pref", MODE_PRIVATE);
        setContentView(R.layout.activity_main2);
        textView = findViewById(R.id.textView);
        textView.setText(sharedPreferences.getString("user_name", "NO NAME IN SHARED PREF"));

    }
}
