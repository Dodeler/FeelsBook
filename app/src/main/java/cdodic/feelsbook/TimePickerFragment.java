package cdodic.feelsbook;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

//https://developer.android.com/guide/topics/ui/controls/pickers
public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        EditFeeling activity = (EditFeeling) getActivity();
        Date d = activity.feeling.getTimestamp();

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, d.getHours(), d.getMinutes(),
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
//        String time = Integer.toString(hourOfDay) + ":" + Integer.toString(minute) + ":00";
        EditFeeling activity = (EditFeeling) getActivity();
        activity.setUserTime(hourOfDay, minute);
    }
}