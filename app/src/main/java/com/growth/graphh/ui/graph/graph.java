package com.growth.graphh.ui.graph;

import androidx.annotation.ColorInt;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import com.growth.graphh.DatePickerFragment;
import com.growth.graphh.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

import static android.content.Context.MODE_PRIVATE;

public class graph extends Fragment implements DatePickerDialog.OnDateSetListener{
    private DatePickerDialog.OnDateSetListener callbackMethod;
    public CardView button_click,ad_card;
    public TextView diaryTextView2,textView20,textView21,days;
    public String str10,str11;
    String fname10, fname11, ss, dd;
    int k, k_count;
    float u;
    ArrayList<Float> xtx = new ArrayList<>();
    public static graph newInstance() {
        return new graph();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.graph_fragment, container, false);

        diaryTextView2=v.findViewById(R.id.diaryTextView2); //기록확인하기 날짜 보여지는곳
        textView20=v.findViewById(R.id.textView20); //나에게 남긴 메시지
        textView21=v.findViewById(R.id.textView21); //계획한 일
        LineChart chart = v.findViewById(R.id.linechart);

        button_click=v.findViewById(R.id.button_click);//DatePickerDialog 띄우기
        button_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.setTargetFragment(graph.this,0);
                
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        SharedPreferences pref = getActivity().getSharedPreferences("pref", MODE_PRIVATE);
        k = pref.getInt("key2", 1);//d-day와 비교해 그래프 그리기 위해 필요
        SharedPreferences pref_day = getActivity().getSharedPreferences("pref_day", MODE_PRIVATE);
        k_count = pref_day.getInt("key_day", 1); //d-day

        dd = String.valueOf(k_count);
        days = (TextView) v.findViewById(R.id.days);
        days.setText("+"+dd+"일"); // d-day 우측상단에 표시 = 앱 사용 일수

        SharedPreferences pref2 = getActivity().getSharedPreferences("pref2", MODE_PRIVATE);
        u = pref2.getFloat("key3",0);
        //오늘에 해당하는 그래프 변화 확인가능하도록 오늘탭에서 저장한 값을 읽어온다.
        SharedPreferences prefs = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        ss = prefs.getString("name", "");
        StringTokenizer st = new StringTokenizer(ss, ",");
        while (st.hasMoreTokens()) {
            xtx.add(Float.parseFloat(st.nextToken()));
        }//오늘 탭에서 저장한 배열을 불러온다.

