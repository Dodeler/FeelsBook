package cdodic.feelsbook;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        EditFeeling activity = (EditFeeling)getActivity();
        Date d = activity.feeling.getTimestamp();

        // Create a new instance of DatePickerDialog and return it
        Log.d("DATETIME ----", d.getYear() + " "+ d.getMonth() + " " + d.getDay());
        return new DatePickerDialog(getActivity(), this, d.getYear() + 1900, d.getMonth(), d.getDay());
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        EditFeeling activity = (EditFeeling) getActivity();
        activity.setUserDate(year - 1900,month,day);
    }
}