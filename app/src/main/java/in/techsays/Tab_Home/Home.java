package in.techsays.Tab_Home;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import in.techsays.About_us.About_us_home;
import in.techsays.Callback.CallbackFragment;
import in.techsays.Contact.Contact_home;
import in.techsays.Login_and_Register.Login;
import in.techsays.Message.Message_home;
import in.techsays.Network_Cheking.MyReceiver;
import in.techsays.Playstore_rating.RatingFragment;
import in.techsays.R;
import in.techsays.Settings.MySharedPreferences;
import in.techsays.Settings.Settings_home;
import in.techsays.Settings.ThemePreference;
import in.techsays.Website.Website_home;

public class Home extends AppCompatActivity implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SectionsPagerAdapterNews sectionsPagerAdapterNews;
    String ph;
    private BottomSheetDialog bottomSheetDialog;

    private NestedScrollView nestedScrollView;
    private Interpolator interpolator;
    private Context context = this;
    final int RequestPermissionCode=1;
    private BroadcastReceiver MyReceiverr = null;
     DrawerLayout drawer;
    ImageView homeimg,webhome,msgsent;
SharedPreferences she,sh;
     FirebaseUser user;
    private ThemePreference themePreference;

    ImageView profileimglogout;
    int doubleBackToExitPressed = 1;
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
        setContentView(R.layout.navigation);

        ThemePreference themePreference = new ThemePreference(Home.this);
        if (themePreference.loadNightMode()) {
            setTheme(R.style.DarkTheme);
        } else setTheme(R.style.AppTheme);



        user = FirebaseAuth.getInstance().getCurrentUser();
EnableRuntimePermission();
        sh = getSharedPreferences("log", MODE_PRIVATE);
        she=getSharedPreferences("log",MODE_PRIVATE);
        SharedPreferences.Editor e=she.edit();
        e.putBoolean("id",true);
        e.apply();
        userdataload();
        MyReceiverr = new MyReceiver();
        broadcastIntent();
//


        interpolator = new FastOutSlowInInterpolator();
        tabLayout = findViewById(R.id.news_tab);
        homeimg = findViewById(R.id.homeimg);
        webhome = findViewById(R.id.ewbviewshome);
        msgsent = findViewById(R.id.homemsgsent);

        viewPager = findViewById(R.id.container11);
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        sectionsPagerAdapterNews = new SectionsPagerAdapterNews(getSupportFragmentManager());

        viewPager.setAdapter(sectionsPagerAdapterNews);


        Picasso.get().load(String.valueOf(user.getPhotoUrl())).into(homeimg);

        bottomSheetDialog = new BottomSheetDialog(Home.this);
        View bottomSheetDialogView = getLayoutInflater().inflate(R.layout.logout_and_info, null);
        bottomSheetDialog.setContentView(bottomSheetDialogView);