       /* Log.d("LOG_TAG",xtx +"xtx 값은??");*/
        ArrayList<Entry> values = new ArrayList<>();
        values.add(new Entry(0, 0));
        values.add(new Entry(1, 0)); //그래프 그리기위한 초기 값
        for (int i = 2; i <k_count +1 ; i++) {
            values.add(new Entry(i, xtx.get(k_count-i))); //오늘탭에서 저장한 배열을 읽는 부분
        }
        values.add(new Entry(k_count+1,u)); //오늘에 해당하는 그래프값(변화되는 u값이 보여진다.)
        LineDataSet set1;
        set1 = new LineDataSet(values, "인생 그래프");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1); // add the data sets
        // create a data object with the data sets
        LineData data = new LineData(dataSets);
        chart.setData(data);
        XAxis xAxis;
        {
            xAxis = chart.getXAxis(); // x 축 설정
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //x 축 표시에 대한 위치 설정
            xAxis.setTextColor(Color.BLACK);
            xAxis.setDrawGridLines(false);
            xAxis.setGranularity(1f);
            xAxis.setDrawAxisLine(false);
            xAxis.setValueFormatter(new xAxisValueFormatter(chart));
            xAxis.enableGridDashedLine(10, 24, -10);
        }
        YAxis yAxis;
        {
            yAxis = chart.getAxisRight();
            yAxis.setTextColor(Color.BLACK);
            yAxis.setDrawLabels(false);
            yAxis.setDrawAxisLine(false);
            yAxis.setDrawGridLines(false);
            yAxis.setAxisMaximum(100f);
            yAxis.setAxisMinimum(-50f);
            yAxis.enableGridDashedLine(10f, 10f, -10f);
        }
        // set data
        chart.setData(data);
        chart.setBackgroundColor(Color.TRANSPARENT); // 그래프 배경 색 설정
        /*set1.setCircleColor(color); // 차트의 points 점 색 설정*/
        set1.setDrawFilled(true); // 차트 아래 fill(채우기) 설정
        /* set1.setColor(Color.BLACK); */// 차트의 선 색 설정

        TypedValue typedValue = new TypedValue(); //그래프는 자동으로 테마가 적용되지 않아서 의도적으로 테마 적용
        Resources.Theme theme = getContext().getTheme();
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
        @ColorInt int color = typedValue.data;
        set1.setColor(color); /// 테마별 선 색상
        xAxis.setTextColor(color);//테마별 날짜 라벨 색상
        set1.setCircleColor(color); // 차트의 points 점 색 설정

        TypedValue typedValue1 = new TypedValue();
        Resources.Theme theme1 = getContext().getTheme();
        theme1.resolveAttribute(R.attr.colorSecondaryVariant, typedValue1,true);
        @ColorInt int color2 = typedValue1.data;
        set1.setFillColor(color2);//테마별 차트아래 채우기 색상

        chart.invalidate();
        chart.animateXY(1500,1500);//차트 그려지는 애니매이션
        set1.setDrawValues(false);//각 데이터값 안보이게
        set1.setDrawCircles(false);//표시점 안보이게
        set1.setCircleRadius((float)1.5);//원 반지름크기
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);//그래프곡선
        set1.setLineWidth((float)1.0);//선두께
        set1.setHighLightColor(Color.rgb(255, 183, 0));//그래프 선택시 그 수치를 기준으로 나타나는 십자가 선
        chart.getDescription().setEnabled(false);
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setEnabled(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getLegend().setEnabled(false);///라벨이름 숨기기

        return v;
    }
    @Override
    //DatePickerDialog에서 받아온 날짜 값을 StringBuilder을 통해 변수를 설정하고 저장한 파일을 읽어온다.
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        StringBuilder sb = new StringBuilder().append(year).append("년").append(monthOfYear + 1).append("월").append(dayOfMonth).append("일");
        String formattedDate = sb.toString();

        fname10=""+formattedDate+".txt"; //오늘탭에서 저장한 파일명을 사용함.
        FileInputStream fis10=null;//FileStream fis 변수
        try{
            fis10=getActivity().openFileInput(fname10);

            byte[] fileData=new byte[fis10.available()];
            fis10.read(fileData);
            fis10.close();

            str10=new String(fileData);
            textView20.setText(str10); //나에게 남긴 메시지

        }catch (FileNotFoundException fnfe) { // 저장한값 없을때 아무것도 표시하지 않음
            textView20.setText("");
        }
        catch (Exception e){
            e.printStackTrace();
        }

        StringBuilder sb2 = new StringBuilder().append(year).append("년 ").append(monthOfYear + 1).append("월 ").append(dayOfMonth).append("일");
        String formattedDate2 = sb2.toString();
        diaryTextView2.setText(formattedDate2);//확인하는 날짜가 보여지도록
        fname11=""+formattedDate2+".txt"; //일정탭에서 저장한 계획의 파일명을 사용.
        FileInputStream fis11=null;//FileStream fis 변수
        try{
            fis11=getActivity().openFileInput(fname11);

            byte[] fileData=new byte[fis11.available()];
            fis11.read(fileData);
            if(fis11.read(fileData)==0){
               textView21.setText("");
            }
            fis11.close();

            str11=new String(fileData);
            textView21.setText(str11); //계획한 일

        }
        catch (FileNotFoundException fnfe) { // 저장한값 없을때 아무것도 표시하지 않음
            textView21.setText("");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public class xAxisValueFormatter extends ValueFormatter {
        //그래프의 x축 라벨을 날짜로 설정
        private final LineChart chart;

        public xAxisValueFormatter(LineChart chart) {
            this.chart = chart;
        }

        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            SharedPreferences pref_day = getActivity().getSharedPreferences("pref_day", MODE_PRIVATE);
            k_count = pref_day.getInt("key_day", 1);
            //d-day에 맞게 라벨 크기가 증가해야 함

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat mFormat = new SimpleDateFormat("M월d일", Locale.getDefault());

            final ArrayList<String> xLabel = new ArrayList<>();
            calendar.add(calendar.DATE, -(k_count) - 2);
            for (int j = 0; j <= k_count + 1; j++) {
                calendar.add(calendar.DAY_OF_YEAR, 1);
                Date date = calendar.getTime();
                String txtDate = mFormat.format(date);
                xLabel.add(txtDate);
            }
            xLabel.set(k_count + 1, "오늘");
            return xLabel.get((int) value);
        }
    }
}
