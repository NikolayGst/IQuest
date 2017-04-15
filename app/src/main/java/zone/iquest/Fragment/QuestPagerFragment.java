package zone.iquest.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import zone.iquest.Adapter.QuestAdapter;
import zone.iquest.R;


public class QuestPagerFragment extends Fragment {


    public QuestPagerFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment__pager_quest, container, false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        QuestAdapter questAdapter = new QuestAdapter(getChildFragmentManager());
        viewPager.setAdapter(questAdapter);
        return view;

    }

}
