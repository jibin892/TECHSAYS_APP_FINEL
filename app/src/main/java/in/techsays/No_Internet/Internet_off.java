package in.techsays.No_Internet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.material.snackbar.Snackbar;

import in.techsays.Login_and_Register.Login;
import in.techsays.Network_Cheking.MyReceiver;
import in.techsays.Network_Cheking.MyReceiver_Splash;
import in.techsays.R;
import in.techsays.Splash;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.GREEN;

public class Internet_off extends AppCompatActivity {
Button check;
Snackbar s;
    private BroadcastReceiver MyReceiverr = null;

FrameLayout vie;
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

        setContentView(R.layout.activity_internet_off);

        MyReceiverr = new MyReceiver_Splash();
        broadcastIntent();
        check=findViewById(R.id.checkinternet);
        vie=findViewById(R.id.checkview);


        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ConnectivityManager cm=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork=cm.getActiveNetworkInfo();

                boolean isConnected=activeNetwork !=null && activeNetwork.isConnected();
                if (isConnected){




                            Intent i = new Intent(Internet_off.this, Login.class);
                            startActivity(i);
                            finish();

                }
                else{

                    s = Snackbar.make(vie, "Check your internet connection", Snackbar.LENGTH_SHORT);
                    View snackBarView = s.getView();
                    snackBarView.setBackgroundColor(BLACK);
                    s.show();




                }





            }
        });



    }

    private void broadcastIntent() {

        registerReceiver(MyReceiverr, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }
}
