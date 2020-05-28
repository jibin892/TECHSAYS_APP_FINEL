package in.techsays.Playstore_rating;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.DialogFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

import in.techsays.R;
import in.techsays.Tab_Home.Home;

import static android.content.Context.MODE_PRIVATE;

public class RatingFragment extends DialogFragment {
Button later;
FirebaseUser user;
    SharedPreferences phonepref;

    public RatingFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            View root = inflater.inflate(R.layout.fragment_rating, container, false);
        phonepref=getActivity().getSharedPreferences("databasedata",MODE_PRIVATE);
        user = FirebaseAuth.getInstance().getCurrentUser();

        later=root.findViewById(R.id.laterrating);


        later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Intent skips = new Intent(getActivity(), Home.class);
                startActivity(skips);
            }
        });


        return root;



    }

}
