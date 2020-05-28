package in.techsays.Welocomshow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import in.techsays.R;
import in.techsays.Splash;

public class Welocme_activity extends AppCompatActivity {

    private ViewPager viewPager;
    private RelativeLayout nextLayout, getStartedLayout;
    private WizAdapterOne wizAdapterOne;
    private List<WizModelOne> wizModelOneList;
    private TabLayout tabLayout;
    private Button goNext;
    private Button getStarted, skipButton;
    private PrefManager prefManager;

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
        setContentView(R.layout.activity_welocme_activity);
        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }



        viewPager = findViewById(R.id.vp4);
        nextLayout = findViewById(R.id.ll7);
        getStartedLayout = findViewById(R.id.ll8);
        wizModelOneList = new ArrayList<>();
        wizModelOneList.add(new WizModelOne(R.drawable.intro1, "Burgers", "Download this wonderful illustration art from Undraw. You can find details about it in Libraries section."));
        wizModelOneList.add(new WizModelOne(R.drawable.explore, "Cup Cakes", "Download this wonderful illustration art from Undraw. You can find details about it in Libraries section."));
        wizModelOneList.add(new WizModelOne(R.drawable.dreamjob, "Cookies", "Download this wonderful illustration art from Undraw. You can find details about it in Libraries section."));
        wizModelOneList.add(new WizModelOne(R.drawable.reward, "Cookies", "Download this wonderful illustration art from Undraw. You can find details about it in Libraries section."));


        wizAdapterOne = new WizAdapterOne(getApplicationContext(), wizModelOneList);

        viewPager.setAdapter(wizAdapterOne);
        viewPager.setPageTransformer(false, new DepthPagerTransformer());

        tabLayout = findViewById(R.id.tabLayout1);
        tabLayout.setupWithViewPager(viewPager);

        goNext = findViewById(R.id.goNext);
        getStarted = findViewById(R.id.getStarted);
        skipButton = findViewById(R.id.skipButton);
        goNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewPager.getCurrentItem();
                if (position < wizModelOneList.size()) {
                    position++;
                    nextLayout.setVisibility(View.VISIBLE);
                    skipButton.setVisibility(View.VISIBLE);
                    getStartedLayout.setVisibility(View.GONE);
                    viewPager.setCurrentItem(position);
                } else if (position == wizModelOneList.size() - 1) {
                    doVisibilityOperation();
                }
            }
        });


        SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        if(pref.getBoolean("activity_executed", false)){
            Intent intent = new Intent(this, Splash.class);
            startActivity(intent);
            finish();
        } else {
            SharedPreferences.Editor ed = pref.edit();
            ed.putBoolean("activity_executed", true);
            ed.commit();
        }




        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent splash=new Intent(getApplicationContext(), Splash.class);
                startActivity(splash);
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(wizModelOneList.size() - 1);
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == wizModelOneList.size() - 1) {
                    doVisibilityOperation();
                } else {
                    nextLayout.setVisibility(View.VISIBLE);
                    skipButton.setVisibility(View.VISIBLE);
                    getStartedLayout.setVisibility(View.GONE);
                    getStarted.animate().setInterpolator(new BounceInterpolator()).scaleXBy(1).scaleX(0).scaleYBy(1).scaleY(0);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void doVisibilityOperation() {
        getStartedLayout.setVisibility(View.VISIBLE);
        nextLayout.setVisibility(View.GONE);
        skipButton.setVisibility(View.INVISIBLE);
        getStarted.animate()
                .setInterpolator(new BounceInterpolator())
                .setDuration(1000)
                .scaleXBy(0)
                .scaleX(1)
                .scaleYBy(0)
                .scaleY(1);
    }

    private class DepthPagerTransformer implements ViewPager.PageTransformer {
        public static final float MIN_SCALE = 0.75f;

        @Override
        public void transformPage(@NonNull View page, float position) {
            int pageWidth = page.getWidth();

            if (position < -1) {
                //left most
                page.setAlpha(0);
            } else if (position <= 0) {
                //from center to first left
                page.setAlpha(1);
                page.setTranslationX(0);
                page.setScaleY(1);
                page.setScaleX(1);

            } else if (position <= 1) {
                //at center to first right
                page.setAlpha(1 - position);
                page.setTranslationX(pageWidth * -position);

                float scaling = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
                page.setScaleX(scaling);
                page.setScaleY(scaling);
                page.animate().setInterpolator(new AccelerateDecelerateInterpolator());
            } else {
                page.setAlpha(0);
            }

        }
    }
    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(Welocme_activity.this, Splash.class));
        finish();
    }

}


