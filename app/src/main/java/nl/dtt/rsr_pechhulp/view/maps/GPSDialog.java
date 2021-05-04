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
 * The type Gps dialog.
 */
public class GPSDialog extends AppCompatDialogFragment {

    //Having a context might give problems when I want to manipulate with Activity lifecycle, instead I replaced that with activity
    private Activity activity;

    /**
     * Instantiates a new Gps dialog.
     *
     * @param activity the activity
     */
    public GPSDialog(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //Creating dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.gps_title).setMessage(R.string.gps_message).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finish();

            }
        }).setPositiveButton(R.string.turn_on, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Intent locationSettings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(locationSettings);
            }
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
