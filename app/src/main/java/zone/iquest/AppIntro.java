package zone.iquest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro2;

import zone.iquest.Utils.PreferenceHelper;
import zone.iquest.Fragment.Slide;


public class AppIntro extends AppIntro2 {

    private PreferenceHelper mPreferenceHelper;

    @Override
    public void init(Bundle savedInstanceState) {
        PreferenceHelper.getInstance().init(this);
        mPreferenceHelper = PreferenceHelper.getInstance();
        if (mPreferenceHelper.isNoFirstLoad(PreferenceHelper.INTRO_KEY)) {
            loadActivity();
        } else {
            addSlide(Slide.newInstance(R.layout.intro_1));
            addSlide(Slide.newInstance(R.layout.intro_2));
            addSlide(Slide.newInstance(R.layout.intro_3));

        }
       selectedIndicatorColor = Color.parseColor("#303030");
        unselectedIndicatorColor = Color.parseColor("#FF878787");


    }



    private void loadActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onDonePressed() {
        loadActivity();
        mPreferenceHelper.saveFirstLoad(PreferenceHelper.INTRO_KEY, true);
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onSlideChanged() {

    }


}