webhome.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Intent log= new Intent(Home.this, Website_home.class);
        startActivity(log);


    }
});

        msgsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent log= new Intent(Home.this, Message_home.class);
                startActivity(log);


            }
        });

    drawer = findViewById(R.id.navmain);
        ImageView menuIcon = (ImageView) findViewById(R.id.navbtn);
        menuIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigations);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        ImageView profileImageView = headerView.findViewById(R.id.navprofile);
        TextView navname = headerView.findViewById(R.id.navname);
        TextView navemail = headerView.findViewById(R.id.navemail);
        navemail.setText(user.getEmail());
        Picasso.get().load(String.valueOf(user.getPhotoUrl())).into(profileImageView);
        navname.setText(user.getDisplayName());

        Button logout = bottomSheetDialogView.findViewById(R.id.logout);
        profileimglogout = bottomSheetDialogView.findViewById(R.id.profileimglogout);
        TextView logoutemail = bottomSheetDialogView.findViewById(R.id.logoutemail);

        TextView logoutname = bottomSheetDialogView.findViewById(R.id.logoutname);
        RelativeLayout facebook = bottomSheetDialogView.findViewById(R.id.faceBookBtnlogout);
        RelativeLayout googel = bottomSheetDialogView.findViewById(R.id.sign_in_buttonlogout);
        RelativeLayout insta = bottomSheetDialogView.findViewById(R.id.instagramlogout);
        Picasso.get().load(String.valueOf(user.getPhotoUrl())).into(profileimglogout);
        logoutname.setText(user.getDisplayName());
        logoutemail.setText(user.getEmail());



        logout.setOnClickListener(this);

        profileimglogout.setOnClickListener(this);

        homeimg.setOnClickListener(this);
        facebook.setOnClickListener(this);

        googel.setOnClickListener(this);

        insta.setOnClickListener(this);

    }

    private void broadcastIntent() {

        registerReceiver(MyReceiverr, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }

    private void userdataload() {



            final FirebaseUser userss = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference().child("REGISTRATION").child(userss.getDisplayName());
            mUserDatabase.keepSynced(true);

            mUserDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String id = dataSnapshot.child("id").getValue().toString();
                    ph = dataSnapshot.child("phone").getValue().toString();
                    String name = dataSnapshot.child("personName").getValue().toString();
                    String photo = dataSnapshot.child("personPhoto").getValue().toString();
                    String email = dataSnapshot.child("personEmail").getValue().toString();
                    String personid = dataSnapshot.child("stamp").getValue().toString();


                 //   Toast.makeText(getApplicationContext(),name,Toast.LENGTH_LONG).show();
                    final SharedPreferences sh=getSharedPreferences("databasedata",MODE_PRIVATE);

                    SharedPreferences.Editor ee=sh.edit();

                    ee.putString("id",id);
                    ee.putString("name",name);
                    ee.putString("email",email);
                    ee.putString("image",photo);
                    ee.putString("phone",ph);
                    ee.putString("stamp",personid);

                    ee.apply();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });





    }

    private class SectionsPagerAdapterNews extends FragmentPagerAdapter {

        public SectionsPagerAdapterNews(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                // replace with different fragments
                case 0:
                    return new FragmentCourese();

                case 1:
                    return new Fragmentservices();

                case 2:
                    return new FragmentNotification();

                case 3:
                    return new FragmentTutorials();

                case 4:
                    return new Fragmentpayment();

                case 5:
                    return new FragmentWallet();

                case 6:
                    return new FragmentProfile();
                case 7:
                    return new FragmentFeedback();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 8;
        }
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

//    @Override
//    public void onBackPressed() {
//
//            if (doubleBackToExitPressedOnce) {
//                super.onBackPressed();
//                return;
//            }
//
////      else  if (drawer.isDrawerOpen(Gravity.LEFT)) {
////            drawer.closeDrawer(Gravity.LEFT);
////        }
////      else {
////            super.onBackPressed();
////        }
//
//            this.doubleBackToExitPressedOnce = true;
//            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
//
//            new Handler().postDelayed(new Runnable() {
//
//                @Override
//                public void run() {
//                    doubleBackToExitPressedOnce=false;
//                }
//            }, 2000);
////
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            final Intent log12 = new Intent(Home.this, Home.class);

            startActivity(log12);        }


        else if (id == R.id.nav_contact) {


         Intent con = new Intent(Home.this, Contact_home.class);

            startActivity(con);


        }

//        else if (id == R.id.nav_payment) {
//
//
//            final Intent pay = new Intent(Home.this, Payment.class);
//
//            startActivity(pay);
//
//
//        }

        else if (id == R.id.nav_msg) {


                    Intent l= new Intent(Home.this, Message_home.class);
                    startActivity(l);




        }

        else if (id == R.id.nav_rate) {
            RatingFragment simpleRatingFragment = new RatingFragment();
            simpleRatingFragment.show(getSupportFragmentManager(), "Ratings");


        }

        else if (id == R.id.nav_about) {

            Intent us = new Intent(Home.this, About_us_home.class);

            startActivity(us);


        }


        else if (id == R.id.nav_settings) {

         Intent setting = new Intent(Home.this, Settings_home.class);

            startActivity(setting);


        }
        else if (id == R.id.nav_callbackk) {



            CallbackFragment greetingsFragment = new CallbackFragment();
            greetingsFragment.show(getSupportFragmentManager(), "Greetings");

//            AlertDialog.Builder builder = new AlertDialog.Builder(
//                    Home.this);
//            builder.setTitle("Are you sure?");
//            builder.setMessage("Call Back Now");
//            builder.setNegativeButton("NO",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog,
//                                            int which) {
//                            Toast.makeText(getApplicationContext(),"Cancel",Toast.LENGTH_LONG).show();
//                        }
//                    });
//            builder.setPositiveButton("YES",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog,
//                                            int which) {
//                            callback();
//                        }
//                    });
//            builder.show();
//
//




        }

        else if (id == R.id.nav_share) {
            Bitmap imgBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.reward);
            String imgBitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(),imgBitmap,"Techsays",null);
            Uri imgBitmapUri = Uri.parse(imgBitmapPath);
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shareIntent.putExtra(Intent.EXTRA_STREAM,imgBitmapUri);
            shareIntent.setType("*/*");
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Download Techsays's Official App From Play Store:https://play.google.com/store/apps/details?id="+getPackageName()+"\n"+" And get Recharged Your Phone with Our Watch and earn Task.For ,More details contact us +91 9847423836, +91 8848968113");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Techsays");
            startActivity(Intent.createChooser(shareIntent, "Share this"));

        }
        else if (id == R.id.nav_logout) {



            final ProgressDialog progress = new ProgressDialog(Home.this);
            progress.setTitle("Loading");
            progress.setMessage("Wait while loading...");
            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
            progress.show();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user.getUid() != null) {

                final SharedPreferences sh1 = getSharedPreferences("userdatastemp", MODE_PRIVATE);

                SharedPreferences.Editor ee = sh1.edit();

                ee.putString("id", user.getUid());
                ee.putString("name", user.getDisplayName());
                ee.putString("email", user.getEmail());
                ee.putString("pid", user.getProviderId());
                ee.putString("image", String.valueOf(user.getPhotoUrl()));

                ee.apply();


                // user is now signed out
                SharedPreferences.Editor e = sh.edit();
                e.clear();
                e.apply();
                startActivity(new Intent(getApplicationContext(), Login.class));
                // finish();

                progress.dismiss();


            } else {
                Toast.makeText(getApplicationContext(), "somthing Wrong", Toast.LENGTH_LONG).show();


            }
        }
        drawer.closeDrawer(Gravity.LEFT);
        return true;
    }


    public void EnableRuntimePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(Home.this,
                Manifest.permission.CALL_PHONE)) {

// Toast.makeText(Cpature_image.this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(Home.this, new String[]{
                    Manifest.permission.CALL_PHONE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.ACCESS_NETWORK_STATE,Manifest.permission.ACCESS_WIFI_STATE,Manifest.permission.CHANGE_NETWORK_STATE,Manifest.permission.CHANGE_WIFI_STATE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, RequestPermissionCode);


        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

