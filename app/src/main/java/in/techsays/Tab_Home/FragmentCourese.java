package in.techsays.Tab_Home;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import in.techsays.About_us.About_us_home;
import in.techsays.Course_details.Android_details;
import in.techsays.Course_details.Digitalmarketing_details;
import in.techsays.Course_details.Webdevulupment_details;
import in.techsays.R;



public class FragmentCourese extends Fragment {
TextView android,digitalmarketing,websitedevelopentebtn;

    public FragmentCourese() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.courses, container, false);

android=root.findViewById(R.id.androidcouresebtn);
        digitalmarketing=root.findViewById(R.id.digitalmarktinfo);
        websitedevelopentebtn=root.findViewById(R.id.websitedevelopentebtn);

android.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        Intent us = new Intent(getActivity(), Android_details.class);

        startActivity(us);


    }
});


        digitalmarketing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent us = new Intent(getActivity(), Digitalmarketing_details.class);

                startActivity(us);


            }
        });

        websitedevelopentebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent usw = new Intent(getActivity(), Webdevulupment_details.class);

                startActivity(usw);


            }
        });


        return root;
    }





}
