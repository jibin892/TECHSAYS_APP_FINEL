package in.techsays.Tab_Home;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import in.techsays.R;


public class FragmentTutorials extends Fragment {


    public FragmentTutorials() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tutorials, container, false);


        return root;
    }





}
