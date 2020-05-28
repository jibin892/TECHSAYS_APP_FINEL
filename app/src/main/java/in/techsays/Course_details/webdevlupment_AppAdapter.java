package in.techsays.Course_details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.techsays.R;

public class webdevlupment_AppAdapter extends RecyclerView.Adapter<webdevlupment_AppAdapter.ViewHolder> {

    private ArrayList<AppModel> appModelArrayList;
    private CodexRecyclerViewClickListener listener;

    public interface CodexRecyclerViewClickListener{
       void onClicked(int position, ViewHolder viewHolder);
    }

    public void setListener(CodexRecyclerViewClickListener listener) {
        this.listener = listener;
    }

    public webdevlupment_AppAdapter(ArrayList<AppModel> appModelArrayList, Context context) {
        this.appModelArrayList = appModelArrayList;
        Context context1 = context;
    }

    @NonNull
    @Override
    public webdevlupment_AppAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.webdevlupment_corses, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final webdevlupment_AppAdapter.ViewHolder holder, final int position) {

        holder.textView.setText(appModelArrayList.get(position).getHead());
//        holder.imageView.setImageResource(appModelArrayList.get(position).getRotation());
    }



    @Override
    public int getItemCount() {
        return appModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public CardView cardView;
        private ImageView imageView;
        ViewHolder viewHolder1 = this;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.app_text);
            cardView = itemView.findViewById(R.id.cv17);
          //  imageView = itemView.findViewById(R.id.support_rotation);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            listener.onClicked(pos, viewHolder1);
                        }
                    }
                }
            });

        }
    }
}
