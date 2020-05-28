package in.techsays.Settings;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MySharedPreferences {

    private Context context;
    private String name;
    private SimpleDateFormat dateFormat;
    private Date date;
    private String className;
    private SharedPreferences sharedPreferences1;

    public MySharedPreferences(Context context) {
        this.context = context;
        sharedPreferences1 = context.getSharedPreferences("theme", Context.MODE_PRIVATE);
    }

    public MySharedPreferences(Context context, String name, String className) {
        this.context = context;
        this.name = name;
        this.className = className;


        long mSeconds = System.currentTimeMillis();
        dateFormat = new SimpleDateFormat("MMM dd, HH:mm aa", Locale.getDefault());
        date = new Date(mSeconds);
        dateFormat.format(date);

    }

    public void setData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("default", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Class Name", className);
        editor.putString("Current Date", dateFormat.format(date));
        editor.putString("Class", name);
        editor.clear();

        editor.apply();
    }

    //update box
    public void showUpdatePage(Boolean state) {
        SharedPreferences.Editor editor = sharedPreferences1.edit();
        editor.putBoolean("NotSecondTime", state);
        editor.apply();
    }

    public boolean loadUpdatePage() {
        return sharedPreferences1.getBoolean("NotSecondTime", false);
    }
}
