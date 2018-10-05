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

import java.io.EOFException;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume(){
        super.onResume();
        //https://stackoverflow.com/questions/4118751/how-do-i-serialize-an-object-and-save-it-to-a-file-in-android{
        //https://stackoverflow.com/questions/24029726/read-a-file-if-it-doesnt-exist-then-create{
        this.filepath = getApplicationContext().getFilesDir().toString() + "/feelings.sav";
        feelings = FeelingList.readFeelings(filepath);
        //}
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    //Adds feeling to feeling list
    public void sendFeel(View view){
        EditText editText = (EditText) findViewById(R.id.editText2);
        String comment = editText.getText().toString();
        editText.setText("");
        Button b = (Button) view;
        String feeling_type = b.getText().toString();
        Feeling new_feeling = new Feeling(new Date(), feeling_type, comment);
        feelings.add(new_feeling);
    }

    //Handles the history button - creates new activity 'History'
    public void gotoHistory(View view){
        //Bundle feelings list and pass to next activity
        Bundle b = new Bundle();
        b.putParcelable("feelings", this.feelings);
        Intent intent = new Intent(this, History.class);
        intent.putExtra("bundle", b);

        startActivity(intent);
    }

    //Handles the statistics button
    public void gotoStatistics(View view){
        //bundle feelings list and go to statistics activity
        Bundle b = new Bundle();
        b.putParcelable("feelings", this.feelings);
        Intent intent = new Intent(this, Statistics.class);
        intent.putExtra("bundle", b);
        startActivity(intent);
    }

//    public FeelingList getFeelings(){
//        return feelings;
//    }

    public HashMap<String, Integer> getAllFeelingCounts(){
        HashMap<String, Integer> feeling_counts = new HashMap<>();
        for(String s: feeling_type_strings){
            feeling_counts.put(s, feelings.getFeelingCount(s));
        }
        return feeling_counts;
    }

    @Override
    protected void onPause(){
        super.onPause();
        FeelingList.writeFeelings(feelings, filepath);
    }

    @Override
    protected void onDestroy(){
        //https://stackoverflow.com/questions/4118751/how-do-i-serialize-an-object-and-save-it-to-a-file-in-android{
        //}
        super.onDestroy();
    }
}
