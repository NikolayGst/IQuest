package zone.iquest.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import zone.iquest.Fragment.QuestFragment;


public class QuestAdapter extends FragmentPagerAdapter {

    public QuestAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return QuestFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return 3;
    }
}
