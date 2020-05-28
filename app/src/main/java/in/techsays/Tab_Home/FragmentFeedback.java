package in.techsays.Tab_Home;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import in.techsays.R;



public class FragmentFeedback extends Fragment {

    private TextInputEditText textInputEditText, about;
    private ImageView imageView;


    public FragmentFeedback() {

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View feedback = inflater.inflate(R.layout.feedback, container, false);
        textInputEditText = feedback.findViewById(R.id.feedback_text);
        about = feedback.findViewById(R.id.feedback_about);
        imageView = feedback.findViewById(R.id.feedback_image);

//        TextView textView = (Home) Objects.requireNonNull(getActivity()).getTextView();
//        textView.setText("Feedback");
//        setHasOptionsMenu(true);
//        AppBarLayout appBarLayout;
//        appBarLayout = ((Home) Objects.requireNonNull(getActivity())).getElevations();
//        appBarLayout.setElevation(0.0f);
        return feedback;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        Objects.requireNonNull(getActivity()).getMenuInflater().inflate(R.menu.menu_send, menu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            menu.getItem(0).getIcon().setTint(ContextCompat.getColor(getActivity(), R.color.materialBlue));
        }
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_send:
                String feed = textInputEditText.getEditableText().toString();
                String type = about.getEditableText().toString();
                if (feed.isEmpty()) {
                    textInputEditText.setError("Please fill the field");
                } else if (type.isEmpty()) {
                    about.setError("Please fill the field");
                } else {
                    Intent send = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto", "uivision.contact@gmail.com", ""));
                    send.putExtra(Intent.EXTRA_SUBJECT, "Feedback about " + type);
                    send.putExtra(Intent.EXTRA_TEXT, feed);
                    startActivity(Intent.createChooser(send, "Choose an Email Client "));
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
