package zone.iquest.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import zone.iquest.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_QUEST = "quest";
    private static final String ARG_QUEST_ID = "quest_id";

    private TextView mTextQuest;
    private ImageView mImageQuest;
    private Button mButtonQuest;
    private InfoFragment mInfoFragment;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    public QuestFragment() {
        // Required empty public constructor
    }


    public static QuestFragment newInstance(int sectionNumber) {
        Bundle args = new Bundle();
        QuestFragment fragment = new QuestFragment();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Bundle args = getArguments();
        View view = inflater.inflate(R.layout.fragment_quest, container, false);

        mTextQuest = (TextView) view.findViewById(R.id.txtQuest);
        mImageQuest = (ImageView) view.findViewById(R.id.imageQuest);
        mButtonQuest = (Button) view.findViewById(R.id.btnQuest);

        mInfoFragment = new InfoFragment();
        mFragmentManager = getActivity().getSupportFragmentManager();
        switch (args.getInt(ARG_SECTION_NUMBER)) {
            case 1:
                mTextQuest.setText("Детские страхи");
                mImageQuest.setImageResource(R.drawable.ds);
                break;
            case 2:
                mTextQuest.setText("Частный детектив");
                mImageQuest.setImageResource(R.drawable.cd);
                break;
            case 3:
                mTextQuest.setText("Блок смертников");
                mImageQuest.setImageResource(R.drawable.bc);
                break;

        }
        mButtonQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentTransaction = mFragmentManager.beginTransaction();
                Bundle name = new Bundle();
                switch (args.getInt(ARG_SECTION_NUMBER)) {
                    case 1:
                        name.putString(ARG_QUEST, mTextQuest.getText().toString());
                        name.putInt(ARG_QUEST_ID, 1);
                        break;
                    case 2:
                        name.putString(ARG_QUEST, mTextQuest.getText().toString());
                        name.putInt(ARG_QUEST_ID, 2);
                        break;
                    case 3:
                        name.putString(ARG_QUEST, mTextQuest.getText().toString());
                        name.putInt(ARG_QUEST_ID, 3);
                        break;
                }

                mInfoFragment.setArguments(name);
                mFragmentTransaction.addToBackStack("quest");
                mFragmentTransaction.replace(R.id.container, mInfoFragment);
                mFragmentTransaction.commit();
            }
        });
        return view;


    }
}
