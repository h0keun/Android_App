package com.growth.graphh;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {
    private DatePickerDialog.OnDateSetListener dateSetListener;
    DatePickerDialog myDatePicker;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        dateSetListener = (DatePickerDialog.OnDateSetListener)getTargetFragment();
        myDatePicker = new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
        myDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        // 오늘날짜를 최대치로 설정해서 이후 날짜 클릭 못하는 dialog
        
        return myDatePicker;
    }
}
