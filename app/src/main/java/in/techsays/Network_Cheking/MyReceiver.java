package in.techsays.Network_Cheking;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import in.techsays.R;
import in.techsays.Tab_Home.Home;

import static java.security.AccessController.getContext;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String status = NetworkUtil.getConnectivityStatusString(context);
        if (status.isEmpty()) {
            status = "No Internet Connection";
        }
        if(status.equals("Wifi enabled"))
        {

        }
        else  if(status.equals("Mobile data enabled"))
        {
           // Toast.makeText(context, "Mobile data enabled", Toast.LENGTH_LONG).show();

        }
        else
        {



            Toast.makeText(context, status, Toast.LENGTH_LONG).show();
        }

        //Crouton.make(context,status, Style.INFO).show();
    }
}