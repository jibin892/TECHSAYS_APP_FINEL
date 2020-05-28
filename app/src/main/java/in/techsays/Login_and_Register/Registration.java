package in.techsays.Login_and_Register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import in.techsays.R;
import in.techsays.Tab_Home.Home;

public class Registration extends AppCompatActivity {
    EditText phone;
   ImageButton phbtn;
    private Animation animation;
    private FrameLayout frameLayout;
ImageView as;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getColor(android.R.color.white));
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.BLACK);
            }
        }

        setContentView(R.layout.activity_registration);

        super.onCreate(savedInstanceState);
        frameLayout = findViewById(R.id.frameph);
        as = findViewById(R.id.phlogo);
        phone=findViewById(R.id.phone);
        phbtn=findViewById(R.id.phnext);


        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.item_animation_go_up);
        animation.setStartTime(3000);
        animation.setDuration(3500);
        animation.setRepeatCount(3);
        frameLayout.startAnimation(animation);
        as.startAnimation(animation);
        if(animation.hasEnded()){
            finish();
        }



        phbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                {
                    final ProgressDialog progress = new ProgressDialog(Registration.this);

                    progress.setMessage("Sigun up... ");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    //  progress.setIndeterminate(true);
                    progress.show();
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    DatabaseReference object = FirebaseDatabase.getInstance().getReference();
                    String uid = user.getDisplayName();

                    DatabaseReference namesRef = object.child("REGISTRATION").child(uid);
                    Map<String, Object> map = new HashMap<>();
                    map.put("personName", user.getDisplayName());
                    map.put("personEmail",user.getEmail());
                    map.put("personPhoto",String.valueOf(user.getPhotoUrl()));
                    map.put("id", user.getUid());
                    map.put("phone", phone.getText().toString());

                    String mGroupId = object.push().getKey();

                    map.put("personId", mGroupId);
                    String timeStamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
                    map.put("stamp", timeStamp);
                    String currentTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
                    map.put("messageTime", currentTime);
                    namesRef.updateChildren(map);
                    object.child("REGISTRATION");



                    object.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            //  Crouton.makeText(Loginpage.this, "SUCCSEUS", Style.CONFIRM).show();
                            final Intent log = new Intent(Registration.this, Home.class);

                            final SharedPreferences sh=getSharedPreferences("data",MODE_PRIVATE);

                            SharedPreferences.Editor ee=sh.edit();

                            ee.putString("id",user.getUid());
                            ee.putString("name",user.getDisplayName());
                            ee.putString("email",user.getEmail());
                            ee.putString("pid",user.getProviderId());
                            ee.putString("image",String.valueOf(user.getPhotoUrl()));
                            ee.putString("pid",user.getProviderId());
                            ee.putString("phone",phone.getText().toString());
                            ee.apply();

                            startActivity(log);
                            progress.dismiss();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }


            }
        });

    }
}
