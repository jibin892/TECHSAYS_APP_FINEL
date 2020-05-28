package in.techsays.Login_and_Register;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import in.techsays.R;
import in.techsays.Tab_Home.Home;

import static android.graphics.Color.GREEN;

public class Login extends AppCompatActivity {
    ConstraintLayout vie;
    int RC_SIGN_IN = 0;
    RelativeLayout signInButton;
    RelativeLayout facebooknButton;
    RelativeLayout  emailbtn;
    EditText username,pssword;
    Button sign,createaccount;
    Snackbar s;
    private boolean loggedIn = false;
    SharedPreferences sharedPreferences,sh,sh1,sh2;
    RelativeLayout pass,pass1;
    TextView name;
    ProgressBar progressBar;
    ImageView profileimg;
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

        setContentView(R.layout.activity_login);
        vie=findViewById(R.id.vie);

        profileimg=findViewById(R.id.profileimg);
        name=findViewById(R.id.names);


        sh2 = getSharedPreferences("userdatastemp", MODE_PRIVATE);
        String name1=sh2.getString("name", null);
        String img=String.valueOf(sh2.getString("image", null));


        if (name1 != null && img != null )
        {
            Picasso.get().load(sh2.getString("image", null)).into(profileimg);
            name.setText(sh2.getString("name", null));

        }


        else{

            profileimg.setImageResource(R.drawable.user_image);
            name.setText("Hi");
        }







        sh1= Objects.requireNonNull(getApplicationContext()).getSharedPreferences("LOGINDATA",MODE_PRIVATE);

        //  Picasso.get().load(sh1.getString("image",null)).into(profileimg);


        FirebaseApp.initializeApp(this);
        sh=getSharedPreferences("log",MODE_PRIVATE);
        loggedIn=sh.getBoolean("id",false);
        sharedPreferences=getSharedPreferences("phone",MODE_PRIVATE);
        if (loggedIn) {
            startActivity(new Intent(Login.this, Home.class));
            // Snackbar.make(v,"Enter emergency number",Snackbar.LENGTH_SHORT).show();

        }


        signInButton = findViewById(R.id.sign_in_button);
        facebooknButton = findViewById(R.id.faceBookBtn);
        emailbtn = findViewById(R.id.emailbtn);



        Animation top_curve_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.edittext_anim);
        signInButton.startAnimation(top_curve_anim);
        facebooknButton.startAnimation(top_curve_anim);

        emailbtn.startAnimation(top_curve_anim);






        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                List<AuthUI.IdpConfig> providers = Arrays.asList(
                        //  new AuthUI.IdpConfig.PhoneBuilder().build(),
                        new AuthUI.IdpConfig.GoogleBuilder().build()
                        //new AuthUI.IdpConfig.FacebookBuilder().build()
                );
// Create and launch sign-in intent
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .build(),

                        12);


            }
        });



        facebooknButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                List<AuthUI.IdpConfig> providers = Arrays.asList(
                        new AuthUI.IdpConfig.FacebookBuilder().build()

                );
// Create and launch sign-in intent
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .build(),

                        12);



            }
        });

        emailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                List<AuthUI.IdpConfig> providers = Arrays.asList(
                        //   new AuthUI.IdpConfig.TwitterBuilder().build()
                        new AuthUI.IdpConfig.EmailBuilder().build()
                        //  new AuthUI.IdpConfig.GoogleBuilder().build()
                        //  new AuthUI.IdpConfig.FacebookBuilder().build()
                );

// Create and launch sign-in intent
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .build(),

                        12);


            }
        });




    }

    public void onBackPressed() {
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


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 12) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
// Successfully signed in
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final ProgressDialog progress = new ProgressDialog(Login.this);
                progress.setMessage("Login... ");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                //  progress.setIndeterminate(true);
                progress.show();
                if(user!=null){
                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("REGISTRATION");
                    userRef
                            .orderByChild("personPhoto")
                            .equalTo(String.valueOf(user.getPhotoUrl()))
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    if (dataSnapshot.getValue() != null) {

                                        final Intent log = new Intent(Login.this, Home.class);
                                        final SharedPreferences sh=getSharedPreferences("LOGINDATA",MODE_PRIVATE);

                                        SharedPreferences.Editor ee=sh.edit();
                                        ee.putString("id",user.getUid());
                                        ee.putString("name",user.getDisplayName());
                                        ee.putString("email",user.getEmail());
                                        ee.putString("pid",user.getProviderId());
                                        ee.putString("image",String.valueOf(user.getPhotoUrl()));

                                        ee.apply();

                                        startActivity(log);
                                        progress.dismiss();


                                    } else {

                                        final Intent log1 = new Intent(Login.this, Registration.class);

                                        startActivity(log1);

                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                }

            }
            else {
                s = Snackbar.make(vie, "SOMTHING WAS WRONG", Snackbar.LENGTH_SHORT);
                View snackBarView = s.getView();
                snackBarView.setBackgroundColor(GREEN);
                s.show();

                return;
            }
        }

    }

}