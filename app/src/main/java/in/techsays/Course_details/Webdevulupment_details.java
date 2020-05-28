package in.techsays.Course_details;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import in.techsays.R;
import in.techsays.Settings.MySharedPreferences;

public class Webdevulupment_details extends AppCompatActivity implements Android_AppAdapter.CodexRecyclerViewClickListener {

    private RecyclerView recyclerView;
    private ArrayList<AppModel> buttonModelArrayList;
    private Android_AppAdapter buttonAdapter;
    private boolean isOpen = false;
    private ImageView imageView;
    private String heads[] = {"Basic", "Colored", "Custom", "Material"};
    int support[] = {R.drawable.ic_about_black_24dp, R.drawable.ic_about_black_24dp,
            R.drawable.ic_about_black_24dp, R.drawable.ic_about_black_24dp};
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
        setContentView(R.layout.activity_webdevulupment_details);
        MySharedPreferences preferences = new MySharedPreferences(getApplicationContext(), "Buttons", getClass().getName());
        preferences.setData();

        getWindow().setEnterTransition(null);

//        Objects.requireNonNull(getSupportActionBar()).setTitle(null);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.rv13);

        buttonModelArrayList = new ArrayList<>();
        for (int i = 0; i < heads.length; i++) {
            AppModel buttonModel = new AppModel();
            buttonModel.setHead(heads[i]);
           buttonModel.setRotation(support[i]);
            buttonModelArrayList.add(buttonModel);
        }
        buttonAdapter = new Android_AppAdapter(buttonModelArrayList, getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(buttonAdapter);

       // ImageView imageView = findViewById(R.id.auth_icon);
      //  imageView.setImageResource(R.drawable.ic_id_open_black_24dp);
        TextView textView1 = findViewById(R.id.setBalanceAmount);
        AppBarLayout appBarLayout = findViewById(R.id.appbar4);
     //   appBarLayout.addOnOffsetChangedListener(new CustomAppBarOffset(this,textView,textView1,toolbar,"Buttons", imageView));
//        if (themePreference.loadNightMode()) {
//            toolbar.getContext().setTheme(R.style.ThemeOverlay_AppCompat_Dark_ActionBar);
//            imageView.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(
//                    getApplicationContext(), R.color.blueTransparent
//            )));
//            imageView.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(
//                    getApplicationContext(), R.color.materialBlue
//            )));
//        }
        buttonAdapter.setListener(this);
        handleFab();
    }

    private void handleFab() {
        FloatingActionButton floatingActionButton = findViewById(R.id.fab_main);
        final LinearLayout linearLayout = findViewById(R.id.hided_layer1);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isOpen = !isOpen;
                if (isOpen) {
                    linearLayout.setVisibility(View.VISIBLE);
                } else {
                    linearLayout.setVisibility(View.GONE);
                }

            }
        });

    FloatingActionButton button = findViewById(R.id.open_web);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

    }

    @Override
    public void onClicked(int position, Android_AppAdapter.ViewHolder viewHolder) {
        switch (position) {
            case 0:
              //  startActivity(new Intent(getApplicationContext(), BasicButtons.class));
                break;

            case 1:
             //   startActivity(new Intent(getApplicationContext(), ColoredButtons.class));
                break;

            case 2:
              //  startActivity(new Intent(getApplicationContext(), CustomButtons.class));
                break;

            case 3:
              //  startActivity(new Intent(getApplicationContext(), NewMaterialButtons.class));
                break;

            case 4:
              //  startActivity(new Intent(getApplicationContext(), OtherButtons.class));
                break;

            default:
              //  Toast.makeText(getApplicationContext(), "No Class Found", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
