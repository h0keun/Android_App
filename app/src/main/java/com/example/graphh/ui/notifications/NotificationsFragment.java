package com.example.graphh.ui.notifications;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.graphh.DayEndDialog;
import com.example.graphh.R;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import static android.content.Context.MODE_PRIVATE;


public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    String fname2=null;
    String str=null;
    String str6=null;
    String str7=null;
    String go=null;
    String ss;
    String remembertxt2;
    int count_click;
    int k_count;
    int k;
    float s;
    float s_num;
    float u;
    private RatingBar rating_bar,rating_bar_sub;
    TextView textView3,dateNow,rating_txt,text_set2,contextText2;
    EditText contextEditText2;
    Button cha_Btn2,sbtn;
    ImageButton day_end;
    public CalendarView calendarView2;
    ArrayList<Float> list = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View v = inflater.inflate(R.layout.fragment_notifications, container, false);
        /*final TextView textView = root.findViewById(R.id.text_notifications);*/
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                /*textView.setText(s);*/
            }
        });

        InputMethodManager mInputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        //버튼클릭시 키보드내리기

        day_end=v.findViewById(R.id.day_end); // 공지사항 dialog
        rating_bar_sub=v.findViewById(R.id.rating_bar_sub); // 저장된 별의 갯수 받아서 그림
        rating_bar=v.findViewById(R.id.rating_bar); // 저장하기위한 별의 갯수 그림
        rating_txt=v.findViewById(R.id.rating_txt); // 별의 갯수에 해당하는 텍스트 내용
        contextEditText2=v.findViewById(R.id.contextEditText2); // 나에게 남길 메시지(EditText)
        contextText2=v.findViewById(R.id.contextText2); // 나에게 남길 메시지(TextView)
        sbtn = (Button) v.findViewById(R.id.sbtn); // 작성완료 버튼
        cha_Btn2=v.findViewById(R.id.cha_Btn2); // 다시작성하기 버튼
        calendarView2=v.findViewById(R.id.calendarView2);
        textView3=v.findViewById(R.id.textView3); // 오늘계획한 일
        text_set2=v.findViewById(R.id.text_set2); // 명심할 한 줄
        dateNow = (TextView) v.findViewById(R.id.dateNow);// 오늘날짜 우측상단에 표시

        day_end=v.findViewById(R.id.day_end);
        day_end.setOnClickListener(new View.OnClickListener() {
            //공지내용에 해당하는 dialog띄우기
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DayEndDialog(); // creating DialogFragment which creates DatePickerDialog
                newFragment.setTargetFragment(NotificationsFragment.this,0);  // Passing this fragment DatePickerFragment.
                // As i figured out this is the best way to keep the reference to calling activity when using FRAGMENT.
                newFragment.show(getFragmentManager(), "dayend");
            }
        });

        rating_bar=v.findViewById(R.id.rating_bar);
        rating_bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating_bar.getRating() == 2.5) {
                    rating_txt.setText("어제와 다를 바 없는 오늘"); // 별의 갯수는 0부터 5까지 받기 때문에
                    s = rating - 2.5f; //그래프를 그리면 무조건 상향그래프라 -2.5 해줌으로써 -2.5부터 2.5의 범위를 갖도록 함
                }
                if(rating_bar.getRating() == 3 ) {
                    rating_txt.setText("어제보다 나아진 오늘");
                    s = rating - 2.5f;
                }
                if(rating_bar.getRating() == 2) {
                    rating_txt.setText("어제보다 못한 오늘");
                    s = rating - 2.5f;
                }
                if(rating_bar.getRating() == 0) {
                    rating_txt.setText("최악의 하루");
                    s = rating - 2.5f;
                }
                if(rating_bar.getRating() == 5) {
                    rating_txt.setText("최고의 하루");
                    s = rating - 2.5f;
                }
                if(rating_bar.getRating() == 4.5) {
                    rating_txt.setText("최고의 하루");
                    s = rating - 2.5f;
                }
                if(rating_bar.getRating() == 0.5) {
                    rating_txt.setText("최악의 하루");
                    s = rating - 2.5f;
                }
                if(rating_bar.getRating() == 4) {
                    rating_txt.setText("최고의 하루");
                    s = rating - 2.5f;
                }
                if(rating_bar.getRating() == 1) {
                    rating_txt.setText("최악의 하루");
                    s = rating - 2.5f;
                }
                if(rating_bar.getRating() == 3.5) {
                    rating_txt.setText("어제보다 나아진 오늘");
                    s = rating - 2.5f;

                }
                if(rating_bar.getRating() == 1.5) {
                    rating_txt.setText("어제보다 못한 오늘");
                    s = rating - 2.5f;
                }
            }
        });

        SharedPreferences pref_day = getActivity().getSharedPreferences("pref_day", MODE_PRIVATE);
        k_count = pref_day.getInt("key_day", 1);
        // d_day
        SharedPreferences pref = getActivity().getSharedPreferences("pref", MODE_PRIVATE);
        k = pref.getInt("key2", 1);
        // 아무런 작없 없이 d_day 증가하였을 때 그에 맞추어 그래프 그리기위해 필요
        SharedPreferences pref_num = getActivity().getSharedPreferences("pref_num", MODE_PRIVATE);
        s_num = pref_num.getFloat("key_num", 0);
        // 그린 별 갯수 저장
        SharedPreferences pref_c = getActivity().getSharedPreferences("pref_c", MODE_PRIVATE);
        count_click = pref_c.getInt("key_c", 0);
        // 저장버튼과 수정 버튼을 구분지어서 레이아웃을 표현하기 위함
        // 프래그먼트 생명주기 때문에 레이아웃이 의도대로 그려지지 않아서 부득이하게 설정함
        SharedPreferences pref2 = getActivity().getSharedPreferences("pref2", MODE_PRIVATE);
        u = pref2.getFloat("key3",0);
        // 별의 갯수를 축적하며 저장하여 그래프를 그리기 위함.
        SharedPreferences prefs = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        ss = prefs.getString("name", "");
        //u를 배열로 저장하기 위함, 그래프 그릴 때 배열값이 필요, 토큰을 이용해 배열값을 구분짓고 저장함
        StringTokenizer st = new StringTokenizer(ss, ",");
        while (st.hasMoreTokens()) {
            list.add(Float.parseFloat(st.nextToken()));
        }

        if(k_count > k){ // 하루가 지나서 d-day가 증가한 경우
            calendarView2=v.findViewById(R.id.calendarView2);
            textView3=v.findViewById(R.id.textView3);
            text_set2=v.findViewById(R.id.text_set2);
            dateNow = (TextView) v.findViewById(R.id.dateNow);
            rating_bar_sub=v.findViewById(R.id.rating_bar_sub);
            rating_bar=v.findViewById(R.id.rating_bar);
            rating_txt=v.findViewById(R.id.rating_txt);
            contextEditText2=v.findViewById(R.id.contextEditText2);
            contextText2=v.findViewById(R.id.contextText2);
            cha_Btn2=v.findViewById(R.id.cha_Btn2);
            day_end=v.findViewById(R.id.day_end);
            sbtn=v.findViewById(R.id.sbtn);

            contextEditText2.setVisibility(View.VISIBLE);
            contextText2.setVisibility(View.GONE);
            rating_bar.setVisibility(View.VISIBLE);
            rating_bar_sub.setVisibility(View.GONE);
            rating_txt.setVisibility(View.VISIBLE);
            sbtn.setVisibility(View.VISIBLE);
            cha_Btn2.setVisibility(View.INVISIBLE);
            day_end.setVisibility(View.INVISIBLE);

            list.add(0, u);
            ss = "";
            for (Float i : list) {
                ss += i + ",";
            }
            SharedPreferences.Editor editors = prefs.edit();
            editors.putString("name", ss);
            editors.commit(); // 배열에 u 값 추가로 저장 k++를통해 아무작없이 없었을 경우도 저장이된다.

            count_click =0;
            SharedPreferences.Editor editor_c = pref_c.edit();
            editor_c.putInt("key_c",count_click);
            editor_c.commit(); //저장버튼 눌렀을때 바뀌었던 레이아웃을 원상복구 해준다.

            k++;
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("key2",k);
            editor.commit();// d-day 증가한만큼 list.add해주어야하기 때문에 k++로 횟수를 맞춰주고 저장함
        }

        Log.d("LOG_TAG",(int)k+"k 값은??");
        Log.d("LOG_TAG",(int)k_count+"k_count 값은??");
        Log.d("LOG_TAG",ss+" ss 값은??");
        Log.d("LOG_TAG",list+" list 값은??");

        if(count_click >0){ //작성완료 버튼을 눌렀을 경우
            calendarView2=v.findViewById(R.id.calendarView2);
            textView3=v.findViewById(R.id.textView3);
            text_set2=v.findViewById(R.id.text_set2);
            dateNow = (TextView) v.findViewById(R.id.dateNow);
            rating_bar_sub=v.findViewById(R.id.rating_bar_sub);
            rating_bar=v.findViewById(R.id.rating_bar);
            rating_txt=v.findViewById(R.id.rating_txt);
            contextEditText2=v.findViewById(R.id.contextEditText2);
            contextText2=v.findViewById(R.id.contextText2);
            cha_Btn2=v.findViewById(R.id.cha_Btn2);
            day_end=v.findViewById(R.id.day_end);
            sbtn=v.findViewById(R.id.sbtn);

            sbtn.setVisibility(View.INVISIBLE);
            cha_Btn2.setVisibility(View.VISIBLE);
            day_end.setVisibility(View.VISIBLE);
            contextEditText2.setVisibility(View.GONE);
            contextText2.setVisibility(View.VISIBLE);
            rating_bar.setVisibility(View.INVISIBLE);
            rating_bar_sub.setVisibility(View.VISIBLE);

            rating_bar_sub.setRating(s_num+(float)2.5); //저장된 별의 갯수만큼 다시 그려줌
            if(rating_bar_sub.getRating() == 2.5) {
                rating_txt.setText("어제와 다를 바 없는 오늘");
            }
            if(rating_bar_sub.getRating() == 3 ) {
                rating_txt.setText("어제보다 나아진 오늘");
            }
            if(rating_bar_sub.getRating() == 2) {
                rating_txt.setText("어제보다 못한 오늘");
            }
            if(rating_bar_sub.getRating() == 0) {
                rating_txt.setText("최악의 하루");
            }
            if(rating_bar_sub.getRating() == 5) {
                rating_txt.setText("최고의 하루");
            }
            if(rating_bar_sub.getRating() == 4.5) {
                rating_txt.setText("최고의 하루");
            }
            if(rating_bar_sub.getRating() == 0.5) {
                rating_txt.setText("최악의 하루");
            }
            if(rating_bar_sub.getRating() == 4) {
                rating_txt.setText("최고의 하루");
            }
            if(rating_bar_sub.getRating() == 1) {
                rating_txt.setText("최악의 하루");
            }
            if(rating_bar_sub.getRating() == 3.5) {
                rating_txt.setText("어제보다 나아진 오늘");
            }
            if(rating_bar_sub.getRating() == 1.5) {
                rating_txt.setText("어제보다 못한 오늘");
            }

        }

        sbtn = (Button) v.findViewById(R.id.sbtn);
        sbtn.setOnClickListener(new View.OnClickListener() { //작성완료 버튼
            @Override
            public void onClick(View v) {
                mInputMethodManager.hideSoftInputFromWindow(contextEditText2.getWindowToken(), 0);
                contextEditText2.setVisibility(View.GONE);
                contextText2.setVisibility(View.VISIBLE);
                rating_bar.setVisibility(View.INVISIBLE);
                rating_bar_sub.setVisibility(View.VISIBLE);
                rating_txt.setVisibility(View.VISIBLE);
                sbtn.setVisibility(View.INVISIBLE);
                cha_Btn2.setVisibility(View.VISIBLE);
                day_end.setVisibility(View.VISIBLE);

                saveDiary2(fname2); //작성내용 저장하기위한 이름 설정

                s_num = s;
                SharedPreferences pref_num = getActivity().getSharedPreferences("pref_num",MODE_PRIVATE);
                SharedPreferences.Editor editor_num = pref_num.edit();
                editor_num.putFloat("key_num",s_num);
                editor_num.commit(); //별의 갯수를 저장
                rating_bar_sub.setRating(s_num+(float)2.5);

                u += s;
                SharedPreferences pref2 = getActivity().getSharedPreferences("pref2",MODE_PRIVATE);
                SharedPreferences.Editor editor2 = pref2.edit();
                editor2.putFloat("key3",u);
                editor2.commit();//증가한 별의 갯수만큼 축적해서 u에 저장

                count_click++;
                SharedPreferences pref_c =getActivity().getSharedPreferences("pref_c",MODE_PRIVATE);
                SharedPreferences.Editor editor_c = pref_c.edit();
                editor_c.putInt("key_c",count_click);
                editor_c.commit(); //작성완료버튼을 누른 상태를 저장

                str7=contextEditText2.getText().toString();
                contextText2.setText(str7);//나에게 남길 메시지
            }
        });

        cha_Btn2=v.findViewById(R.id.cha_Btn2);
        cha_Btn2.setOnClickListener(new View.OnClickListener() { //다시작성하기 버튼
            @Override
            public void onClick(View v) {
                contextEditText2.setVisibility(View.VISIBLE);
                contextEditText2.setText(str7);
                contextText2.setVisibility(View.GONE);
                rating_bar.setVisibility(View.VISIBLE);
                rating_bar_sub.setVisibility(View.GONE);
                rating_txt.setVisibility(View.VISIBLE);
                sbtn.setVisibility(View.VISIBLE);
                cha_Btn2.setVisibility(View.INVISIBLE);
                day_end.setVisibility(View.INVISIBLE);

                SharedPreferences pref_num = getActivity().getSharedPreferences("pref_num", MODE_PRIVATE);
                s_num = pref_num.getFloat("key_num",0);
                SharedPreferences pref2 = getActivity().getSharedPreferences("pref2", MODE_PRIVATE);
                u = pref2.getFloat("key3",0);

                u -= s_num;
                SharedPreferences.Editor editor2 = pref2.edit();
                editor2.putFloat("key3",u);
                editor2.commit(); // 이전에 추가하였던 값을 그대로 뺴주어서 초기상태로 돌림

                count_click =0;
                SharedPreferences pref_c =getActivity().getSharedPreferences("pref_c",MODE_PRIVATE);
                SharedPreferences.Editor editor_c = pref_c.edit();
                editor_c.putInt("key_c",count_click);
                editor_c.commit(); // 수정버튼 누른 상태를 저장
            }
        });

        Date today7 = new Date();
        SimpleDateFormat date5 = new SimpleDateFormat("yyyy년M월d일", Locale.getDefault());
        String toDay7 = date5.format(today7); // 나에게 남길 메시지 파일명 지정
        fname2=""+toDay7+".txt";

        FileInputStream fis7=null;//FileStream fis 변수

        try{
            fis7=getActivity().openFileInput(fname2);
            byte[] fileData=new byte[fis7.available()];
            fis7.read(fileData);
            fis7.close();

            str7=new String(fileData);
            contextText2.setText(str7); //나에게 남길 메시지

        }catch (Exception e){
            e.printStackTrace();
        }

        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy년 M월 d일",Locale.getDefault());
        String toDay = date.format(today);

        Date today2 = new Date();
        SimpleDateFormat date2 = new SimpleDateFormat("M월 d일",Locale.getDefault());
        String toDay2 = date2.format(today2);

        dateNow = (TextView) v.findViewById(R.id.dateNow);
        dateNow.setText(toDay2); //오늘의 날짜를 오른쪽 상단에 표시

        FileInputStream fis=null;//FileStream fis 변수
        go =""+toDay+".txt";//불러올 파일 이름설정(일정 탭에서 저장한 파일)
        try{
            fis=getActivity().openFileInput(go);
            byte[] fileData=new byte[fis.available()];
            fis.read(fileData);
            fis.close();

            str=new String(fileData);
            textView3.setText(str); //오늘 계획한 일

        }catch (Exception e){
            e.printStackTrace();
        }

        text_set2=v.findViewById(R.id.text_set2);//명심할 한 줄
        FileInputStream fis5=null;//FileStream fis 변수
        remembertxt2 = "명심할 것.txt";//저장된 파일 이름불러오기(일정탭에서 저장함)
        try{
            fis5=getActivity().openFileInput(remembertxt2);
            byte[] fileData=new byte[fis5.available()];
            fis5.read(fileData);
            fis5.close();

            str6=new String(fileData);
            text_set2.setText(str6); //명심할 한줄

        }catch (Exception e){
            e.printStackTrace();
        }

        return v;
    }

    @SuppressLint("WrongConstant")
    public void saveDiary2(String readDay2){

        //나에게 남길 메시지작성을 위해 write
        FileOutputStream fos=null;

        try{
            fos=getActivity().openFileOutput(readDay2, Context.MODE_PRIVATE);
            String content=contextEditText2.getText().toString();
            fos.write((content).getBytes());
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}