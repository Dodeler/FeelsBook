package cdodic.feelsbook;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Date;

// Information was gathered from android about how to implement pickers
// //https://developer.android.com/guide/topics/ui/controls/pickers
public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the date of the feeling being edited for default value
        EditFeeling activity = (EditFeeling) getActivity();
        Date d = activity.feeling.getTimestamp();

        // create time picker dialog
        return new TimePickerDialog(getActivity(), this, d.getHours(), d.getMinutes(),
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // pass time selected back to calling activity
        EditFeeling activity = (EditFeeling) getActivity();
        activity.setUserTime(hourOfDay, minute);
    }
}