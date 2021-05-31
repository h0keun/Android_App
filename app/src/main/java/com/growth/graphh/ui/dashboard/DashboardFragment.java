package com.growth.graphh.ui.dashboard;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.LoadAdError;
import com.growth.graphh.R;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DashboardFragment extends Fragment {

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        SimpleDateFormat date_yesterday = new SimpleDateFormat("M월 d일", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        String DateYes = date_yesterday.format(calendar.getTime());
        // 레이아웃 우측상단에 어제날자 표시

        SimpleDateFormat date_yesterday_plan = new SimpleDateFormat("yyyy년 M월 d일", Locale.getDefault());
        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.DATE, -1);
        String DateYesPlan = date_yesterday_plan.format(calendar2.getTime());
        // 계획한 일 날짜 받기위함

        SimpleDateFormat date_yesterday_message = new SimpleDateFormat("yyyy년M월d일", Locale.getDefault());
        Calendar calendar3 = Calendar.getInstance();
        calendar3.add(Calendar.DATE, -1);
        String DateYesMessage = date_yesterday_message.format(calendar3.getTime());
        // 나에게 남긴 메시지 날짜 받기위함

        Date famous_day = new Date();
        SimpleDateFormat sdf5 = new SimpleDateFormat("d", Locale.getDefault());
        // 일자별로 다른 명언 불러들이기 위함
        CardView ads_card = v.findViewById(R.id.ads_card);
        TextView yesterday_plan = v.findViewById(R.id.yesterday_plan);
        TextView yesterday_message = v.findViewById(R.id.yesterday_message);
        TextView dateYes = v.findViewById(R.id.dateYes);
        dateYes.setText(DateYes); //어제날짜

        int day_famous = Integer.parseInt(sdf5.format(famous_day));
        AssetManager am = getResources().getAssets(); // Asset폴더에 있는 파일 읽기
        InputStream is;
        try {
            is = am.open("famous_saying");

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            for (int i = 1; i < day_famous; i++) {
                br.readLine();
            } // BufferedReader을 통해 해당날짜에 해당하는 줄 이외의 줄을 읽지 않음
            TextView famousSaying = v.findViewById(R.id.famousSaying);
            famousSaying.setText(br.readLine()); //해당날짜에 해당하는 줄의 명언표시

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
            TextView famousWho = v.findViewById(R.id.famousWho);
            famousWho.setText(brr.readLine() + " "); //해당날짜에 해당하는 유명인표시, 이텔릭체라 글자조금 짤려서 공백추가

            iss.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        ImageView famous_img = v.findViewById(R.id.famous_img);
        String famous = Integer.toString(day_famous);
        String famous_who = "@drawable/day_" + famous + "_who";
        String packName = this.getActivity().getPackageName();
        int resID = getResources().getIdentifier(famous_who, "drawable", packName);
        famous_img.setImageResource(resID);  // 일자별로 해당하는 인물사진 불러오기


        FileInputStream fis;
        String go2 = "" + DateYesPlan + ".txt"; //읽어들일 파일 이름설정(오늘 탭에서 저장한 파일 읽기)
        try {
            fis = getActivity().openFileInput(go2);
            byte[] fileData = new byte[fis.available()];
            //noinspection ResultOfMethodCallIgnored
            fis.read(fileData);
            fis.close();

            String str2 = new String(fileData);
            yesterday_plan.setText(str2); //계획했던 일

        } catch (Exception e) {
            e.printStackTrace();
        }

        FileInputStream fis3;
        String go3 = "" + DateYesMessage + ".txt";//읽어들일 파일 이름설정(오늘 탭에서 저장한 파일 읽기)
        try {
            fis3 = getActivity().openFileInput(go3);
            byte[] fileData = new byte[fis3.available()];
            //noinspection ResultOfMethodCallIgnored
            fis3.read(fileData);
            fis3.close();

            String str3 = new String(fileData);
            yesterday_message.setText(str3); //나에게 남긴 메시지

        } catch (Exception e) {
            e.printStackTrace();
        }

        /*ca-app-pub-3940256099942544/2247696110 fake*/
        AdLoader adLoader = new AdLoader.Builder(getContext(), "ca-app-pub-3940256099942544/2247696110")
                .forNativeAd(nativeAd -> {
                    ads_card.setVisibility(View.VISIBLE);
                    TemplateView template = v.findViewById(R.id.my_template);
                    template.setNativeAd(nativeAd);
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                    }

                    @Override
                    public void onAdClicked() {
                        super.onAdClicked();
                    }
                })

                .build();

        adLoader.loadAds(new AdRequest.Builder().build(), 3);

        return v;
    }
}