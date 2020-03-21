package nl.dtt.rsr_pechhulp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private boolean tabletSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabletSize = getResources().getBoolean(R.bool.isTablet);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.main_menu_title);
        setSupportActionBar(toolbar);

        checkFirstPolicy();

        Button mainBtn = findViewById(R.id.main_btn);
        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
        Button infoBtn = findViewById(R.id.info_btn);
        if(tabletSize)
        {
            infoBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDialog();
                }
            });
        }
    }

    private void checkFirstPolicy() {
        //To see if user agreed with privacy policy
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if(prefs.getBoolean("FirstPolicy", true))
        {
            openDialog();
        }
    }

    //Inflate XML rsc using onCreateOptionsMenu() callback
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    //Setting visibility of info icon based on device
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuItem infoIcon = menu.findItem(R.id.action_info);
        infoIcon.setVisible(!tabletSize);
        return true;
    }

    //Providing "OnClickListener" on info icon
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.action_info)
        {
            super.onOptionsItemSelected(item);
            openDialog();
            return true;
        }
        return false;
    }

    //Instantiating MainMenuDialog fragment to display dialog
    private void openDialog()
    {
        MainMenuDialog mainMenuDialog = new MainMenuDialog();
        mainMenuDialog.setCancelable(false);
        mainMenuDialog.show(getSupportFragmentManager(), "privacy dialog");
    }

}
