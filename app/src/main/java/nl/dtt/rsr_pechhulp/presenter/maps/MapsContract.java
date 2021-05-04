package nl.dtt.rsr_pechhulp.presenter.maps;

import android.app.Activity;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;

/**
 * The interface Maps contract.
 */
public interface MapsContract {
    /**
     * The interface Maps view.
     */
    interface MapsView {
        /**
         * Open contact dialog.
         */
        void openContactDialog();

        /**
         * Close contact dialog.
         */
        void closeContactDialog();

        /**
         * Make call method.
         */
        void makeCall();

        Activity getViewActivity();

        void openGPSDialog();

        void openNetworkDialog();

    }

    /**
     * The interface Presenter.
     */
    interface Presenter {
        /**
         * Handle cancel anction when clicked on button.
         *
         * @param view the view
         */
        void handleCancelBtnClick(View view);

        /**
         * Handle opening contact dialog by button.
         *
         * @param view the view
         */
        void handleOpenContactDialogBtnClick(View view);

        /**
         * Handle call btn click.
         *
         * @param view the view
         */
        void handleCallBtnClick(View view);

        /**
         * Handle call when clicked on text.
         *
         * @param view the view
         */
        void handleCallTextClick(View view);

        boolean getNetworkConnection();

        String getAddress(LatLng latLng);
    }
}
