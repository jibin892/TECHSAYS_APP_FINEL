package in.techsays.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import in.techsays.R;

import static android.widget.Toast.LENGTH_LONG;

public class Message_home extends AppCompatActivity implements View.OnClickListener {
    FirebaseListAdapter<ChatMessage> adapter;
    FloatingActionButton fab,fab6, cam;
    SweetAlertDialog pDialog;
    // ShimmerLayout shimmerText;
    ListView listOfMessages;
    CircleImageView img;
    View view;
    ImageView image_message_profile,mesagepic,msgbottamsheet;
    String personPhoto;
    GoogleSignInClient mGoogleSignInClient;
    EditText input;
    String personName;
    String personId;
    CoordinatorLayout views;
    String personEmail;
    private Uri filePath;
    FirebaseStorage storage;
    Bitmap bitmap;
    StorageReference storageReference;
    // request code
    ProgressDialog progress;
    private BottomSheetDialog bottomSheetDialog;
    FirebaseUser user;
    ImageView imgmsg,ivCloseShare;
    TextView messageTime,tobarausernmae;
    DatabaseReference reference;
    DatabaseReference a;
    TextView messageUser;
    SharedPreferences phonepref;
    SharedPreferences sh1;
    TextView sendernname;
    private final int PICK_IMAGE_REQUEST = 71;
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
        setContentView(R.layout.activity_message_home);



        phonepref=getSharedPreferences("databasedata",MODE_PRIVATE);

//        ivCloseShare = (ImageView) findViewById(R.id.ivCloseShare);
//        tobarausernmae = (TextView) findViewById(R.id.tobarausernmae);


        //fab6 = (FloatingActionButton) root.findViewById(R.id.fab6);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setVisibility(View.INVISIBLE);
        // fab.setEnabled(false);
        mesagepic =  findViewById(R.id.mesgpic);
        msgbottamsheet =  findViewById(R.id.msgbottamsheet);
        sendernname =  findViewById(R.id.mesgnamesender);

        user = FirebaseAuth.getInstance().getCurrentUser();
        sendernname.setText(user.getDisplayName());
        Picasso.get().load(String.valueOf(user.getPhotoUrl())).into(mesagepic);

        input = (EditText) findViewById(R.id.input);
        views =  findViewById(R.id.msgvie);

        sh1 = getSharedPreferences("LOGINDATA", MODE_PRIVATE);

        personName = user.getDisplayName();
        String personGivenName =  user.getDisplayName();
        String personFamilyName =  user.getDisplayName();
        personEmail =  user.getEmail();
        personId =  user.getUid();
        personPhoto = String.valueOf( user.getPhotoUrl());
        bottomSheetDialog = new BottomSheetDialog(Message_home.this);
        View bottomSheetDialogView = getLayoutInflater().inflate(R.layout.callbackmsg, null);
        bottomSheetDialog.setContentView(bottomSheetDialogView);


        TextView msgname = bottomSheetDialogView.findViewById(R.id.msggname);
        ImageView pic = bottomSheetDialogView.findViewById(R.id.msggphoto);
        TextView emailmsg = bottomSheetDialogView.findViewById(R.id.msggemail);
        Button msgcalback = bottomSheetDialogView.findViewById(R.id.msgcalback);
        Picasso.get().load(String.valueOf(user.getPhotoUrl())).into(pic);
        msgname.setText(user.getDisplayName());
        emailmsg.setText(user.getEmail());



        msgcalback.setOnClickListener(this);

        msgbottamsheet.setOnClickListener(this);








        listOfMessages = (ListView) findViewById(R.id.list_of_messages);

