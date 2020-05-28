package in.techsays.Welocomshow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

import in.techsays.R;

public class WizAdapterOne extends PagerAdapter {

    private Context context;
    private List<WizModelOne> wizModelOneList;

    public WizAdapterOne(Context context, List<WizModelOne> wizModelOneList) {
        this.context = context;
        this.wizModelOneList = wizModelOneList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.welcomeslide, null);

        ImageView imageView = layoutScreen.findViewById(R.id.wizImage1);
      //  TextView headLines = layoutScreen.findViewById(R.id.wizText1);
      //  TextView descriptions = layoutScreen.findViewById(R.id.wizText2);
       // headLines.setText(wizModelOneList.get(position).getHeadLine());
       // descriptions.setText(wizModelOneList.get(position).getDescription());
        imageView.setImageResource(wizModelOneList.get(position).getWizImage());
        container.addView(layoutScreen);
        return layoutScreen;
    }

    @Override
    public int getCount() {
        return wizModelOneList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
