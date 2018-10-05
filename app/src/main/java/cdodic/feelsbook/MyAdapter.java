package cdodic.feelsbook;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

//Recycler view adapter for populating the history activity (dynamically) with saved feelings
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private FeelingList feelings_list;

    //View holder for adapter - stores displayed attribute views
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView feeling, date;
        public MyViewHolder(View v) {
            super(v);
            feeling = (TextView) v.findViewById(R.id.feeling);
            date = (TextView) v.findViewById(R.id.date);
        }
    }

    public MyAdapter(FeelingList feelings) {
        feelings_list = feelings;
    }

    // override methods for adapter

    //creates view holder
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view,
                                                                parent,
                                                    false);
        return new MyViewHolder(v);
    }

    //
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Feeling feeling = feelings_list.get(position);
        holder.feeling.setText(feeling.getFeeling_type());
        holder.date.setText(feeling.getTimestamp().toString());

    }

    @Override
    public int getItemCount() {
        if (feelings_list == null){
            return 0;
        }else{
            return feelings_list.size();
        }

    }
}