        input.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().trim().length() == 0) {
                    fab.setVisibility(View.INVISIBLE);
                    //  fab6.setVisibility(View.VISIBLE);

                } else {
                    fab.setVisibility(View.VISIBLE);
                    // fab6.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }


            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (input.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Empty value is not allowed",
                            LENGTH_LONG)
                            .show();

                }

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                else {

                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference namesRef = rootRef.child("MSG").push();
                    Map<String, Object> map = new HashMap<>();
                    map.put("messageText", input.getText().toString());
                    map.put("photo", String.valueOf(personPhoto));
                    map.put("messageUser", personName);
                    map.put("email", personEmail);
                    map.put("id", personId);
                    String mGroupId = rootRef.push().getKey();

                    map.put("idd", mGroupId);
                    String timeStamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
                    map.put("stamp", timeStamp);

                    String currentTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
                    map.put("messageTime", currentTime);
                    namesRef.updateChildren(map);
                    rootRef.child("MSG");
                    input.getText().clear();
                    rootRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            // Log.d(TAG, "This: "+dataSnapshot.getValue());
                            //Toast.makeText(getActivity(),"gfdg",Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

//
                }
            }


        });
        displayChatMessages();



    }


    private void displayChatMessages() {

        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
                R.layout.item_in_message,

                reference = FirebaseDatabase.getInstance().getReference().child("MSG")) {
            @Override
            protected void populateView(View v, final ChatMessage model, final int position) {
                // Get references to the views of message.xml
                final ImageView postimg = v.findViewById(R.id.postimg123);
                final TextView messageText = (TextView) v.findViewById(R.id.message_text);
                messageUser = (TextView) v.findViewById(R.id.message_user);
                messageTime = (TextView) v.findViewById(R.id.message_time);
                final ImageView image_message_profile = v.findViewById(R.id.image_message_profile);
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                Picasso.get().load(model.getPhoto()).into(image_message_profile);
                Picasso.get().load(model.getPhoto1()).resize(700, 700).centerCrop().into(postimg);
//Toast.makeText(getActivity(),model.getStamp(),LENGTH_LONG).show();

                Calendar cal = Calendar.getInstance();
                TimeZone tz = cal.getTimeZone();//get your local time zone.
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                sdf.setTimeZone(tz);//set time zone.
                String localTime = sdf.format(new Date(Long.parseLong(model.getStamp() )* 1000));
                Date date = new Date();
                try {
                    date = sdf.parse(localTime);//get local date
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if(date == null) {
                    //  return null;
                }

                long time = date.getTime();

                Date curDate = currentDate();
                long now = curDate.getTime();
                if (time > now || time <= 0) {
                    //  return null;
                }

                float timeDIM = getTimeDistanceInMinutes(time);

                String timeAgo = null;

                if (timeDIM == 0) {
                    timeAgo = "just now";
                } else if (timeDIM == 1) {
                    //  return  "1 minute";
                    timeAgo="1 minute ago";
                } else if (timeDIM >= 2 && timeDIM <= 44) {
                    timeAgo = (Math.round(timeDIM)) + " minutes ago";
                } else if (timeDIM >= 45 && timeDIM <= 89) {
                    timeAgo = " 1 hour ago";
                } else if (timeDIM >= 90 && timeDIM <= 1439) {
                    timeAgo = "about " + (Math.round(timeDIM / 60)) + " hours";
                } else if (timeDIM >= 1440 && timeDIM <= 2519) {
                    timeAgo = "1 day ago";
                } else if (timeDIM >= 2520 && timeDIM <= 43199) {
                    timeAgo = (Math.round(timeDIM / 1440)) + " days";
                } else if (timeDIM >= 43200 && timeDIM <= 86399) {
                    timeAgo = "about a month ago";
                } else if (timeDIM >= 86400 && timeDIM <= 525599) {
                    timeAgo = (Math.round(timeDIM / 43200)) + " months";
                } else if (timeDIM >= 525600 && timeDIM <= 655199) {
                    timeAgo = "about a year ago";
                } else if (timeDIM >= 655200 && timeDIM <= 914399) {
                    timeAgo = "over a year ago";
                } else if (timeDIM >= 914400 && timeDIM <= 1051199) {
                    timeAgo = "almost 2 years ago";
                } else {
                    timeAgo = "about " + (Math.round(timeDIM / 525600)) + " years";
                }
                // return timeAgo + " ago";
                messageTime.setText(timeAgo);







                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if(dataSnapshot.exists()){
//
//
//                            mShimmerViewContainer.stopShimmerAnimation();
//                            mShimmerViewContainer.setVisibility(View.GONE);
//                        }
//
//                        else{
//
//                            mShimmerViewContainer.stopShimmerAnimation();
//                            mShimmerViewContainer.setVisibility(View.GONE);
//
//                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //  }



            }

//            @Override
//            public View getView(int position, View view, ViewGroup viewGroup) {
//                ChatMessage chatMessage = getItem(position);
//                if (chatMessage.getId().equals(personId)) {
//
//
//                    view = getLayoutInflater().inflate(R.layout.item_out_message, viewGroup, false);
//
//
//
//
//                } else {
//                    view = getLayoutInflater().inflate(R.layout.item_in_message, viewGroup, false);
////                    String timeStamps = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
////
////                    if(chatMessage.getStamp()==timeStamps) {
////                        Crouton.makeText(getActivity(), "name" + "\t" + chatMessage.getMessageUser() + "\t" + chatMessage.getMessageText(), Style.INFO).show();
////                    }
//                }
////generating view}
//                populateView(view, chatMessage, position);
//
//                return view;
//            }

            @Override
            public int getViewTypeCount() {
// return the total number of view types. this value should never change
// at runtime
                return 2;
            }

            @Override
            public int getItemViewType(int position) {
// return a value between 0 and (getViewTypeCount - 1)
                return position % 2;
            }

        };


        listOfMessages.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
    //    @Override
//    public void onResume() {
//        super.onResume();
//
//        mShimmerViewContainer.startShimmerAnimation();
//    }
//
//    @Override
//    public void onPause() {
//        mShimmerViewContainer.stopShimmerAnimation();
//        super.onPause();
//
//    }
    public static Date currentDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    private static int getTimeDistanceInMinutes(long time) {
        long timeDistance = currentDate().getTime() - time;
        return Math.round((Math.abs(timeDistance) / 1000) / 60);
    }



    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {

            case R.id.msgbottamsheet:
                bottomSheetDialog.show();
                break;
            case R.id.msgcalback:

                StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://techsays.in/sms.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Snackbar.make(views,response, BaseTransientBottomBar.LENGTH_LONG).show();



                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
//You can handle error here if you want
                            }

                        }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
//Adding parameters to request
                        params.put("phone", phonepref.getString("phone",null));
                        params.put("name",user.getDisplayName());
//returning parameter
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(Message_home.this);
                requestQueue.add(stringRequest);
                break;


        }

    }

}




