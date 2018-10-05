package cdodic.feelsbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    protected FeelingList feelings = new FeelingList();
    protected String filepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume(){
        super.onResume();
        // read feelings from file whenever we return to main activity
        this.filepath = getApplicationContext().getFilesDir().toString() + "/feelings.sav";
        feelings = FeelingList.readFeelings(filepath);
        //}
    }

    @Override
    protected void onPause(){
        super.onPause();
        // write feelings to file to prevent loss of content
        FeelingList.writeFeelings(feelings, filepath);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    //Adds feeling to feeling list (response to clicking a feeling button)
    // creates a timestamp for the feeling and collects the comment from the text box
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
}
