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

    LinearLayout remember,remember_edit;
    public String remembertxt, fname, str, str5=null;
    public CalendarView calendarView;
    public Button cha_Btn,del_Btn,save_Btn;
    public ImageButton remember_back,remember_save;
    public TextView diaryTextView,textView2,textView4,text_set;
    public EditText contextEditText,remember_content;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        InputMethodManager mInputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        //버튼클릭시 키보드 내리기위함

        calendarView=v.findViewById(R.id.calendarView);
        diaryTextView=v.findViewById(R.id.diaryTextView);
        save_Btn=v.findViewById(R.id.save_Btn);
        del_Btn=v.findViewById(R.id.del_Btn);
        cha_Btn=v.findViewById(R.id.cha_Btn);
        textView2=v.findViewById(R.id.textView2);
        contextEditText=v.findViewById(R.id.contextEditText);
        textView4=v.findViewById(R.id.textView4);
        text_set=v.findViewById(R.id.text_set);
        remember_back=v.findViewById(R.id.remember_back);
        remember_content=v.findViewById(R.id.remember_content);
        remember_edit=v.findViewById(R.id.remember_edit);
        remember_save=v.findViewById(R.id.remember_save);
        remember=v.findViewById(R.id.remember);

        remember.setOnClickListener(new View.OnClickListener() {
            //명심할 한 줄 작성하기 위해 클릭시 입력창 나타나게함
            @Override
            public void onClick(View view) {
                remember_edit.setVisibility(View.VISIBLE);
                remember_content.setText(str5);
                text_set.setVisibility(View.INVISIBLE);
                remember_back.setVisibility(View.VISIBLE);
                remember_save.setVisibility(View.VISIBLE);
                }
        });
        remember_save.setOnClickListener(new View.OnClickListener(){
            //명심할 한 줄 작성하고 저장 버튼
            @Override
            public void onClick(View view){
                mInputMethodManager.hideSoftInputFromWindow(remember_content.getWindowToken(), 0);
                saveMemory(remembertxt);
                remember_back.setVisibility(View.GONE);
                remember_save.setVisibility(View.GONE);
                remember_edit.setVisibility(View.INVISIBLE);
                text_set.setVisibility(View.VISIBLE);
                str5=remember_content.getText().toString();
                text_set.setText(str5);
            }
        });
        remember_back.setOnClickListener(new View.OnClickListener(){
            // 삭제버튼
            @Override
            public void onClick(View view){
                mInputMethodManager.hideSoftInputFromWindow(remember_content.getWindowToken(), 0);
                getActivity().deleteFile(remembertxt);
                str5=null;
                remember_content.setText("");
                text_set.setText("나의 다짐 작성하기(클릭해주세요)");
                text_set.setVisibility(View.VISIBLE);
                remember_back.setVisibility(View.GONE);
                remember_save.setVisibility(View.GONE);
                remember_edit.setVisibility(View.INVISIBLE);
            }
        });

        FileInputStream fis5=null;//FileStream fis 변수
        remembertxt = "명심할 것.txt";//저장할 파일 이름설정
        try{
            fis5=getActivity().openFileInput(remembertxt);
            byte[] fileData=new byte[fis5.available()];
            fis5.read(fileData);
            fis5.close();

            str5=new String(fileData);
            text_set.setText(str5); //명심할 것

        }catch (Exception e){
            e.printStackTrace();
        }

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            //달력의 날짜를 클릭하였을 때 선택한 날짜를 감지
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                textView4.setVisibility(View.INVISIBLE);
                diaryTextView.setVisibility(View.VISIBLE);
                save_Btn.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.INVISIBLE);
                contextEditText.setVisibility(View.VISIBLE);
                cha_Btn.setVisibility(View.INVISIBLE);
                del_Btn.setVisibility(View.INVISIBLE);
                diaryTextView.setText(String.format("%d년 %d월 %d일",year,month+1,dayOfMonth));
                contextEditText.setText("");
                checkDay(year,month,dayOfMonth);
            }
        });

        save_Btn.setOnClickListener(new View.OnClickListener() {
            // 선택한 날짜를 파일이름으로 생성하고 저장하는 작업
            @Override
            public void onClick(View view) {
                mInputMethodManager.hideSoftInputFromWindow(contextEditText.getWindowToken(), 0);
                saveDiary(fname);
                textView4.setVisibility(View.INVISIBLE);
                str=contextEditText.getText().toString();
                textView2.setText(str);
                save_Btn.setVisibility(View.INVISIBLE);
                cha_Btn.setVisibility(View.VISIBLE);
                del_Btn.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.INVISIBLE);
                textView2.setVisibility(View.VISIBLE);
            }
        });
        return v;
    }
    public void  checkDay(int cYear,int cMonth,int cDay){//선택한 날짜를 파일명으로 생성하기 위함
        fname=""+cYear+"년 "+(cMonth+1)+"월 "+cDay+"일.txt";//저장할 파일 이름설정

        FileInputStream fis=null;

        try{
            fis=getActivity().openFileInput(fname);

            byte[] fileData=new byte[fis.available()];
            fis.read(fileData);
            fis.close();

            str=new String(fileData);

            contextEditText.setVisibility(View.INVISIBLE);
            textView2.setVisibility(View.VISIBLE);
            textView4.setVisibility(View.INVISIBLE);
            textView2.setText(str); //해당날짜에 작성한 일정

            save_Btn.setVisibility(View.INVISIBLE);
            cha_Btn.setVisibility(View.VISIBLE);
            del_Btn.setVisibility(View.VISIBLE);

            cha_Btn.setOnClickListener(new View.OnClickListener() {
                //수정버튼 눌렀을 때 편집이 가능하도록 함
                @Override
                public void onClick(View view) {
                    contextEditText.setVisibility(View.VISIBLE);
                    textView2.setVisibility(View.INVISIBLE);
                    contextEditText.setText(str);
                    save_Btn.setVisibility(View.VISIBLE);
                    cha_Btn.setVisibility(View.INVISIBLE);
                    del_Btn.setVisibility(View.INVISIBLE);
                    textView2.setText(contextEditText.getText());
                    textView4.setVisibility(View.INVISIBLE);
                }
            });
            del_Btn.setOnClickListener(new View.OnClickListener() {
                //삭제버튼 눌렀을 때 삭제가 가능하도록 함
                @Override
                public void onClick(View view) {
                    boolean isDeleted = getActivity().deleteFile(fname);
                    if(isDeleted){
                        textView2.setVisibility(View.INVISIBLE);
                        contextEditText.setText("");
                        contextEditText.setVisibility(View.VISIBLE);
                        save_Btn.setVisibility(View.VISIBLE);
                        cha_Btn.setVisibility(View.INVISIBLE);
                        del_Btn.setVisibility(View.INVISIBLE);
                        textView4.setVisibility(View.INVISIBLE);
                    }}
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @SuppressLint("WrongConstant")
    public void saveDiary(String readDay){
        //일정을 작성하고 저장할 수 있도록 write한다.
        FileOutputStream fos=null;
        try{
            fos=getActivity().openFileOutput(readDay, MODE_PRIVATE);
            String content=contextEditText.getText().toString();
            fos.write((content).getBytes());
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @SuppressLint("WrongConstant")
    public void saveMemory(String memory){
        //명심할 한줄을 작성하고 저장할 수 있도록 write한다.
        FileOutputStream fos5=null;
        try{
            fos5=getActivity().openFileOutput(memory, MODE_PRIVATE);
            String content=remember_content.getText().toString();
            fos5.write((content).getBytes());
            fos5.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}