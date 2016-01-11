package myapp.tae.ac.uk.gcmpushnotification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import myapp.tae.ac.uk.gcmpushnotification.push_notification.GcmRegistrationAsyncTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new GcmRegistrationAsyncTask(this).execute();
    }
}
