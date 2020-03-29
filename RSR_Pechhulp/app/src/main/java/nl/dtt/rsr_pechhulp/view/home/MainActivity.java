package nl.dtt.rsr_pechhulp.view.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;

import nl.dtt.rsr_pechhulp.R;
import nl.dtt.rsr_pechhulp.databinding.ActivityMainBinding;
import nl.dtt.rsr_pechhulp.presenter.home.MainContract;
import nl.dtt.rsr_pechhulp.presenter.home.MainPresenter;
import nl.dtt.rsr_pechhulp.view.maps.MapsActivity;


/**
 * Displays the main screen
 */
public class MainActivity extends AppCompatActivity implements MainContract.MainView {
    /**
     * The Binding.
     */
    ActivityMainBinding binding;
    private boolean tabletSize;
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainPresenter = new MainPresenter(this);
        binding.setPresenter(mainPresenter);

        //boolean - if the device is tablet, returns true, if mobile phone, returns false
        tabletSize = getResources().getBoolean(R.bool.isTablet);

        //setting title and setting the toolbar as an app bar
        binding.mainToolbar.setTitle(R.string.main_menu_title);
        Toolbar toolbar = binding.mainToolbar;
        setSupportActionBar(toolbar);

        //calling method if the user agreed to privacy policy
        checkFirstPolicy();
    }

    //Inflate XML rsc using onCreateOptionsMenu() callback
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    //Set visibility of info icon based on tabletSize boolean - if device is tablet, visibility is false
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem infoIcon = menu.findItem(R.id.action_info);
        infoIcon.setVisible(!tabletSize);
        return true;
    }

    //Providing "OnClickListener" on info icon to open information dialog - privacy policy
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_info) {
            super.onOptionsItemSelected(item);
            openDialog();
            return true;
        }
        return false;
    }

    //Opening Maps screen
    @Override
    public void openMapsScreen() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    @Override
    public void openInfoDialog() {
        openDialog();
    }

    //Calling presenter method handleFIrstPolicy
    private void checkFirstPolicy() {
        //Calling presenter method to check, if the user agreed to policy privacy
        mainPresenter.handleFirstPolicy(this);
    }

    //Instantiating MainMenuDialog fragment to display dialog
    private void openDialog() {
        MainMenuDialog mainMenuDialog = new MainMenuDialog();
        mainMenuDialog.setCancelable(false);
        mainMenuDialog.show(getSupportFragmentManager(), "privacy dialog");
    }


}
