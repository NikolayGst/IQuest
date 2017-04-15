package zone.iquest.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {

    public static final String INTRO_KEY = "intro_screen";
    public static final String SPLASH_KEY = "splash_screen";
    public static final String NAME_KEY = "name";
    public static final String EMAIL_KEY = "email";
    public static final String PHONE_KEY = "phone";
    private static PreferenceHelper instance;
    public static SharedPreferences preferences;

    private PreferenceHelper() {

    }

    public static PreferenceHelper getInstance() {
        if (instance == null) {
            instance = new PreferenceHelper();
        }
        return instance;
    }

    public void init(Context context) {
        preferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
    }

    public void saveFirstLoad(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void saveSplash(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void saveName(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void saveEmail(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void savePhone(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getName(String key) {
        return preferences.getString(key, "");
    }

    public String getEmail(String key) {
        return preferences.getString(key, "");
    }

    public String getPhone(String key) {
        return preferences.getString(key, "");
    }

    public boolean isNoFirstLoad(String key) {
        return preferences.getBoolean(key, false);
    }

    public boolean isSavedSplash(String key) {
        return preferences.getBoolean(key, false);
    }
}
