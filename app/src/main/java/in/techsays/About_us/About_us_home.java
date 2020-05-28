package in.techsays.About_us;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import in.techsays.R;

public class About_us_home extends AppCompatActivity {
FloatingActionButton fab;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.WHITE);
        } else {
            getWindow().setStatusBarColor(Color.BLACK);
        }
        setContentView(R.layout.activity_about_us_home);


        fab=findViewById(R.id.logoaboutus);
        Animation top_curve_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_animation);
        fab.startAnimation(top_curve_anim);
    }
}
