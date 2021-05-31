package com.growth.graphh.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.growth.graphh.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    private LinearLayout remember_edit;
    private String savedRememberText, filename, str_plan, str_remember = null;
    private Button cha_Btn, del_Btn, save_Btn;
    private ImageButton remember_back, remember_save;
    private TextView diaryTextView, showTextFiled, hideTextFiled, remember_text;
    private EditText contextEditText, remember_editText;

    @SuppressLint("DefaultLocale")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        InputMethodManager mInputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        //버튼클릭시 키보드 내리기위함

        CalendarView calendarView = v.findViewById(R.id.calendarView);
        diaryTextView = v.findViewById(R.id.diaryTextView);
        save_Btn = v.findViewById(R.id.save_Btn);
        del_Btn = v.findViewById(R.id.del_Btn);
        cha_Btn = v.findViewById(R.id.cha_Btn);
        showTextFiled = v.findViewById(R.id.showTextFiled);
        contextEditText = v.findViewById(R.id.contextEditText);
        hideTextFiled = v.findViewById(R.id.hideTextFiled);
        remember_text = v.findViewById(R.id.remember_text);
        remember_back = v.findViewById(R.id.remember_back);
        remember_editText = v.findViewById(R.id.remember_editText);
        remember_edit = v.findViewById(R.id.remember_edit);
        remember_save = v.findViewById(R.id.remember_save);
        LinearLayout remember = v.findViewById(R.id.remember);

        //명심할 한 줄 작성하기 위해 클릭시 입력창 나타나게함
        remember.setOnClickListener(view -> {
            remember_edit.setVisibility(View.VISIBLE);
            remember_editText.setText(str_remember);
            remember_text.setVisibility(View.INVISIBLE);
            remember_back.setVisibility(View.VISIBLE);
            remember_save.setVisibility(View.VISIBLE);
        });
        //명심할 한 줄 작성하고 저장 버튼
        remember_save.setOnClickListener(view -> {
            mInputMethodManager.hideSoftInputFromWindow(remember_editText.getWindowToken(), 0);
            saveMemory(savedRememberText);
            remember_back.setVisibility(View.GONE);
            remember_save.setVisibility(View.GONE);
            remember_edit.setVisibility(View.INVISIBLE);
            remember_text.setVisibility(View.VISIBLE);
            str_remember = remember_editText.getText().toString();
            remember_text.setText(str_remember);
        });
        // 삭제버튼
        remember_back.setOnClickListener(view -> {
            mInputMethodManager.hideSoftInputFromWindow(remember_editText.getWindowToken(), 0);
            getActivity().deleteFile(savedRememberText);
            str_remember = null;
            remember_editText.setText("");
            remember_text.setText("나의 다짐 작성하기");
            remember_text.setVisibility(View.VISIBLE);
            remember_back.setVisibility(View.GONE);
            remember_save.setVisibility(View.GONE);
            remember_edit.setVisibility(View.INVISIBLE);
        });

        FileInputStream fis_remember;//FileStream fis 변수
        savedRememberText = "명심할 것.txt";//저장할 파일 이름설정
        try {
            fis_remember = getActivity().openFileInput(savedRememberText);
            byte[] fileData = new byte[fis_remember.available()];
            //noinspection ResultOfMethodCallIgnored
            fis_remember.read(fileData);
            fis_remember.close();

            str_remember = new String(fileData);
            remember_text.setText(str_remember); //명심할 것

        } catch (Exception e) {
            e.printStackTrace();
        }

        //달력의 날짜를 클릭하였을 때 선택한 날짜를 감지
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            hideTextFiled.setVisibility(View.INVISIBLE);
            diaryTextView.setVisibility(View.VISIBLE);
            save_Btn.setVisibility(View.VISIBLE);
            showTextFiled.setVisibility(View.INVISIBLE);
            contextEditText.setVisibility(View.VISIBLE);
            cha_Btn.setVisibility(View.INVISIBLE);
            del_Btn.setVisibility(View.INVISIBLE);
            diaryTextView.setText(String.format("%d년 %d월 %d일", year, month + 1, dayOfMonth));
            contextEditText.setText("");
            checkDay(year, month, dayOfMonth);
        });

        // 선택한 날짜를 파일이름으로 생성하고 저장하는 작업
        save_Btn.setOnClickListener(view -> {
            mInputMethodManager.hideSoftInputFromWindow(contextEditText.getWindowToken(), 0);
            saveDiary(filename);
            hideTextFiled.setVisibility(View.INVISIBLE);
            str_plan = contextEditText.getText().toString();
            showTextFiled.setText(str_plan);
            save_Btn.setVisibility(View.INVISIBLE);
            cha_Btn.setVisibility(View.VISIBLE);
            del_Btn.setVisibility(View.VISIBLE);
            contextEditText.setVisibility(View.INVISIBLE);
            showTextFiled.setVisibility(View.VISIBLE);
        });
        return v;
    }

    public void checkDay(int cYear, int cMonth, int cDay) {//선택한 날짜를 파일명으로 생성하기 위함
        filename = "" + cYear + "년 " + (cMonth + 1) + "월 " + cDay + "일.txt";//저장할 파일 이름설정

        FileInputStream fis;

        try {
            fis = getActivity().openFileInput(filename);

            byte[] fileData = new byte[fis.available()];
            //noinspection ResultOfMethodCallIgnored
            fis.read(fileData);
            fis.close();

            str_plan = new String(fileData);

            contextEditText.setVisibility(View.INVISIBLE);
            showTextFiled.setVisibility(View.VISIBLE);
            hideTextFiled.setVisibility(View.INVISIBLE);
            showTextFiled.setText(str_plan); //해당날짜에 작성한 일정

            save_Btn.setVisibility(View.INVISIBLE);
            cha_Btn.setVisibility(View.VISIBLE);
            del_Btn.setVisibility(View.VISIBLE);

            //수정버튼 눌렀을 때 편집이 가능하도록 함
            cha_Btn.setOnClickListener(view -> {
                contextEditText.setVisibility(View.VISIBLE);
                showTextFiled.setVisibility(View.INVISIBLE);
                contextEditText.setText(str_plan);
                save_Btn.setVisibility(View.VISIBLE);
                cha_Btn.setVisibility(View.INVISIBLE);
                del_Btn.setVisibility(View.INVISIBLE);
                showTextFiled.setText(contextEditText.getText());
                hideTextFiled.setVisibility(View.INVISIBLE);
            });
            //삭제버튼 눌렀을 때 삭제가 가능하도록 함
            del_Btn.setOnClickListener(view -> {
                boolean isDeleted = getActivity().deleteFile(filename);
                if (isDeleted) {
                    showTextFiled.setVisibility(View.INVISIBLE);
                    contextEditText.setText("");
                    contextEditText.setVisibility(View.VISIBLE);
                    save_Btn.setVisibility(View.VISIBLE);
                    cha_Btn.setVisibility(View.INVISIBLE);
                    del_Btn.setVisibility(View.INVISIBLE);
                    hideTextFiled.setVisibility(View.INVISIBLE);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("WrongConstant")
    public void saveDiary(String readDay) {
        //일정을 작성하고 저장할 수 있도록 write한다.
        FileOutputStream fos;
        try {
            fos = getActivity().openFileOutput(readDay, MODE_PRIVATE);
            String content = contextEditText.getText().toString();
            fos.write((content).getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("WrongConstant")
    public void saveMemory(String memory) {
        //명심할 한줄을 작성하고 저장할 수 있도록 write한다.
        FileOutputStream fos5;
        try {
            fos5 = getActivity().openFileOutput(memory, MODE_PRIVATE);
            String content = remember_editText.getText().toString();
            fos5.write((content).getBytes());
            fos5.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}