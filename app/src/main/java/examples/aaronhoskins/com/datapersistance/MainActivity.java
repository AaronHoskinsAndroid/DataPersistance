package examples.aaronhoskins.com.datapersistance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    EditText etUserName;
    TextView tvDisplayUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, DatabaseEntryActivity.class));
        finish();
        etUserName = findViewById(R.id.etUserName);
        tvDisplayUserName = findViewById(R.id.tvDisplayUserName);

        sharedPreferences = getSharedPreferences("shared_pref", MODE_PRIVATE);

    }

    public void onClick(View view) {
        //storeToSharedPref(etUserName.getText().toString());
        //tvDisplayUserName.setText(retreiveFromSharedPref());
        startActivity(new Intent(this, Main2Activity.class));
    }

    private void storeToSharedPref(String string) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_name", string);
        editor.apply();
    }

    private String retreiveFromSharedPref() {
        return sharedPreferences.getString("user_name", "NO NAME STORED");
    }
}
