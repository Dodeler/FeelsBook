package cdodic.feelsbook;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Date;

// Information was gathered from android about how to implement pickers
// //https://developer.android.com/guide/topics/ui/controls/pickers
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the date of the feeling being edited for default value
        EditFeeling activity = (EditFeeling)getActivity();
        Date d = activity.feeling.getTimestamp();

        // Add 1900 to year to handle Date object vs. DatePickerDialog interpretation of years
        // launch new date picker dialog
        return new DatePickerDialog(getActivity(), this, d.getYear() + 1900, d.getMonth(), d.getDay());
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Subtract 1900 to year to handle Date object vs. DatePickerDialog interpretation of years
        // return the date selected to calling activity
        EditFeeling activity = (EditFeeling) getActivity();
        activity.setUserDate(year - 1900,month,day);
    }
}