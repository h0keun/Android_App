package com.growth.graphh;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.growth.graphh.ui.dashboard.DashboardFragment;
import com.growth.graphh.ui.graph.graph;
import com.growth.graphh.ui.home.HomeFragment;
import com.growth.graphh.ui.notifications.NotificationsFragment;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Fragment fragment1 = new HomeFragment();
    Fragment fragment2 = new DashboardFragment();
    Fragment fragment3 = new NotificationsFragment();
    Fragment fragment4 = new graph();
    Fragment active = fragment3;
    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Graphh);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        fm.beginTransaction().add(R.id.container, fragment3).commit();//젤먼저 추가해줘야 함
        fm.beginTransaction().add(R.id.container, fragment1).hide(fragment1).commit();
        fm.beginTransaction().add(R.id.container, fragment2).hide(fragment2).commit();
        fm.beginTransaction().add(R.id.container, fragment4).hide(fragment4).commit();

        SharedPreferences pref_start = getSharedPreferences("pref_start", MODE_PRIVATE);
        //앱 처음 실행했을 때 1번만 감지하기위해 ifFirstRun true 로 두고 실행

        boolean isFirstRun = pref_start.getBoolean("isFirstRun", true);
        long day_first;

        if (isFirstRun) {

            Date start_day = new Date();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy", Locale.getDefault());
            SimpleDateFormat sdf2 = new SimpleDateFormat("M", Locale.getDefault());
            SimpleDateFormat sdf3 = new SimpleDateFormat("d", Locale.getDefault());

            int year_st = Integer.parseInt(sdf1.format(start_day));
            int month_st = Integer.parseInt(sdf2.format(start_day));
            int day_st = Integer.parseInt(sdf3.format(start_day));

            Calendar first_day = new GregorianCalendar(year_st, month_st, day_st);
            day_first = first_day.getTimeInMillis() / 86400000;
            // 앱 최초로 한번 실행한 날짜 초로 반환
            //( 1일의 값(86400000 = 24시간 * 60분 * 60초 * 1000(1초값)))

            SharedPreferences pref_first = getSharedPreferences("pref_first", MODE_PRIVATE);
            SharedPreferences.Editor editor_first = pref_first.edit();
            editor_first.putLong("first", day_first);
            editor_first.apply();

            pref_start.edit().putBoolean("isFirstRun", false).apply();
            //처음만 true 그다음부터는 false 바꾸는 동작
        }

        SharedPreferences pref_first = getSharedPreferences("pref_first", MODE_PRIVATE);
        day_first = pref_first.getLong("first", 0); //저장한 앱 최초실행날짜 불러오기

        Date count_day = new Date();
        SimpleDateFormat sdf11 = new SimpleDateFormat("yyyy", Locale.getDefault());
        SimpleDateFormat sdf22 = new SimpleDateFormat("M", Locale.getDefault());
        SimpleDateFormat sdf33 = new SimpleDateFormat("d", Locale.getDefault());

        int year_cd = Integer.parseInt(sdf11.format(count_day));
        int month_cd = Integer.parseInt(sdf22.format(count_day));
        int day_cd = Integer.parseInt(sdf33.format(count_day));
        Calendar counting = new GregorianCalendar(year_cd, month_cd, day_cd);
        long day_count = counting.getTimeInMillis() / 86400000; //앱 실행하는 날짜(최초 실행날짜 아님)

        long count = day_count - day_first + 1; // 앱 실행하는 오늘날짜 - 앱 최초 처음 실행한 날짜 = d-day

        int k_count = (int) count; // int k 로 형변환
        SharedPreferences pref_day = getSharedPreferences("pref_day", MODE_PRIVATE);
        SharedPreferences.Editor editor_day = pref_day.edit();
        editor_day.putInt("key_day", k_count);
        editor_day.apply();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide(); //액션바 숨기기
        setupBottomNavigation();
    }

    private void setupBottomNavigation() {//프래그먼트 바뀔떄마다 애드몹 리퀘스트로 앱이 느려져서 add,hide 사용

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        TypedValue typedValue = new TypedValue(); //테마별 바텀네비게이션 뷰 색상
        Resources.Theme theme = this.getTheme();
        theme.resolveAttribute(R.attr.tabBackground, typedValue, true);
        @ColorInt int color = typedValue.data;
        bottomNavigationView.setBackgroundColor(color);
        bottomNavigationView.setSelectedItemId(R.id.navigation_notifications);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        fm.beginTransaction().hide(active).detach(fragment3).show(fragment1).commit();
                        active = fragment1;
                        return true;

                    case R.id.navigation_dashboard:
                        fm.beginTransaction().hide(active).show(fragment2).commit();
                        active = fragment2;
                        return true;

                    case R.id.navigation_notifications:
                        fm.beginTransaction().hide(active).detach(fragment4).attach(fragment3).show(fragment3).commit();
                        active = fragment3;
                        return true;

                    case R.id.navigation_graph:
                        fm.beginTransaction().hide(active).attach(fragment4).show(fragment4).commit();
                        active = fragment4;
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        //화면전환이나 테마변경시 recreate 로 엑티비티 중복되는것 막기위해 그냥 액티비티 재실행
        super.onConfigurationChanged(newConfig);
        int currentNightMode = newConfig.uiMode & Configuration.UI_MODE_NIGHT_MASK;

        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
            case Configuration.UI_MODE_NIGHT_YES:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                this.finish();
                break;
        }
    }
}