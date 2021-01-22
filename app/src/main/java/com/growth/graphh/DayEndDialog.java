package com.growth.graphh;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;

public class DayEndDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("\n" + "오늘이 지나면 더이상 수정이 불가합니다")
                .setTitle("오늘하루도 고생하셨어요");

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //그냥 공지용이이 때문에 따로 이벤트처리는 안한다.
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        //그냥 공지용이이 때문에 따로 이벤트처리는 안한다.
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}