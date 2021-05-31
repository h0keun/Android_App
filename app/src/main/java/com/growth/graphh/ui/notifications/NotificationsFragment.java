package com.growth.graphh.ui.notifications;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.growth.graphh.DayEndDialog;
import com.growth.graphh.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

import static android.content.Context.MODE_PRIVATE;

public class NotificationsFragment extends Fragment {

    private String messageFileName;
    private String str_leftMessage;
    private int count_click;
    private float rating_stars, rating_stars_num, rating_stars_sum;
    private RatingBar rating_bar, rating_bar_sub;
    private TextView rating_txt;
    private TextView leftMessage;
    private EditText leaveMessage;
    private Button saveButton, editButton;
    private ImageButton dialogButton;
    private final ArrayList<Float> list = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_notifications, container, false);

        InputMethodManager mInputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        //버튼클릭시 키보드내리기

        dialogButton = v.findViewById(R.id.dialogButton); // 공지사항 dialog
        rating_bar_sub = v.findViewById(R.id.rating_bar_sub); // 저장된 별의 갯수 받아서 그림
        rating_bar = v.findViewById(R.id.rating_bar); // 저장하기위한 별의 갯수 그림
        rating_txt = v.findViewById(R.id.rating_txt); // 별의 갯수에 해당하는 텍스트 내용
        leaveMessage = v.findViewById(R.id.leaveMessage); // 나에게 남길 메시지(EditText)
        leftMessage = v.findViewById(R.id.leftMessage); // 나에게 남길 메시지(TextView)
        saveButton = v.findViewById(R.id.saveButton); // 작성완료 버튼
        editButton = v.findViewById(R.id.editButton); // 다시작성하기 버튼
        TextView today_plan = v.findViewById(R.id.today_plan); // 오늘계획한 일
        TextView remembered_text = v.findViewById(R.id.remembered_text); // 명심할 한 줄
        TextView dateNow = v.findViewById(R.id.dateNow);// 오늘날짜 우측상단에 표시

        dialogButton = v.findViewById(R.id.dialogButton);
        dialogButton.setOnClickListener(view -> {
            DialogFragment newFragment = new DayEndDialog();
            newFragment.setTargetFragment(NotificationsFragment.this, 0);
            newFragment.show(getFragmentManager(), "dayend");
        });

        SimpleDateFormat sdf_date = new SimpleDateFormat("d", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +1);
        String toDate = sdf_date.format(calendar.getTime());
        int day_famous = Integer.parseInt(toDate);
        // 일자별로 다른 명언 불러들이기 위함

        AssetManager am = getResources().getAssets(); // Asset폴더에 있는 파일 읽기
        InputStream is;

        try {
            is = am.open("famous_saying");

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            for (int i = 1; i < day_famous; i++) {
                br.readLine();
            } // BufferedReader을 통해 해당날짜에 해당하는 줄 이외의 줄을 읽지 않음
            TextView famous_Saying = v.findViewById(R.id.famous_Saying);
            famous_Saying.setText(br.readLine()); //해당날짜에 해당하는 줄의 명언표시

            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        InputStream iss;
        try {
            iss = am.open("famous_saying_who");

            BufferedReader brr = new BufferedReader(new InputStreamReader(iss));
            for (int i = 1; i < day_famous; i++) {
                brr.readLine();
            } // BufferedReader을 통해 해당날짜에 해당하는 줄 이외의 줄을 읽지 않음
            TextView famous_Who = v.findViewById(R.id.famous_Who);
            famous_Who.setText(brr.readLine() + " "); //해당날짜에 해당하는 유명인표시, 이텔릭체라 글자조금 짤려서 공백추가

            iss.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ImageView famous_imgg = v.findViewById(R.id.famous_imgg);
        String famous = Integer.toString(day_famous);
        String famous_who = "@drawable/day_" + famous + "_who";
        String packName = this.getActivity().getPackageName();
        int resID = getResources().getIdentifier(famous_who, "drawable", packName);
        famous_imgg.setImageResource(resID);  // 일자별로 해당하는 인물사진 불러오기

        rating_bar = v.findViewById(R.id.rating_bar);
        rating_bar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (rating_bar.getRating() == 2.5) {
                rating_txt.setText("어제와 다를 바 없는 오늘"); // 별의 갯수는 0부터 5까지 받기 때문에
                rating_stars = rating - 2.5f; //그래프를 그리면 무조건 상향그래프라 -2.5 해줌으로써 -2.5부터 2.5의 범위를 갖도록 함
            }
            if (rating_bar.getRating() == 3) {
                rating_txt.setText("어제보다 나아진 오늘");
                rating_stars = rating - 2.5f;
            }
            if (rating_bar.getRating() == 2) {
                rating_txt.setText("어제보다 못한 오늘");
                rating_stars = rating - 2.5f;
            }
            if (rating_bar.getRating() == 0) {
                rating_txt.setText("최악의 하루");
                rating_stars = rating - 2.5f;
            }
            if (rating_bar.getRating() == 5) {
                rating_txt.setText("최고의 하루");
                rating_stars = rating - 2.5f;
            }
            if (rating_bar.getRating() == 4.5) {
                rating_txt.setText("최고의 하루");
                rating_stars = rating - 2.5f;
            }
            if (rating_bar.getRating() == 0.5) {
                rating_txt.setText("최악의 하루");
                rating_stars = rating - 2.5f;
            }
            if (rating_bar.getRating() == 4) {
                rating_txt.setText("최고의 하루");
                rating_stars = rating - 2.5f;
            }
            if (rating_bar.getRating() == 1) {
                rating_txt.setText("최악의 하루");
                rating_stars = rating - 2.5f;
            }
            if (rating_bar.getRating() == 3.5) {
                rating_txt.setText("어제보다 나아진 오늘");
                rating_stars = rating - 2.5f;
            }
            if (rating_bar.getRating() == 1.5) {
                rating_txt.setText("어제보다 못한 오늘");
                rating_stars = rating - 2.5f;
            }
        });

        SharedPreferences pref = getActivity().getSharedPreferences("pref", MODE_PRIVATE);
        int past_day_count = pref.getInt("key2", 1);
        // 아무런 작없 없이 d_day 증가하였을 때 그에 맞추어 그래프 그리기위해 필요
        SharedPreferences pref_num = getActivity().getSharedPreferences("pref_num", MODE_PRIVATE);
        rating_stars_num = pref_num.getFloat("key_num", 0);
        // 그린 별 갯수 저장
        SharedPreferences pref_c = getActivity().getSharedPreferences("pref_c", MODE_PRIVATE);
        count_click = pref_c.getInt("key_c", 0);
        // 저장버튼과 수정 버튼을 구분지어서 레이아웃을 표현하기 위함
        // 프래그먼트 생명주기 때문에 레이아웃이 의도대로 그려지지 않아서 부득이하게 설정함
        SharedPreferences pref2 = getActivity().getSharedPreferences("pref2", MODE_PRIVATE);
        rating_stars_sum = pref2.getFloat("key3", 0);
        // 별의 갯수를 축적하며 저장하여 그래프를 그리기 위함.


        SharedPreferences pref_day = getActivity().getSharedPreferences("pref_day", MODE_PRIVATE);
        int day_count = pref_day.getInt("key_day", 1);
        // d_day
        SharedPreferences prefs = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        StringBuilder listToken = new StringBuilder(prefs.getString("name", ""));
        //u를 배열로 저장하기 위함, 그래프 그릴 때 배열값이 필요, 토큰을 이용해 배열값을 구분짓고 저장함
        StringTokenizer st = new StringTokenizer(listToken.toString(), ",");
        while (st.hasMoreTokens()) {
            list.add(Float.parseFloat(st.nextToken()));
        }

        while (day_count != past_day_count) {

            list.add(0, rating_stars_sum);
            listToken = new StringBuilder();
            for (Float i : list) {
                listToken.append(i).append(",");
            }
            SharedPreferences.Editor editors = prefs.edit();
            editors.putString("name", listToken.toString());
            editors.apply(); // 배열에 u 값 추가로 저장 k++를통해 아무작없이 없었을 경우도 저장이된다.

            count_click = 0;
            SharedPreferences.Editor editor_c = pref_c.edit();
            editor_c.putInt("key_c", count_click);
            editor_c.apply();

            past_day_count++;
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("key2", past_day_count);
            editor.apply();// d-day 증가한만큼 list.add해주어야하기 때문에 k++로 횟수를 맞춰주고 저장

        }
        if (count_click > 0) { //작성완료 버튼을 눌렀을 경우
            today_plan = v.findViewById(R.id.today_plan);
            v.findViewById(R.id.remembered_text);
            v.findViewById(R.id.dateNow);
            rating_bar_sub = v.findViewById(R.id.rating_bar_sub);
            rating_bar = v.findViewById(R.id.rating_bar);
            rating_txt = v.findViewById(R.id.rating_txt);
            leaveMessage = v.findViewById(R.id.leaveMessage);
            leftMessage = v.findViewById(R.id.leftMessage);
            editButton = v.findViewById(R.id.editButton);
            dialogButton = v.findViewById(R.id.dialogButton);
            saveButton = v.findViewById(R.id.saveButton);

            saveButton.setVisibility(View.INVISIBLE);
            editButton.setVisibility(View.VISIBLE);
            dialogButton.setVisibility(View.VISIBLE);
            leaveMessage.setVisibility(View.GONE);
            leftMessage.setVisibility(View.VISIBLE);
            rating_bar.setVisibility(View.INVISIBLE);
            rating_bar_sub.setVisibility(View.VISIBLE);

            rating_bar_sub.setRating(rating_stars_num + (float) 2.5); //저장된 별의 갯수만큼 다시 그려줌
            if (rating_bar_sub.getRating() == 2.5) {
                rating_txt.setText("어제와 다를 바 없는 오늘");
            }
            if (rating_bar_sub.getRating() == 3) {
                rating_txt.setText("어제보다 나아진 오늘");
            }
            if (rating_bar_sub.getRating() == 2) {
                rating_txt.setText("어제보다 못한 오늘");
            }
            if (rating_bar_sub.getRating() == 0) {
                rating_txt.setText("최악의 하루");
            }
            if (rating_bar_sub.getRating() == 5) {
                rating_txt.setText("최고의 하루");
            }
            if (rating_bar_sub.getRating() == 4.5) {
                rating_txt.setText("최고의 하루");
            }
            if (rating_bar_sub.getRating() == 0.5) {
                rating_txt.setText("최악의 하루");
            }
            if (rating_bar_sub.getRating() == 4) {
                rating_txt.setText("최고의 하루");
            }
            if (rating_bar_sub.getRating() == 1) {
                rating_txt.setText("최악의 하루");
            }
            if (rating_bar_sub.getRating() == 3.5) {
                rating_txt.setText("어제보다 나아진 오늘");
            }
            if (rating_bar_sub.getRating() == 1.5) {
                rating_txt.setText("어제보다 못한 오늘");
            }
        }
        saveButton = v.findViewById(R.id.saveButton);
        //작성완료 버튼
        saveButton.setOnClickListener(v12 -> {
            mInputMethodManager.hideSoftInputFromWindow(leaveMessage.getWindowToken(), 0);
            leaveMessage.setVisibility(View.GONE);
            leftMessage.setVisibility(View.VISIBLE);
            rating_bar.setVisibility(View.INVISIBLE);
            rating_bar_sub.setVisibility(View.VISIBLE);
            rating_txt.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.INVISIBLE);
            editButton.setVisibility(View.VISIBLE);
            dialogButton.setVisibility(View.VISIBLE);

            saveDiary2(messageFileName); //작성내용 저장하기위한 이름 설정

            rating_stars_num = rating_stars;
            SharedPreferences pref_num12 = getActivity().getSharedPreferences("pref_num", MODE_PRIVATE);
            SharedPreferences.Editor editor_num = pref_num12.edit();
            editor_num.putFloat("key_num", rating_stars_num);
            editor_num.apply(); //별의 갯수를 저장
            rating_bar_sub.setRating(rating_stars_num + (float) 2.5);

            rating_stars_sum += rating_stars;
            SharedPreferences pref212 = getActivity().getSharedPreferences("pref2", MODE_PRIVATE);
            SharedPreferences.Editor editor2 = pref212.edit();
            editor2.putFloat("key3", rating_stars_sum);
            editor2.apply();//증가한 별의 갯수만큼 축적해서 u에 저장

            count_click++;
            SharedPreferences pref_c12 = getActivity().getSharedPreferences("pref_c", MODE_PRIVATE);
            SharedPreferences.Editor editor_c = pref_c12.edit();
            editor_c.putInt("key_c", count_click);
            editor_c.apply(); //작성완료버튼을 누른 상태를 저장

            str_leftMessage = leaveMessage.getText().toString();
            leftMessage.setText(str_leftMessage);//나에게 남길 메시지
        });

        editButton = v.findViewById(R.id.editButton);
        editButton.setOnClickListener(v1 -> {
            leaveMessage.setVisibility(View.VISIBLE);
            leaveMessage.setText(str_leftMessage);
            leftMessage.setVisibility(View.GONE);
            rating_bar.setVisibility(View.VISIBLE);
            rating_bar_sub.setVisibility(View.GONE);
            rating_txt.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.VISIBLE);
            editButton.setVisibility(View.INVISIBLE);
            dialogButton.setVisibility(View.INVISIBLE);

            SharedPreferences pref_num1 = getActivity().getSharedPreferences("pref_num", MODE_PRIVATE);
            rating_stars_num = pref_num1.getFloat("key_num", 0);
            SharedPreferences pref21 = getActivity().getSharedPreferences("pref2", MODE_PRIVATE);
            rating_stars_sum = pref21.getFloat("key3", 0);

            rating_stars_sum -= rating_stars_num;
            SharedPreferences.Editor editor2 = pref21.edit();
            editor2.putFloat("key3", rating_stars_sum);
            editor2.apply(); // 이전에 추가하였던 값을 그대로 뺴주어서 초기상태로 돌림

            count_click = 0;
            SharedPreferences pref_c1 = getActivity().getSharedPreferences("pref_c", MODE_PRIVATE);
            SharedPreferences.Editor editor_c = pref_c1.edit();
            editor_c.putInt("key_c", count_click);
            editor_c.apply(); // 수정버튼 누른 상태를 저장
        });

        Date today7 = new Date();
        SimpleDateFormat date5 = new SimpleDateFormat("yyyy년M월d일", Locale.getDefault());
        String toDay7 = date5.format(today7); // 나에게 남길 메시지 파일명 지정
        messageFileName = "" + toDay7 + ".txt";

        FileInputStream fis7;//FileStream fis 변수

        try {
            fis7 = getActivity().openFileInput(messageFileName);
            byte[] fileData = new byte[fis7.available()];
            //noinspection ResultOfMethodCallIgnored
            fis7.read(fileData);
            fis7.close();

            str_leftMessage = new String(fileData);
            leftMessage.setText(str_leftMessage); //나에게 남길 메시지

        } catch (Exception e) {
            e.printStackTrace();
        }

        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy년 M월 d일", Locale.getDefault());
        String toDay = date.format(today);

        Date today2 = new Date();
        SimpleDateFormat date2 = new SimpleDateFormat("M월 d일", Locale.getDefault());
        String toDay2 = date2.format(today2);


        dateNow.setText(toDay2); //오늘의 날짜를 오른쪽 상단에 표시

        FileInputStream fis;//FileStream fis 변수
        String planFileName = "" + toDay + ".txt";//불러올 파일 이름설정(일정 탭에서 저장한 파일)
        try {
            fis = getActivity().openFileInput(planFileName);
            byte[] fileData = new byte[fis.available()];
            //noinspection ResultOfMethodCallIgnored
            fis.read(fileData);
            fis.close();

            String str_today = new String(fileData);
            today_plan.setText(str_today); //오늘 계획한 일

        } catch (Exception e) {
            e.printStackTrace();
        }


        FileInputStream fis5;//FileStream fis 변수
        String rememberFileName = "명심할 것.txt";//저장된 파일 이름불러오기(일정탭에서 저장함)
        try {
            fis5 = getActivity().openFileInput(rememberFileName);
            byte[] fileData = new byte[fis5.available()];
            //noinspection ResultOfMethodCallIgnored
            fis5.read(fileData);
            fis5.close();

            String str_remember = new String(fileData);
            remembered_text.setText(str_remember); //명심할 한줄

        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }

    @SuppressLint("WrongConstant")
    public void saveDiary2(String readDay2) {
        //나에게 남길 메시지작성을 위해 write
        FileOutputStream fos;
        try {
            fos = getActivity().openFileOutput(readDay2, Context.MODE_PRIVATE);
            String content = leaveMessage.getText().toString();
            fos.write((content).getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
