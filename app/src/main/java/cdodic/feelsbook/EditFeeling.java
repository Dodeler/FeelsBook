package cdodic.feelsbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Date;

// Usage information gathered from android
// https://developer.android.com/reference/android/widget/AdapterView.OnItemSelectedListener
public class EditFeeling extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    protected Feeling feeling;
    private Button time_button;
    private Button date_button;

    public void onItemSelected(AdapterView<?> parent, View v, int pos, long id){
        String new_feeling_type = parent.getItemAtPosition(pos).toString();
        feeling.setFeelingType(new_feeling_type);
    }

    //saves the results of an edit by returning the feeling to the previous activity
    public void saveButton(View v){
        //collect comment
        EditText comment = findViewById(R.id.edit_comment);
        //update comment box
        feeling.setComment(comment.getText().toString());
        //bundle and return feeling to calling activity
        Bundle b = new Bundle();
        b.putParcelable("feeling", feeling);
        Intent intent = new Intent();
        intent.putExtra("bundle", b);
        setResult(123, intent);
        finish();
    }

    //dummy method (do nothing when nothing selected)
    public void onNothingSelected(AdapterView<?> parent){

    }

    //displays date picker fragment
    public void showDatePicker(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "date_picker");
    }

    //displays time picker fragment
    public void showTimePicker(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "time_picker");
    }

    // tell previous activity to remove the feeling being edited from the feelings list
    public void removeFeeling(View v){
        setResult(44);
        finish();
    }

    //sets the time value of feeling after selection (from time picker)
    protected void setUserTime(int h, int m){
        feeling.setTime(h, m);
        String time_pattern = "HH:mm:ss";

        SimpleDateFormat sdf_time = new SimpleDateFormat(time_pattern);
        time_button.setText(sdf_time.format(feeling.getTimestamp()));
    }



    //sets the date value of feeling after selection (from date picker)
    protected void setUserDate(int year, int month, int day){
        feeling.setDate(year,month,day);
        String date_pattern = "yyyy-MM-dd";
        SimpleDateFormat sdf_date = new SimpleDateFormat(date_pattern);
        date_button.setText(sdf_date.format(feeling.getTimestamp()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_feeling);

        //collect bundled feeling
        Bundle b = getIntent().getBundleExtra("bundle");
        Class cls = null;
        try {
            cls = Class.forName("cdodic.feelsbook.Feeling");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        b.setClassLoader(cls.getClassLoader());
        feeling = b.getParcelable("feeling");

        //Determine location in list and store
        String[] feeling_array = getResources().getStringArray(R.array.feelings_array);
        int id = 0;
        for(int i=0;i<feeling_array.length;i++){
            if(feeling_array[i].equals(feeling.getFeeling_type())){
                id = i;
            }
        }
        //Populate spinner box with feelings and set current value to editing feelings type
        Spinner spinner = findViewById(R.id.edit_feeling);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.feelings_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(id);
        spinner.setOnItemSelectedListener(this);

        //collected time/date buttons
        time_button = findViewById(R.id.edit_time);
        date_button = findViewById(R.id.edit_date);
        //fill buttons text with feelings time/date value
        Date d = feeling.getTimestamp();
        String time_pattern = "HH:mm:ss";
        String date_pattern = "yyyy-MM-dd";
        SimpleDateFormat sdf_time = new SimpleDateFormat(time_pattern);
        SimpleDateFormat sdf_date = new SimpleDateFormat(date_pattern);

        time_button.setText(sdf_time.format(d));
        date_button.setText(sdf_date.format(d));

        //fill comment box with feeling's saved comment
        EditText comment = findViewById(R.id.edit_comment);
        comment.setText(feeling.getComment());
    }

}
