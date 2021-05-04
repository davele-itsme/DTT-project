package nl.dtt.rsr_pechhulp.presenter.home;

import android.app.Activity;
import android.view.View;

import nl.dtt.rsr_pechhulp.view.home.MainActivity;

/**
 * Defining the  contract between the View {@link MainActivity} and the Presenter {@link MainPresenter}
 */
public interface MainContract {
    /**
     * The interface Main view.
     */
    interface MainView {
        /**
         * Open maps screen.
         */
        void openMapsScreen();

        /**
         * Open info dialog.
         */
        void openInfoDialog();


    }

    /**
     * The interface Presenter.
     */
    interface Presenter {
        /**
         * Handle main btn click.
         *
         * @param view the view
         */
        void handleMainBtnClick(View view);

        /**
         * Handle info btn click.
         *
         * @param view the view
         */
        void handleInfoBtnClick(View view);

        void handleFirstPolicy(Activity activity);
    }
}
