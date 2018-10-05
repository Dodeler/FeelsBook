package cdodic.feelsbook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Statistics extends AppCompatActivity {
    private FeelingList feelings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        // Collect feelings list
        Bundle b = getIntent().getBundleExtra("bundle");
        Class cls = null;
        try {
            cls = Class.forName("cdodic.feelsbook.Feeling");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        b.setClassLoader(cls.getClassLoader());
        feelings = b.getParcelable("feelings");
    }
    @Override
    protected void onResume(){
        super.onResume();
        // Proof of concept for statistics activity
        // should use getAllFeelingCounts() and dynamically generate table
        TextView tv1 = findViewById(R.id.love_val);
        TextView tv2 = findViewById(R.id.joy_val);
        TextView tv3 = findViewById(R.id.surprise_val);
        TextView tv4 = findViewById(R.id.anger_val);
        TextView tv5 = findViewById(R.id.sadness_val);
        TextView tv6 = findViewById(R.id.fear_val);
        tv1.setText(feelings.getFeelingCount("Love").toString());
        tv2.setText(feelings.getFeelingCount("Joy").toString());
        tv3.setText(feelings.getFeelingCount("Surprise").toString());
        tv4.setText(feelings.getFeelingCount("Anger").toString());
        tv5.setText(feelings.getFeelingCount("Sadness").toString());
        tv6.setText(feelings.getFeelingCount("Fear").toString());
    }
}
