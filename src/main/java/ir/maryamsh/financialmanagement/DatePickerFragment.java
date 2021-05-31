package ir.maryamsh.financialmanagement;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {
    Context context;
    public DatePickerFragment(Context context){
        this.context=context;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(context,R.style.TimePickerTheme, (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
    }
}
