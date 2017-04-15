package zone.iquest.Fragment;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import zone.iquest.R;


public class InfoFragment extends Fragment {
    private static final String ARG_QUEST = "quest";
    private static final String ARG_QUEST_ID = "quest_id";
    private String mQuestName;
    private int mQuestId;

    private TextView mTitleQuest;
    private TextView mTextQuest;
    private TextView mButtonQuest;
    private ImageView mYouTubeImg;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private CalendarFragment mCalendarFragment;

    public InfoFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        mQuestId = args.getInt(ARG_QUEST_ID);
        mQuestName = args.getString(ARG_QUEST);

        View view = inflater.inflate(R.layout.fragment_info, container, false);
        initView(view);
        mTitleQuest.setText(mQuestName);

        if (mQuestId == 3) {
            mYouTubeImg.setVisibility(View.GONE);
        } else {
            setImageYouTube(mQuestId, mYouTubeImg);
        }

        mYouTubeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watchYoutubeVideo(mQuestId);
            }
        });

        setTextQuest(mQuestId, mTextQuest);
        mButtonQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentManager = getActivity().getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                Bundle data = new Bundle();
                data.putString(ARG_QUEST, mTitleQuest.getText().toString());
                data.putInt(ARG_QUEST_ID, mQuestId);
                mCalendarFragment = new CalendarFragment();
                mCalendarFragment.setArguments(data);
                mFragmentTransaction.addToBackStack("quest");
                mFragmentTransaction.replace(R.id.container, mCalendarFragment);
                mFragmentTransaction.commit();
            }
        });
        return view;
    }

    public void watchYoutubeVideo(int questId) {
        String id = "";
        switch (questId) {
            case 1:
                id = "1tFvNFi_mNY";
                break;
            case 2:
                id = "HBu8Io1R1xM";
                break;
        }
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + id));
            startActivity(intent);
        }
    }

    public void setTextQuest(int questId, TextView textQuest) {
        int resId = 0;
        switch (questId) {
            case 1:
                resId = R.string.detail_ds;
                break;
            case 2:
                resId = R.string.detail_cd;
                break;
            case 3:
                resId = R.string.detail_bc;
                break;
        }
        textQuest.setText(resId);
    }

    public void setImageYouTube(int questId, ImageView youTubeImg) {
        int resId = 0;
        switch (questId) {
            case 1:
                resId = R.drawable.detskie;
                break;
            case 2:
                resId = R.drawable.detektiv;
                break;
            case 3:
                resId = R.drawable.detektiv;
                break;
        }
        youTubeImg.setImageResource(resId);
    }

    public void initView(View view) {
        mTitleQuest = (TextView) view.findViewById(R.id.titleQuest);
        mYouTubeImg = (ImageView) view.findViewById(R.id.yb);
        mTextQuest = (TextView) view.findViewById(R.id.textQuest);
        mButtonQuest = (TextView) view.findViewById(R.id.btnQuest);
    }
}
