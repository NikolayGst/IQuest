package zone.iquest.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment;
import zone.iquest.Provider.VolleyRequest;
import zone.iquest.R;
import zone.iquest.Utils.FormatSeance;
import zone.iquest.Utils.PreferenceHelper;

public class SeanceDialogFragment extends SupportBlurDialogFragment {

    private static final String ARG_QUEST_DATE = "quest_date";
    private static final String ARG_QUEST_ID = "quest_id";
    private static final String ARG_QUEST = "quest";
    private static final String ARG_QUEST_SEANCE = "quest_seance";

    private PreferenceHelper mPreferenceHelper;
    private ProgressDialog pDialog;
    private VolleyRequest mVolleyRequest;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.seance_dialog, null);
        mVolleyRequest = new VolleyRequest();

        final int seanceId = getArguments().getInt(ARG_QUEST_SEANCE);
        final int questId = getArguments().getInt(ARG_QUEST_ID);
        final String date = getArguments().getString(ARG_QUEST_DATE);
        final String name = getArguments().getString(ARG_QUEST);

        TextView txtSeance = (TextView) view.findViewById(R.id.txtSeance);
        txtSeance.setText(seanceId + " сеанс на " + FormatSeance.format(seanceId, questId));

        builder.setView(view);
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mVolleyRequest.registerSeance(getContext(), SeanceDialogFragment.this, date,
                        questId, seanceId,name);
            }
        });
        builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return builder.create();
    }
}
