package com.growth.graphh.ui.dashboard;

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
import com.growth.graphh.R;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DashboardFragment extends Fragment {

    TextView dateYes, textView10, textView11, text_set3, text_set4;
    ImageView famous_img;
    String go2 = null;
    String go3 = null;
    String str2 = null;
    String str3 = null;
    String famous;
    CardView ads_card;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        SimpleDateFormat date4 = new SimpleDateFormat("yyyy년 M월 d일", Locale.getDefault());
        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(calendar2.DATE, -1);
        String toDay4 = date4.format(calendar2.getTime());
        // 계획한 일 날짜 받기위함

        SimpleDateFormat date6 = new SimpleDateFormat("yyyy년M월d일", Locale.getDefault());
        Calendar calendar3 = Calendar.getInstance();
        calendar3.add(calendar3.DATE, -1);
        String toDay6 = date6.format(calendar3.getTime());
        // 나에게 남긴 메시지 날짜 받기위함

        SimpleDateFormat date2 = new SimpleDateFormat("M월 d일", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.add(calendar.DATE, -1);
        String toDay3 = date2.format(calendar.getTime());
        // 레이아웃 우측상단에 어제날자 표시

        Date famous_day = new Date();
        SimpleDateFormat sdf5 = new SimpleDateFormat("d", Locale.getDefault());
        // 일자별로 다른 명언 불러들이기 위함
        ads_card = v.findViewById(R.id.ads_card);
        textView10 = v.findViewById(R.id.textView10);
        textView11 = v.findViewById(R.id.textView11);
        dateYes = (TextView) v.findViewById(R.id.dateYes);
        dateYes.setText(toDay3); //어제날짜

        int day_famous = Integer.parseInt(sdf5.format(famous_day));
        AssetManager am = getResources().getAssets(); // Asset폴더에 있는 파일 읽기
        InputStream is = null;

        try {
            is = am.open("famous_saying");

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            for (int i = 1; i < day_famous; i++) {
                br.readLine();
            } // BufferedReader을 통해 해당날짜에 해당하는 줄 이외의 줄을 읽지 않음
            text_set3 = (TextView) v.findViewById(R.id.text_set3);
            text_set3.setText(br.readLine()); //해당날짜에 해당하는 줄의 명언표시

            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        InputStream iss = null;
        try {
            iss = am.open("famous_saying_who");

            BufferedReader brr = new BufferedReader(new InputStreamReader(iss));
            for (int i = 1; i < day_famous ; i++) {
                brr.readLine();
            } // BufferedReader을 통해 해당날짜에 해당하는 줄 이외의 줄을 읽지 않음
            text_set4 = (TextView) v.findViewById(R.id.text_set4);
            text_set4.setText(brr.readLine() + " "); //해당날짜에 해당하는 유명인표시, 이텔릭체라 글자조금 짤려서 공백추가

            iss.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        famous_img = v.findViewById(R.id.famous_img);
        famous = Integer.toString(day_famous);
        String famous_who = "@drawable/day_" + famous + "_who";
        String packName = this.getActivity().getPackageName();
        int resID = getResources().getIdentifier(famous_who, "drawable", packName);
        famous_img.setImageResource(resID);  // 일자별로 해당하는 인물사진 불러오기


        FileInputStream fis = null;
        go2 = "" + toDay4 + ".txt"; //읽어들일 파일 이름설정(오늘 탭에서 저장한 파일 읽기)
        try {
            fis = getActivity().openFileInput(go2);
            byte[] fileData = new byte[fis.available()];
            fis.read(fileData);
            fis.close();

            str2 = new String(fileData);
            textView10.setText(str2); //계획했던 일

        } catch (Exception e) {
            e.printStackTrace();
        }

        FileInputStream fis3 = null;
        go3 = "" + toDay6 + ".txt";//읽어들일 파일 이름설정(오늘 탭에서 저장한 파일 읽기)
        try {
            fis3 = getActivity().openFileInput(go3);
            byte[] fileData = new byte[fis3.available()];
            fis3.read(fileData);
            fis3.close();

            str3 = new String(fileData);
            textView11.setText(str3); //나에게 남긴 메시지

        } catch (Exception e) {
            e.printStackTrace();
        }
        /*ca-app-pub-1350498864943165/3398973741 real*/
        /*ca-app-pub-3940256099942544/2247696110 fake*/
        AdLoader adLoader = new AdLoader.Builder(getContext(), "ca-app-pub-3940256099942544/2247696110")
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        // Show the ad.
                        ads_card.setVisibility(View.VISIBLE);
                        TemplateView template = v.findViewById(R.id.my_template);
                        template.setNativeAd(unifiedNativeAd);
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        // Handle the failure by logging, altering the UI, and so on.
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build();
        adLoader.loadAds(new AdRequest.Builder().build(), 3);

        return v;
    }
/*    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //AdLoader 이쪽에 넣어야 하는거같은데 이유 찾아봐야할듯 프래그먼트 생명주기
    }*/

}