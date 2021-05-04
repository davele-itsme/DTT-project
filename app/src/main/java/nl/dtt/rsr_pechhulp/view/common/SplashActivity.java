package nl.dtt.rsr_pechhulp.view.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import nl.dtt.rsr_pechhulp.R;
import nl.dtt.rsr_pechhulp.view.home.MainActivity;

/**
 * The type Splash activity, which is launched first
 */
public class SplashActivity extends AppCompatActivity {
    //The length of the splash activity displaying
    final int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Creating Explicit intent to open MainActivity class
        new Handler().postDelayed(() -> {
            Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mainActivity);
            finish();
        }, SPLASH_TIME_OUT);
    }
}