// Toast.makeText(Cpature_image.this,"Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(this, "Permission Cancelled,Please Grant Permission", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {

            case R.id.homeimg:
                bottomSheetDialog.show();
                break;
            case R.id.logout:
                logoutt();

                break;

            case R.id.faceBookBtnlogout:
                fbook();

                break;


            case R.id.sign_in_buttonlogout:
                gmail();

                break;

            case R.id.instagramlogout:
                instagram();

                break;


        }

    }
        private void gmail() {


            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/pscTrolls"));
            startActivity(intent);




        }

        private void instagram() {



            Uri uri = Uri.parse("https://instagram.com/_techsays_software_solutions/");
            Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

            likeIng.setPackage("com.instagram.android");

            try {
                startActivity(likeIng);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://instagram.com/_techsays_software_solutions/")));
            }

        }

        private void fbook() {


            String facebookUrl = "https://www.facebook.com/techsayssoftwaresolutions/?modal=admin_todo_tour";
            String facebookID = "111754506931637";

            try {
                int versionCode = getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;

                if (!facebookID.isEmpty()) {
                    // open the Facebook app using facebookID (fb://profile/facebookID or fb://page/facebookID)
                    Uri uri = Uri.parse("fb://page/" + facebookID);
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                } else if (versionCode >= 3002850 && !facebookUrl.isEmpty()) {
                    // open Facebook app using facebook url
                    Uri uri = Uri.parse("fb://facewebmodal/f?href=" + facebookUrl);
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                } else {
                    // Facebook is not installed. Open the browser
                    Uri uri = Uri.parse(facebookUrl);
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }
            } catch (PackageManager.NameNotFoundException e) {
                // Facebook is not installed. Open the browser
                Uri uri = Uri.parse(facebookUrl);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }




        }

        private void logoutt() {
            final ProgressDialog progress = new ProgressDialog(Home.this);
            progress.setTitle("Loading");
            progress.setMessage("Wait while loading...");
            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
            progress.show();
            user = FirebaseAuth.getInstance().getCurrentUser();
            if (user.getUid() != null) {

                final SharedPreferences sh1 = getSharedPreferences("userdatastemp", MODE_PRIVATE);

                SharedPreferences.Editor ee = sh1.edit();

                ee.putString("id", user.getUid());
                ee.putString("name", user.getDisplayName());
                ee.putString("email", user.getEmail());
                ee.putString("pid", user.getProviderId());
                ee.putString("image", String.valueOf(user.getPhotoUrl()));

                ee.apply();


                // user is now signed out
                SharedPreferences.Editor e = sh.edit();
                e.clear();
                e.apply();
                startActivity(new Intent(getApplicationContext(), Login.class));
                // finish();

                progress.dismiss();


            } else {
                Toast.makeText(getApplicationContext(), "somthing Wrong", Toast.LENGTH_LONG).show();


            }

}


}
