package in.techsays.Tab_Home;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import in.techsays.R;

import static android.content.Context.MODE_PRIVATE;


public class FragmentProfile extends Fragment {
SharedPreferences profiledata;
    ImageView webview,profile,backarrow;
    TextView name,email,pname,pph,pemail,pid;
    FirebaseUser user;
    public FragmentProfile() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.profile, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        profiledata=getActivity().getSharedPreferences("databasedata",MODE_PRIVATE);


        pname=root.findViewById(R.id.profilenameedit);
        pemail=root.findViewById(R.id.profileemailedit);
        pph=root.findViewById(R.id.profilephedit);
        pid=root.findViewById(R.id.profileidedit);

        pname.setText(profiledata.getString("name",null));
        pemail.setText(profiledata.getString("email",null));
        pph.setText(profiledata.getString("phone",null));
        pid.setText(profiledata.getString("stamp",null));


        name=root.findViewById(R.id.profilename);
        email=root.findViewById(R.id.profileemail);
        profile=root.findViewById(R.id.profileimg);
        Picasso.get().load(String.valueOf(user.getPhotoUrl())).into(profile);
        name.setText(user.getDisplayName());
        email.setText(user.getEmail());




        return root;
    }





}
