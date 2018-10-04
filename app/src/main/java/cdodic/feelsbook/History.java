package cdodic.feelsbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toolbar;

import java.util.List;

public class History extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history); // https://developer.android.com/guide/topics/ui/layout/recyclerview
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        mRecyclerView = findViewById(R.id.recycler);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Intent intent = getIntent();
        // specify an adapter (see also next example)
        Bundle b = getIntent().getBundleExtra("bundle");
        Class cls = null;
        try {
            cls = Class.forName("cdodic.feelsbook.Feeling");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        b.setClassLoader(cls.getClassLoader());
        FeelingList feelings = b.getParcelable("feelings");
        mAdapter = new MyAdapter(feelings);
        mRecyclerView.setAdapter(mAdapter);
        //}

    }




}

