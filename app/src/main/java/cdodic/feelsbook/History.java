package cdodic.feelsbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
// recycler view is a main component of this activity and its usage was derived from:
// // https://developer.android.com/guide/topics/ui/layout/recyclerview
public class History extends AppCompatActivity {
    private RecyclerView recycler_view;
    private RecyclerView.Adapter feeling_adapter;
    private RecyclerView.LayoutManager layout_manager;
    private FeelingList feelings;
    private Feeling sel_feeling;
    private Intent ret_data;
    private Boolean edited;
    private int sel_position;

    //Helper method for launching edit activity
    protected void gotoEdit(Intent intent){
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        //Collected bundled feelings list
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

    //Edit activity is created expecting a result - this method accepts the result with result code
    @Override
    protected void onActivityResult(int reqc, int resc, Intent data){
        // feeling has been modified (save button selected)
        if(resc == 123){
            ret_data = data;
            edited = true;
        }
        // feelings was deleted (delete button selected)
        else if (resc == 44){
            ret_data = null;
            edited = true;
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        //flag to check if feeling was edited (if coming from editfeeling activity)
        if(edited){
            // if deleted
            if (ret_data == null){
                feelings.remove(sel_position);
            }
            //if modified
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
            }
            //update feelinglist saved in file
            FeelingList.writeFeelings(feelings, getApplicationContext().getFilesDir().toString() + "/feelings.sav");
            edited = false;
        }

        //sets up and fills recycler view with feelings based on format specified by adapter
        recycler_view = findViewById(R.id.recycler);
        recycler_view.setHasFixedSize(true);
        layout_manager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(layout_manager);

        //sort feelings (on date) before populating recycler view
        feelings.sort();
        feeling_adapter = new FeelingAdapter(feelings);
        recycler_view.setAdapter(feeling_adapter);

        recycler_view.addOnItemTouchListener(createListener(recycler_view));

        //}
    }
    // Helper method to create listener (reduce clutter)
    private RecyclerTouchListener createListener(View v){
        return new RecyclerTouchListener(getApplicationContext(), (RecyclerView) v, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                sel_position = position;
                sel_feeling = feelings.get(position);
                Bundle b = new Bundle();
                b.putParcelable("feeling", sel_feeling);
                Intent intent = new Intent(getApplicationContext(), EditFeeling.class);
                intent.putExtra("bundle", b);
                gotoEdit(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        });
    }
}

