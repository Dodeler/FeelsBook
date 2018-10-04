package cdodic.feelsbook;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    protected FeelingList feelings = new FeelingList();
    private String[] feeling_type_strings = new String[]{"Love", "Joy", "Fear", "Anger", "Hope", "Sad"};
    protected String filepath;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        //https://stackoverflow.com/questions/4118751/how-do-i-serialize-an-object-and-save-it-to-a-file-in-android{
        //https://stackoverflow.com/questions/24029726/read-a-file-if-it-doesnt-exist-then-create{
        this.filepath = getApplicationContext().getFilesDir().toString() + "feelings.sav";
        File file = new File(filepath);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //}

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filepath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (fis == null){

        }
        else {
            try {
                if (fis.available() >= 0){

                }
                else{
                    ObjectInputStream is = null;
                    try {
                        is = new ObjectInputStream(fis);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
//                        Serializable cereal =
                        feelings = (FeelingList) is.readObject();
//                        feelings = (FeelingList) (Serializable) is.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void sendFeel(View view){
        EditText editText = (EditText) findViewById(R.id.editText2);
        String comment = editText.getText().toString();
        Button b = (Button) view;
        String feeling_type = b.getText().toString();
        Log.d("DEBUG ------------", feeling_type);
        Feeling new_feeling = new Feeling(new Date(), feeling_type, comment);
        feelings.add(new_feeling);
//        if (feeling_type.equals("love")){
//            new_feeling = new Feeling(new Date(), comment);
//            feelings.add(new_feeling);
//        }
    }
    public void gotoHistory(View view){

        Bundle b = new Bundle();
        b.putParcelable("feelings", this.feelings);
        Intent intent = new Intent(this, History.class);
        intent.putExtra("bundle", b);
        Log.d("DEBUG", "HERE WE GOOOOOOOOOOOOOOOOOOOOOOOOO");
        for(int i=0;i<feelings.size();i++){
            Log.d("DEBUG",feelings.get(i).getFeeling_type());
        }

        startActivity(intent);
    }
    public FeelingList getFeelings(){
        return feelings;
    }
    public Integer getFeelingCount(String feeling){
        Integer feeling_count = 0;
        for(int i=0; i<feelings.size(); i++){
            if(feelings.get(i).getFeeling_type().equals(feeling)){
                feeling_count += 1;
            }
        }
        return feeling_count;
    }
    public HashMap<String, Integer> getAllFeelingCounts(){
        HashMap<String, Integer> feeling_counts = new HashMap<>();
        for(String s: feeling_type_strings){
            feeling_counts.put(s, getFeelingCount(s));
        }
        return feeling_counts;
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        //https://stackoverflow.com/questions/4118751/how-do-i-serialize-an-object-and-save-it-to-a-file-in-android{
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filepath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectOutputStream os = null;
        try {
            os = new ObjectOutputStream(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            os.writeObject(this.feelings);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //}
    }
}
