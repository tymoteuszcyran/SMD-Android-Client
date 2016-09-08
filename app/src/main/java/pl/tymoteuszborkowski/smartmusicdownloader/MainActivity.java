package pl.tymoteuszborkowski.smartmusicdownloader;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
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
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                final String artist = artistEditText.getText().toString();
                final String title = titleEditText.getText().toString();

                if ((!artist.isEmpty()) && (!title.isEmpty())) {
                    if (shouldAskPermission()) {
                        String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
                        if(!hasPermission(perms[0])){
                            int permsRequestCode = 200;
                            requestPermissions(perms, permsRequestCode);
                        }

                    }
                    passParameters = new PassParameters(artist, title);
                    Thread thread = new Thread(passParameters);
                    thread.start();
                }

            }
        });

    }


    private boolean shouldAskPermission() {

        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case 200:

                boolean writeAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                System.out.println("ACCEPTED: " + writeAccepted);
                break;

        }
    }


    @SuppressLint("NewApi")
    private boolean hasPermission(String permission) {
        boolean canMakeSmores = Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP_MR1;
        return !canMakeSmores || (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);

    }
}
