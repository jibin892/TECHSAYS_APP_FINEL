package in.techsays.Network_Cheking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import in.techsays.Course_details.Android_details;
import in.techsays.Login_and_Register.Login;
import in.techsays.Tab_Home.Home;

public class MyReceiver_Splash extends BroadcastReceiver {
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
            final Intent log12 = new Intent(context, Login.class);
            context.startActivity(log12);

        }
        else
        {




        }

    }
}