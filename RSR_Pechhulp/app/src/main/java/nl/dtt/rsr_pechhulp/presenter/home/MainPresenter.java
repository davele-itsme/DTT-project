package nl.dtt.rsr_pechhulp.presenter.home;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;

import androidx.preference.PreferenceManager;

/**
 * Responsible for handling the actions from the View and updating UI as required
 */
public class MainPresenter implements MainContract.Presenter {
    private MainContract.MainView mView;

    /**
     * Creating constructor - Main presenter.
     *
     * @param view the view assigning to instance variable, so that it can be used in below methods
     */
    public MainPresenter(MainContract.MainView view) {
        mView = view;
    }

    @Override
    public void handleMainBtnClick(View view) {
        mView.openMapsScreen();
    }

    @Override
    public void handleInfoBtnClick(View view) {
        mView.openInfoDialog();
    }

    //Checks, if the user agreed to privacy policy, if not, it displays the privacy policy dialog

    @Override
    public void handleFirstPolicy(Activity activity) {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity.getBaseContext());
        if (prefs.getBoolean("FirstPolicy", true)) {
            mView.openInfoDialog();
        }
    }

}
