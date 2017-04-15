package zone.iquest.Fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import zone.iquest.Provider.VolleyRequest;
import zone.iquest.R;
import zone.iquest.Utils.EventDecorator;


public class CalendarFragment extends Fragment {
    private static final String ARG_QUEST = "quest";
    private static final String ARG_QUEST_DATE = "quest_date";
    private static final String ARG_QUEST_ID = "quest_id";
    private Collection<CalendarDay> mCalendarDays;
    private Calendar mCalendar;
    private List<HashMap<String, String>> mListDate;
    private HashMap<String, String> mHashMap;
    private List<CalendarDay> mCalendarDayList;
    private HashMap<String, String> mArrayDate;
    private String mDate;
    private String mStatus;
    private String questName;
    private int mColor;
    private int questId;
    private List<String> mOne;
    private List<String> mTwo;
    private FragmentManager mFragmentManager;
    private SeanceFragment mSeanceFragment;
    private VolleyRequest mVolleyRequest;
    private TextView nameQuest;
    private MaterialCalendarView materialCalendarView;
    private ImageView mBackground;
    private static CalendarFragment instance;

    public static CalendarFragment getInstance() {
        return instance;
    }

    public CalendarFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        if (instance == null) {
            instance = this;
        }

        mFragmentManager = getActivity().getSupportFragmentManager();
        mSeanceFragment = new SeanceFragment();

        mCalendar = Calendar.getInstance();

        mVolleyRequest = new VolleyRequest();

        mCalendarDayList = new ArrayList<>();
        mOne = new ArrayList<>();
        mTwo = new ArrayList<>();
        mListDate = new ArrayList<>();

        Bundle args = getArguments();
        questId = args.getInt(ARG_QUEST_ID);
        mVolleyRequest.getDay(getActivity(), questId, mListDate);
        mBackground = (ImageView) view.findViewById(R.id.bg);
        setBackground(questId, mBackground);

        nameQuest = (TextView) view.findViewById(R.id.nameQuest);
        questName = args.getString(ARG_QUEST);
        nameQuest.setText("Расписание для квеста:\n" + questName);

        materialCalendarView = (MaterialCalendarView)
                view.findViewById(R.id.calendarView);

        materialCalendarView.setMinimumDate(mCalendar.getTime());
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget,
                                       @NonNull CalendarDay date,
                                       boolean selected) {
                int dayOfMonth = date.getDay();
                int month = date.getMonth() + 1;
                int year = date.getYear();
                String dateStr;
                if (month < 10 && dayOfMonth < 10) {
                    dateStr = year + "-" + "0" + month + "-" + "0" + dayOfMonth;
                } else if (month < 10) {
                    dateStr = year + "-" + "0" + month + "-" + dayOfMonth;
                } else if (dayOfMonth < 10) {
                    dateStr = year + "-" + month + "-" + "0" + dayOfMonth;
                } else {
                    dateStr = year + "-" + month + "-" + dayOfMonth;
                }
                Bundle quest = new Bundle();
                quest.putString(ARG_QUEST, questName);
                quest.putString(ARG_QUEST_DATE, dateStr);
                quest.putInt(ARG_QUEST_ID, questId);
                mSeanceFragment.setArguments(quest);
                mFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, mSeanceFragment)
                        .addToBackStack("quest")
                        .commit();
                if (mListDate != null && mCalendarDayList != null &&
                        mListDate != null &&
                        mOne != null &&
                        mTwo != null &&
                        mArrayDate != null) clearData();
            }

        });
        return view;
    }

    public void setDate() {
        for (int i = 0; i < mListDate.size(); i++) {
            mArrayDate = mListDate.get(i);
            mDate = mArrayDate.get("mDate");
            mStatus = mArrayDate.get("mStatus");
            if (mStatus.equals("0")) {
                mColor = Color.GREEN;
                mOne.add(mDate);
                setDateIndicator(mOne, mColor);
            } else {
                mColor = Color.RED;
                mTwo.add(mDate);
                setDateIndicator(mTwo, mColor);
            }
        }
        Log.d("tag", "Status 0: " + Arrays.asList(mOne));
        Log.d("tag", "Status 1: " + Arrays.asList(mTwo));
    }

    public void clearData() {
        mCalendarDayList.clear();
        mListDate.clear();
        mOne.clear();
        mTwo.clear();
        mArrayDate.clear();

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

    public void setDateIndicator(List<String> array, int color) {
        for (String date : array) {
            String parts[] = date.split("-");
            // might be a more elegant way to do this part, but this is very explicit
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]) - 1;
            int day = Integer.parseInt(parts[2]);
            mCalendar.set(year, month, day);
            CalendarDay calendarDay = CalendarDay.from(mCalendar);
            mCalendarDayList.add(calendarDay);
        }
        mCalendarDays = mCalendarDayList;
        materialCalendarView.addDecorators(new EventDecorator(color, mCalendarDays));
        mCalendarDays.clear();
        mCalendarDayList.clear();
    }

    @Override
    public void onStop() {
        super.onStop();
        instance = null;
    }
}

