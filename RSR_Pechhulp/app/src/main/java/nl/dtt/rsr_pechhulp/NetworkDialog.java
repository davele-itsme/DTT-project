package nl.dtt.rsr_pechhulp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class NetworkDialog extends AppCompatDialogFragment {

    private Activity activity;

    public NetworkDialog(Activity activity)
    {
        this.activity = activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //Creating dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.network_title).setMessage(R.string.network_message).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finish();
            }
        }).setPositiveButton(R.string.turn_on, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Intent networkSettings = new Intent(Settings.ACTION_WIFI_SETTINGS);
                startActivity(networkSettings);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

        //Styling button to be similar to the Play Store's version
        Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        if(positiveButton != null)
        {
            positiveButton.setTextColor(getResources().getColor(R.color.colorPrimary));
            positiveButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        }

        Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        if(negativeButton != null)
        {
            negativeButton.setTextColor(getResources().getColor(R.color.color_black));
            negativeButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        }

        //To be able to make a reference with the text in the dialog
        TextView text = dialog.findViewById(android.R.id.message);
        text.setMovementMethod(LinkMovementMethod.getInstance());
        return dialog;
    }
}
