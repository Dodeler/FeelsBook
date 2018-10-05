package cdodic.feelsbook;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EditFeeling extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    protected Feeling feeling;
    private Button time_button;
    private Button date_button;
    public void onItemSelected(AdapterView<?> parent, View v, int pos, long id){
        Log.d("DEBUG -----", "selected: " + Integer.toString(pos));
        String new_feeling_type = parent.getItemAtPosition(pos).toString();
        feeling.setFeelingType(new_feeling_type);
        Log.d("DEBUG -----", "feeling type: "+feeling.getFeeling_type());



//        Feeling feeling = feelings.get(position);


    }
    public void saveButton(View v){
        EditText comment = findViewById(R.id.edit_comment);
        feeling.setComment(comment.getText().toString());
        Bundle b = new Bundle();
        b.putParcelable("feeling", feeling);
        Intent intent = new Intent();
        intent.putExtra("bundle", b);
        setResult(123, intent);
        finish();
    }
    public void onNothingSelected(AdapterView<?> parent){

    }
    public void showDatePicker(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    public void showTimePicker(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
    protected void setUserTime(int h, int m){
        feeling.setTime(h, m);
        String time_pattern = "HH:mm:ss";

        SimpleDateFormat sdf_time = new SimpleDateFormat(time_pattern);
        time_button.setText(sdf_time.format(feeling.getTimestamp()));
    }
    public void removeFeeling(View v){
        setResult(44);
        finish();
    }
    protected void setUserDate(int year, int month, int day){
        Log.d("TIMEDATE", year + " " + month + " " + day);
        feeling.setDate(year,month,day);
        String date_pattern = "yyyy-MM-dd";
        SimpleDateFormat sdf_date = new SimpleDateFormat(date_pattern);

        date_button.setText(sdf_date.format(feeling.getTimestamp()));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_feeling);


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
        feeling = b.getParcelable("feeling");

        String[] feeling_array = getResources().getStringArray(R.array.feelings_array);
        int id = 0;
        for(int i=0;i<feeling_array.length;i++){
            if(feeling_array[i].equals(feeling.getFeeling_type())){
                id = i;
            }
        }
        Spinner spinner = findViewById(R.id.edit_feeling);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.feelings_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(id);
        spinner.setOnItemSelectedListener(this);

        time_button = findViewById(R.id.edit_time);
        date_button = findViewById(R.id.edit_date);

        Date d = feeling.getTimestamp();
        String time_pattern = "HH:mm:ss";
        String date_pattern = "yyyy-MM-dd";
        SimpleDateFormat sdf_time = new SimpleDateFormat(time_pattern);
        SimpleDateFormat sdf_date = new SimpleDateFormat(date_pattern);


        time_button.setText(sdf_time.format(d));
        date_button.setText(sdf_date.format(d));

        EditText comment = findViewById(R.id.edit_comment);
        comment.setText(feeling.getComment());

//        SimpleDateFormat sdf = findViewById(R.id.edit_date)edit_date?;


    }

}
