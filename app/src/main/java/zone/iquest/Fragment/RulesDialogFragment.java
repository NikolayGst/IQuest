package zone.iquest.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;

import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment;
import zone.iquest.R;

public class RulesDialogFragment extends SupportBlurDialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_rules, null);
        builder.setView(view);
        return builder.create();
    }

}