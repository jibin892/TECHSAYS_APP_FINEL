package in.techsays.Settings;

import android.content.Context;
import android.content.SharedPreferences;


public class ThemePreference {
    private Context context;
    private SharedPreferences sharedPreferences;

    public ThemePreference(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("theme", Context.MODE_PRIVATE);
    }

    public void setNightMode(Boolean state) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("NightMode", state);
        editor.apply();
    }

    public boolean loadNightMode() {
        Boolean state = sharedPreferences.getBoolean("NightMode", false);
        return state;
    }

}
