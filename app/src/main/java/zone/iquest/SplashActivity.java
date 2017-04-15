package zone.iquest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import zone.iquest.Utils.PreferenceHelper;
import zone.iquest.Fragment.EnterDataDialogFragment;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;
    private PreferenceHelper mPreferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        PreferenceHelper.getInstance().init(this);
        mPreferenceHelper = PreferenceHelper.getInstance();
        Log.d("tag", "mPreferenceHelper " + mPreferenceHelper.isSavedSplash(PreferenceHelper.SPLASH_KEY));
        if (mPreferenceHelper.isSavedSplash(PreferenceHelper.SPLASH_KEY)) {
            loadActivity();
        } else {
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        showDialog();
    }

    public void loadActivity(){
        startActivity(new Intent(SplashActivity.this,AppIntro.class));
        finish();
    }

    private void showDialog() {
        FragmentManager fm = getSupportFragmentManager();
        EnterDataDialogFragment editNameDialog = new EnterDataDialogFragment();
        editNameDialog.show(fm, "fragment_enter_data");
    }
}
