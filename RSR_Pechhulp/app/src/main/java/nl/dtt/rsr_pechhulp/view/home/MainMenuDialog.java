package nl.dtt.rsr_pechhulp.view.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import java.util.Objects;

import nl.dtt.rsr_pechhulp.R;

/**
 * The type Main menu dialog - information dialog that displays in the Main menu {@link MainActivity}
 */
public class MainMenuDialog extends AppCompatDialogFragment {

    //The method called when creating the instance of this class
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //Creating dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.privacy_title).setMessage(R.string.privacy_message).setPositiveButton(R.string.privacy_confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                //Key-value data to save information, that the user agreed to privacy policy
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(getActivity()));
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("FirstPolicy", false);
                editor.apply();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

        setButtonStyle(dialog);
        setTextMovement(dialog);

        return dialog;
    }

    private void setButtonStyle(AlertDialog dialog)
    {
        //Styling button to be similar to the Play Store's version
        Button button = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        if (button != null) {
            button.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()).getBaseContext(), R.color.colorPrimary));
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        }
    }

    private void setTextMovement(AlertDialog dialog)
    {
        //To be able to make a reference with the text in the dialog
        TextView text = dialog.findViewById(android.R.id.message);
        text.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
