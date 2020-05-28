package in.techsays;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import in.techsays.Login_and_Register.Login;
import in.techsays.Tab_Home.Home;

public class Splash extends AppCompatActivity {
    private ImageView imageView;
    private Animation animation;
    int doubleBackToExitPressed = 1;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getColor(android.R.color.white));
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.BLACK);
            }
        }
        setContentView(R.layout.activity_splash);


        imageView = findViewById(R.id.logo_up);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_down_to_center);
        animation.setInterpolator(new AnticipateOvershootInterpolator());
        animation.setDuration(2000);
        imageView.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(Splash.this, Login.class);
                startActivity(i);
                finish();
            }
        }, 2000);

    }

    private void setSupportActionBar(Toolbar toolbar) {
    }


    public void onBackPressed() {
        super.onBackPressed();
        animation.cancel();
        if (doubleBackToExitPressed == 2) {
            finishAffinity();
            System.exit(0);
        }
        else {
            doubleBackToExitPressed++;
            Toast.makeText(this, "Please press Back again to exit", Toast.LENGTH_SHORT).show();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressed=1;
            }
        }, 1000);
    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        animation.cancel();
//    }
}
