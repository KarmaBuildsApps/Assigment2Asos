package uk.ac.googleanalytics.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import uk.ac.googleanalytics.R;
import uk.ac.googleanalytics.app.MyApplication;

public class SecondActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Enable this code to track the activity
        // if ga_autoActivityTracking set to false
         MyApplication.getInstance().trackScreenView("Second Activity");
    }
}