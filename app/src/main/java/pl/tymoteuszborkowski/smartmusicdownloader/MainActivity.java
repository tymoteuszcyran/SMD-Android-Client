package pl.tymoteuszborkowski.smartmusicdownloader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import pl.tymoteuszborkowski.smartmusicdownloader.communication.PassParameters;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private EditText artistEditText;
    private EditText titleEditText;
    private PassParameters passParameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.downloadBtn);
        artistEditText = (EditText) findViewById(R.id.artistEditText);
        titleEditText = (EditText) findViewById(R.id.titleEditText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String artist = artistEditText.getText().toString();
                final String title = titleEditText.getText().toString();

                if((!artist.isEmpty()) && (!title.isEmpty())){
                    passParameters = new PassParameters(artist, title);
                    Thread thread = new Thread(passParameters);
                    thread.start();
                }
            }
        });

    }


}
