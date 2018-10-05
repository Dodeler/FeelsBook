package cdodic.feelsbook;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//Recycler view adapter for populating the history activity (dynamically) with saved feelings
// information on how to construct the adapter was gathered from:
//https://www.androidhive.info/2016/01/android-working-with-recycler-view/
// as well as from android
public class FeelingAdapter extends RecyclerView.Adapter<FeelingAdapter.MyViewHolder> {
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

    public FeelingAdapter(FeelingList feelings) {
        feelings_list = feelings;
    }

    // override methods for adapter

    //creates view holder
    @Override
    public FeelingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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