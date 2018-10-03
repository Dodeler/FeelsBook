package cdodic.feelsbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    protected List<Feeling> feelings;
    private String[] feeling_type_strings = new String[]{"Love", "Joy", "Fear", "Anger", "Hope", "Sad"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("feelings.sav");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectInputStream is = null;
        try {
            is = new ObjectInputStream(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            feelings = (List<Feeling>) is.readObject();
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
        Feeling new_feeling;
        if (feeling_type.equals("love")){
            new_feeling = new Love(new Date(), comment);
            feelings.add(new_feeling);
        }


    }
    public List<Feeling> getFeelings(){
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
            fos = new FileOutputStream("feelings.sav");
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
            os.writeObject(this);
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
