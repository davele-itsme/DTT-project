package nl.dtt.rsr_pechhulp.view.maps;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.TypedValue;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.content.ContextCompat;

import nl.dtt.rsr_pechhulp.R;

/**
 * The type Network dialog.
 */
public class NetworkDialog extends AppCompatDialogFragment {

    private Activity activity;

    /**
     * Creating a Network dialog constructor, passing activity for having a possibility to manipulate with the lifecycle of the passed activity.
     *
     * @param activity the activity
     */
    public NetworkDialog(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //Creating AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.network_title).setMessage(R.string.network_message).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finish();
            }
        }).setPositiveButton(R.string.turn_on, (dialog, i) -> {
            Intent networkSettings = new Intent(Settings.ACTION_WIFI_SETTINGS); //Move user to Wifi settings to let user enable Wifi
            startActivity(networkSettings);
            dialog.dismiss();
        });
        AlertDialog dialog = builder.create();
        dialog.show();

        setButtonStyle(dialog);
        return dialog;
    }

    private void setButtonStyle(AlertDialog dialog)
    {
        //Styling positive button to be similar to the Play Store's version
        Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        if (positiveButton != null) {
            positiveButton.setTextColor(ContextCompat.getColor(activity.getBaseContext(), R.color.colorPrimary));
            positiveButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        }

        //Styling negative button to be similar to the Play Store's version
        Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        if (negativeButton != null) {
            negativeButton.setTextColor(ContextCompat.getColor(activity.getBaseContext(), R.color.color_black));
            negativeButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        }
    }
}
