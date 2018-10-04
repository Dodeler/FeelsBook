package cdodic.feelsbook;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private FeelingList feelings_list;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView feeling, date;
        public MyViewHolder(View v) {
            super(v);
            feeling = (TextView) v.findViewById(R.id.feeling);
            date = (TextView) v.findViewById(R.id.date);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(FeelingList feelings) {
        feelings_list = feelings;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
//        TextView v = (TextView) LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.my_text_view, parent, false);
        //...
//        MyViewHolder vh = new MyViewHolder(v);
//        return vh;
        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Feeling feeling = feelings_list.get(position);
        holder.feeling.setText(feeling.getFeeling_type());
        holder.date.setText(feeling.getTimestamp().toString());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (feelings_list == null){
            return 0;
        }else{
            return feelings_list.size();
        }

    }
}