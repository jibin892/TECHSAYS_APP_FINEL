package in.techsays.Callback;


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
import androidx.fragment.app.Fragment;


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

import in.techsays.Login_and_Register.Registration;
import in.techsays.R;
import in.techsays.Tab_Home.Home;

import static android.content.Context.MODE_PRIVATE;

public class CallbackFragment extends DialogFragment {
NestedScrollView vie;
Button callback,skip;
FirebaseUser user;
    SharedPreferences phonepref;

    public CallbackFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            View root = inflater.inflate(R.layout.fragment_callback, container, false);
        phonepref=getActivity().getSharedPreferences("databasedata",MODE_PRIVATE);
        user = FirebaseAuth.getInstance().getCurrentUser();

        callback=root.findViewById(R.id.callback);
        skip=root.findViewById(R.id.callbackskip);
        vie=root.findViewById(R.id.callbackview);

skip.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        final Intent skips = new Intent(getActivity(), Home.class);
            startActivity(skips);
    }
});


callback.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        final ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setMessage("Please wait... ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //  progress.setIndeterminate(true);
        progress.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://techsays.in/sms.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Snackbar.make(vie,response, BaseTransientBottomBar.LENGTH_LONG).show();
                        progress.dismiss();
                        final Intent skips = new Intent(getActivity(), Home.class);
                        startActivity(skips);


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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


    }
});


        return root;



    }

}
