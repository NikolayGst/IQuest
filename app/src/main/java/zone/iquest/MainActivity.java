package zone.iquest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import zone.iquest.Fragment.AboutFragment;
import zone.iquest.Fragment.EditDataDialogFragment;
import zone.iquest.Fragment.QuestPagerFragment;
import zone.iquest.Utils.PreferenceHelper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FragmentManager mFragmentManager;
    private QuestPagerFragment mQuestPagerFragment;
    private PreferenceHelper mPreferenceHelper;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        PreferenceHelper.getInstance().init(this);
        mPreferenceHelper = PreferenceHelper.getInstance();

        initFragment();
        mFragmentManager = getSupportFragmentManager();

        mFragmentManager.beginTransaction()
                .replace(R.id.container, mQuestPagerFragment)
                .commit();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);

        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_quest) {
            home();
        } else if (id == R.id.nav_about) {
            showAboutFragment();
        } else if (id == R.id.nav_edit) {
            showhDialog();
        } else if (id == R.id.nav_exit) {
            exit();
        } else if (id == R.id.nav_share) {
            sharingData();
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showDialog() {
        FragmentManager fm = getSupportFragmentManager();
        EditDataDialogFragment editDialog = new EditDataDialogFragment();
        editDialog.show(fm, "fragment_edit_data");
    }

    public void sharingData() {
        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String textToSend = "У меня есть крутое приложение для IQuest, скачивай и ты по ссылке ...";
        intent.putExtra(Intent.EXTRA_TEXT, textToSend);
        try {
            startActivity(Intent.createChooser(intent, "Поделиться приложением"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "Ошибка " + ex.getLocalizedMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void showAboutFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new AboutFragment())
                .addToBackStack("quest")
                .commit();
    }

    public void exit() {
        mPreferenceHelper.saveSplash(PreferenceHelper.SPLASH_KEY, false);
        mPreferenceHelper.saveName(PreferenceHelper.NAME_KEY, "");
        mPreferenceHelper.saveEmail(PreferenceHelper.EMAIL_KEY, "");
        mPreferenceHelper.savePhone(PreferenceHelper.PHONE_KEY, "");
        finish();
    }

    public void clear() {
        FragmentManager fm = getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();
        startActivity(new Intent(MainActivity.this, MainActivity.class));
        finish();
    }

    public void initFragment() {
        mQuestPagerFragment = new QuestPagerFragment();
    }

    public void showhDialog() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showDialog();
            }
        }, 500);
    }

    public void home() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                clear();
                mFragmentManager.beginTransaction()
                        .replace(R.id.container, mQuestPagerFragment)
                        .commit();
            }
        }, 500);
    }
}
