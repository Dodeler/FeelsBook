package cdodic.feelsbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import java.util.List;

public class History extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FeelingList feelings;
    private Feeling sel_feeling;
    private Intent ret_data;
    private Boolean edited;
    private int sel_position;
    private long button_click_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history); // https://developer.android.com/guide/topics/ui/layout/recyclerview
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        Intent intent = getIntent();
        // specify an adapter (see also next example)
        Bundle b = getIntent().getBundleExtra("bundle");
        Class cls = null;
        try {
            cls = Class.forName("cdodic.feelsbook.Feeling");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        b.setClassLoader(cls.getClassLoader());
        feelings = b.getParcelable("feelings");
        edited = false;
    }
    @Override
    protected void onActivityResult(int reqc, int resc, Intent data){
//        super.onActivityResult(reqc, resc, data);
        Log.d("DEBUG =-= ", "HERE! resc: "+Integer.toString(resc));
        if(resc == 123){
            ret_data = data;
            edited = true;
        }
        else if (resc == 44){
            ret_data = null;
            edited = true;
        }


//
//        Bundle listb = new Bundle();
//        listb.putParcelable("feelings", this.feelings);
//        Intent intent = new Intent();
//        intent.putExtra("bundle", listb);
//
//        setResult(RESULT_OK, intent);
    }
    protected void launch_edit(Intent intent){
        startActivityForResult(intent, 0);
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(edited){
            Log.d("DEBUG ----", "HERE222!");
            if (ret_data == null){
                feelings.remove(sel_position);
            }
            else {
                Bundle b = ret_data.getBundleExtra("bundle");
                Class cls = null;
                try {
                    cls = Class.forName("cdodic.feelsbook.Feeling");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                b.setClassLoader(cls.getClassLoader());
                sel_feeling = b.getParcelable("feeling");
                feelings.set(sel_position, sel_feeling);
                Log.d("DEBUG----", sel_feeling.getFeeling_type());
            }
            FeelingList.writeFeelings(feelings, getApplicationContext().getFilesDir().toString() + "/feelings.sav");
            edited = false;
        }

        mRecyclerView = findViewById(R.id.recycler);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        feelings.sort();
        mAdapter = new MyAdapter(feelings);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
//                Button button = (Button) view;
//                button.setEnabled(false);
                //https://stackoverflow.com/questions/31868874/fast-taps-clicks-on-recyclerview-opens-multiple-fragments
                long now = System.currentTimeMillis();
                if (now - button_click_time < 300){
                    return;
                }
                button_click_time = now;
                sel_position = position;
                sel_feeling = feelings.get(position);
                Bundle b = new Bundle();
                b.putParcelable("feeling", sel_feeling);
                Intent intent = new Intent(getApplicationContext(), EditFeeling.class);
                intent.putExtra("bundle", b);
                launch_edit(intent);

//                Toast.makeText(getApplicationContext(), movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        //}

    }




}

