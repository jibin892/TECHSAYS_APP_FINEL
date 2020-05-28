package in.techsays.Delete_account;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.DialogFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import in.techsays.Login_and_Register.Login;
import in.techsays.R;
import in.techsays.Tab_Home.Home;

import static android.content.Context.MODE_PRIVATE;

public class DeletaccountFragment extends DialogFragment implements View.OnClickListener{
NestedScrollView vie;
Button deletac,skip;
ImageView pic;
TextView name,email,t;
FirebaseUser user;
ImageView profileimgdelet;
    SharedPreferences phonepref,sh;
    private BottomSheetDialog bottomSheetDialog;

    public DeletaccountFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            View root = inflater.inflate(R.layout.fragment_delete, container, false);
        phonepref=getActivity().getSharedPreferences("databasedata",MODE_PRIVATE);
        user = FirebaseAuth.getInstance().getCurrentUser();
        sh = getActivity().getSharedPreferences("log", MODE_PRIVATE);

        deletac=root.findViewById(R.id.deleteaccountbtn);
        name=root.findViewById(R.id.deleteaccountname);
        email=root.findViewById(R.id.deleteaccountemail);
        pic=root.findViewById(R.id.deleteaccountimage);
        t=root.findViewById(R.id.deletaccounttxt);


        Picasso.get().load(String.valueOf(user.getPhotoUrl())).into(pic);
        name.setText(user.getDisplayName());
        email.setText(user.getEmail());


        Animation top_curve_anim = AnimationUtils.loadAnimation(getActivity(),R.anim.slide_in);
        deletac.startAnimation(top_curve_anim);
        name.startAnimation(top_curve_anim);
        email.startAnimation(top_curve_anim);
        pic.startAnimation(top_curve_anim);
        t.startAnimation(top_curve_anim);

        bottomSheetDialog = new BottomSheetDialog(getActivity());
        View bottomSheetDialogView = getLayoutInflater().inflate(R.layout.deleteaccount, null);
        bottomSheetDialog.setContentView(bottomSheetDialogView);



        Button delet1 = bottomSheetDialogView.findViewById(R.id.deletaccounts);
        profileimgdelet = bottomSheetDialogView.findViewById(R.id.profileimgdelet);
        TextView logoutemail = bottomSheetDialogView.findViewById(R.id.deletemail);
        TextView logoutname = bottomSheetDialogView.findViewById(R.id.deletname);
        Button logout1 = bottomSheetDialogView.findViewById(R.id.deletlogout);

//        RelativeLayout facebook = bottomSheetDialogView.findViewById(R.id.faceBookBtnlogout);
//        RelativeLayout googel = bottomSheetDialogView.findViewById(R.id.sign_in_buttonlogout);
//        RelativeLayout insta = bottomSheetDialogView.findViewById(R.id.instagramlogout);
        Picasso.get().load(String.valueOf(user.getPhotoUrl())).into(profileimgdelet);
        logoutname.setText(user.getDisplayName());
        logoutemail.setText(user.getEmail());


        deletac.setOnClickListener(this);

        delet1.setOnClickListener(this);
       logout1.setOnClickListener(this);




//
//skip.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//
//
//        final Intent skips = new Intent(getActivity(), Home.class);
//            startActivity(skips);
//    }
//});



        return root;



    }
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {

            case R.id.deleteaccountbtn:
                bottomSheetDialog.show();
                break;
            case R.id.deletaccounts:
               deletaccount();

                break;

            case R.id.deletlogout:
logout();
                break;




        }

    }

    private void logout() {




        final ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user.getUid() != null) {

            final SharedPreferences sh1 =getActivity(). getSharedPreferences("userdatastemp", MODE_PRIVATE);

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
            startActivity(new Intent(getActivity(), Login.class));
            // finish();

            progress.dismiss();


        } else {
            Toast.makeText(getActivity(), "somthing Wrong", Toast.LENGTH_LONG).show();


        }





    }

    private void deletaccount() {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyCustomAlert1);
        builder.setTitle("Confirmation message");
        builder.setMessage("Are you sure you want to delete this Account");
        builder.setPositiveButton("AGREE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
confirem();
                dialogInterface.dismiss();
            }

            private void confirem() {



                final ProgressDialog progress = new ProgressDialog(getActivity());
                progress.setMessage(" Please wait... ");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                //  progress.setIndeterminate(true);
                progress.show();
                if(user.getUid()!=null) {


                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    Query applesQuery = ref.child("REGISTRATION").orderByChild("id").equalTo(user.getUid());
                    applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                appleSnapshot.getRef().removeValue();


                                AuthUI.getInstance()
                                        .delete(getActivity())

                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            public void onComplete(@NonNull Task<Void> task) {
                                                // user is now signed out
                                                SharedPreferences.Editor e = sh.edit();
                                                e.clear();
                                                e.apply();
                                                startActivity(new Intent(getActivity(), Login.class));
                                                // finish();
                                                progress.dismiss();
                                            }
                                        });


                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Log.e(TAG, "onCancelled", databaseError.toException());
                        }
                    });


                }






            }
        });

        builder.setNegativeButton("DISAGREE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getActivity().finish();
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

}
