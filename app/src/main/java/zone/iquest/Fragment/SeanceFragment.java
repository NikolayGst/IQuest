package zone.iquest.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zone.iquest.Adapter.SeanceAdapter;
import zone.iquest.Model.Seance;
import zone.iquest.Provider.VolleyRequest;
import zone.iquest.R;
import zone.iquest.Utils.FormatDate;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeanceFragment extends Fragment {

    private static final String ARG_QUEST = "quest";
    private static final String ARG_QUEST_DATE = "quest_date";
    private static final String ARG_QUEST_ID = "quest_id";
    private VolleyRequest mVolleyRequest;
    private List<Seance> seanceList;
    private int mSeance;
    TextView txtDateQuest;
    String date;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager lm;
    private ImageView mBackground;

    public SeanceFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seance, container, false);
        Bundle value = getArguments();
        int questId = value.getInt(ARG_QUEST_ID);
        String questDate = value.getString(ARG_QUEST_DATE);
        String questName = value.getString(ARG_QUEST);
        seanceList = new ArrayList<>();
        SeanceAdapter seanceAdapter = new SeanceAdapter(seanceList, getContext(),
                getFragmentManager(), questName, questId, questDate);

        if (questId == 2) {
            mSeance = 8;
        } else {
            mSeance = 7;
        }

        for (int i = 1; i <= mSeance; i++) {
            Seance seance = new Seance();
            seance.setSeanceId(i);
            seance.setStatus(0);
            seanceList.add(seance);
        }

        mBackground = (ImageView) view.findViewById(R.id.bg);
        setBackground(questId, mBackground);

        txtDateQuest = (TextView) view.findViewById(R.id.txtDateQuest);
        date = FormatDate.format(value.getString(ARG_QUEST_DATE));
        txtDateQuest.setText(value.getString(ARG_QUEST) + "\n" + date);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        lm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(seanceAdapter);
        recyclerView.setVisibility(View.GONE);

        mVolleyRequest = new VolleyRequest();
        mVolleyRequest.getNewStatus(getActivity(), questId, questDate, seanceList,
                recyclerView);
        return view;
    }

    public void setBackground(int questId, ImageView background) {
        int resId = 0;
        switch (questId) {
            case 1:
                resId = R.drawable.ds;
                break;
            case 2:
                resId = R.drawable.cd;
                break;
            case 3:
                resId = R.drawable.bc;
                break;
        }
        background.setImageResource(resId);
    }

}
