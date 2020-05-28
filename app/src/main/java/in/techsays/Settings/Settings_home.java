package in.techsays.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import in.techsays.About_us.About_us_home;
import in.techsays.Callback.CallbackFragment;
import in.techsays.Delete_account.DeletaccountFragment;
import in.techsays.Login_and_Register.Login;
import in.techsays.Playstore_rating.RatingFragment;
import in.techsays.R;
import in.techsays.Tab_Home.Home;

public class Settings_home extends AppCompatActivity  {
    private RecyclerView recyclerView;
    private ArrayList<MainModel> appModelArrayList;
    private SettingsAdapter settingsAdapter;
    private ThemePreference themePreference;
    private NestedScrollView nestedScrollView;
    private AppBarLayout appBarLayout;
    private TextView textView;

    private int images[] = {
            R.drawable.ic_share_black_24dp,
            R.drawable.ic_callback_black_24dp,
            R.drawable.ic_brightness_3_black_24dp,
            R.drawable.ic_language_outline_black_24dp,
            R.drawable.ic_about_black_24dp,
            R.drawable.ic_star_border_black_24dp,
            R.drawable.ic_delete_forever_black_24dp};

    private String names[] = {"Share App",
            "Call Back", "Night Mode", "Change Language","About US",
            "Rating", "Delet Account"};
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.WHITE);
        } else {
            getWindow().setStatusBarColor(Color.BLACK);
        }

        themePreference = new ThemePreference(Objects.requireNonNull(Settings_home.this));

        setContentView(R.layout.activity_settings_home);

        recyclerView = findViewById(R.id.rv32);

        appModelArrayList = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            MainModel appModel = new MainModel();
            appModel.setNames(names[i]);
            appModel.setImages(images[i]);
            appModelArrayList.add(appModel);
        }
        settingsAdapter = new SettingsAdapter(appModelArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(settingsAdapter);

//        appBarLayout = ((Home) Objects.requireNonNull(Settings_home.this.getElevations()));
//        appBarLayout.setElevation(0.0f);
        nestedScrollView = findViewById(R.id.nsv3);
//        textView = ((Home) Objects.requireNonNull(getApplicationContext())).getTextView();
//        textView.setText("Settings");

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    appBarLayout.setElevation(10.0f);

                } else if (scrollX == scrollY) {
                    appBarLayout.setElevation(0.0f);

                }
            }
        });

    }



    private class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {

        private static final String URL = "https://play.google.com/store/apps/details?id=com.projects.sharath.materialvision";
        private ArrayList<MainModel> appModelArrayList;


        public SettingsAdapter(ArrayList<MainModel> appModelArrayList) {
            this.appModelArrayList = appModelArrayList;
        }

        @NonNull
        @Override
        public SettingsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_code_design, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final SettingsAdapter.ViewHolder holder, final int position) {
            holder.textView.setText(appModelArrayList.get(position).getNames());
            holder.textView.setTypeface(holder.textView.getTypeface(), Typeface.NORMAL);
            holder.imageView.setImageResource(appModelArrayList.get(position).getImages());
            if (position == 2) {
                holder.aSwitch1.setVisibility(View.VISIBLE);
            } else {
                holder.aSwitch1.setVisibility(View.GONE);
            }
            if (themePreference.loadNightMode()) {
                holder.aSwitch1.setChecked(true);
            }

            holder.aSwitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    setThePreferences(b);
                }
            });


            holder.setItemClickListener(new MyItemClickListener() {
                @Override
                public void onItemClick(int pos) {
                    notifyDataSetChanged();
                    switch (pos) {
                        case 0:
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
                            break;

                        case 1:
                            CallbackFragment greetingsFragment = new CallbackFragment();
                            greetingsFragment.show(getSupportFragmentManager(), "Greetings");

                            break;

                        case 2:
                            if (holder.aSwitch1.isChecked()) {
                                setThePreferences(false);
                            } else {
                                setThePreferences(true);
                            }
                            break;

                        case 3:
                            AlertDialog.Builder builder = new AlertDialog.Builder(Settings_home.this);
                            builder.setTitle("Phone Ringtone");

                            final String[] items = {"English","हिन्दी","മലയാളം","தமிழ்"};
                            builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });

                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {

                                    if(which==0)
                                    {
                                        setLocale("en");
                                        recreate();

                                    }
                                    else  if(which==1)
                                    {
                                        setLocale("hi");
                                        recreate();

                                    }
                                    else  if(which==2)
                                    {
                                        setLocale("ml");
                                        recreate();
                                    }
                                    else  if(which==3)
                                    {
                                        setLocale("ta");
                                        recreate();
                                    }
                                    dialogInterface.dismiss();
                                }
                            });

                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    dialogInterface.dismiss();
                                }
                            });

                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();

                            break;
                        case 4:
                            Intent intent3 = new Intent(getApplicationContext(), About_us_home.class);
                            startActivity(intent3);
                            break;

                        case 5:
                             RatingFragment simpleRatingFragment = new RatingFragment();
                           simpleRatingFragment.show(getSupportFragmentManager(), "Ratings");
                            break;

                        case 6:
                            DeletaccountFragment deletaccountFragment = new DeletaccountFragment();
                            deletaccountFragment.show(getSupportFragmentManager(), "Delete");
                            break;

                    }
                }
            });

        }

//        private void loadLocale()
//        {
//            SharedPreferences sharedPreferences=getSharedPreferences("Settings",MODE_PRIVATE);
//            String language=sharedPreferences.getString("my","");
//            setLocale(language);
//
//        }


        private void setThePreferences(boolean b) {
            if (b) {
                themePreference.setNightMode(true);
                restartApp();
            } else {
                themePreference.setNightMode(false);
                restartApp();
            }
        }


        private void restartApp() {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        @Override
        public int getItemCount() {
            return appModelArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private TextView textView;
            private ImageView imageView;
            public MyItemClickListener itemClickListener;
            private Switch aSwitch1;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.names1);
                imageView = itemView.findViewById(R.id.images1);
                aSwitch1 = itemView.findViewById(R.id.switch3);
                itemView.setOnClickListener(this);
            }

            public void setItemClickListener(MyItemClickListener itemClickListener) {
                this.itemClickListener = itemClickListener;
            }

            @Override
            public void onClick(View view) {
                this.itemClickListener.onItemClick(this.getAdapterPosition());
            }
        }
    }

    public void setLocale(String hi) {
        Locale locale=new Locale(hi);
        Locale.setDefault(locale);
        Configuration configuration=new Configuration();
        configuration.locale=locale;
        getResources().updateConfiguration(configuration,getResources().getDisplayMetrics());
        SharedPreferences sh=getSharedPreferences("Settings",MODE_PRIVATE);
        SharedPreferences.Editor editor=sh.edit();
        editor.putString("my",hi);
        editor.apply();
    }
    private void loadLocale() {

        SharedPreferences sharedPreferences=getSharedPreferences("Settings",MODE_PRIVATE);
        String language=sharedPreferences.getString("my","");
        setLocale(language);


    }
}
