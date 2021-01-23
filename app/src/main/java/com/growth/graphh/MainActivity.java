package com.growth.graphh;

import android.app.usage.ConfigurationStats;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.growth.graphh.ui.dashboard.DashboardFragment;
import com.growth.graphh.ui.graph.graph;
import com.growth.graphh.ui.home.HomeFragment;
import com.growth.graphh.ui.notifications.NotificationsFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public SharedPreferences pref_start;
    long day_first;
    long day_count;
    long count;
    int k_count;
    int t;

    final Fragment fragment1 = new HomeFragment();
    final Fragment fragment2 = new DashboardFragment();
    final Fragment fragment3 = new NotificationsFragment();
    final Fragment fragment4 = new graph();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Graphh);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        fm.beginTransaction().add(R.id.container,fragment3, "3").commit();//젤먼저 추가해줘야 함
        fm.beginTransaction().add(R.id.container, fragment1, "1").hide(fragment1).commit();
        fm.beginTransaction().add(R.id.container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.container, fragment4, "4").hide(fragment4).commit();


        pref_start = getSharedPreferences("pref_start", MODE_PRIVATE);
        //앱 처음 실행했을 때 1번만 감지하기위해 ifFirstRv un true로 두고 실행

        boolean isFirstRun = pref_start.getBoolean("isFirstRun", true);
        if (isFirstRun == true) {

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
            //( 1일의 값(86400000 = 24시간 * 60분 * 60초 * 1000(1초값) ) )

            SharedPreferences pref_first = getSharedPreferences("pref_first", MODE_PRIVATE);
            SharedPreferences.Editor editor_first = pref_first.edit();
            editor_first.putLong("first", day_first);
            editor_first.commit();

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
        day_count = counting.getTimeInMillis() / 86400000; //앱 실행하는 날짜(최초 실행날짜 아님)

        count = day_count - day_first + 1; // 앱 실행하는 오늘날짜 - 앱 최초 처음 실행한 날짜 = d-day

        k_count = (int) count; // int k 로 형변환
        SharedPreferences pref_day = getSharedPreferences("pref_day", MODE_PRIVATE);
        SharedPreferences.Editor editor_day = pref_day.edit();
        editor_day.putInt("key_day", k_count);
        editor_day.commit();

        /*BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_graph)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);*/
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide(); //액션바 숨기기
        setupBottomNavigation();
    }
    private void setupBottomNavigation(){//프래그먼트 바뀔떄마다 애드몹 리퀘스트로 앱이 느려져서 add,hide 사용

        BottomNavigationView bottomNavigationView =findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.navigation_notifications);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        fm.beginTransaction().hide(active).show(fragment1).commit();
                        active = fragment1;
                        return true;

                    case R.id.navigation_dashboard:
                        fm.beginTransaction().hide(active).show(fragment2).commit();
                        active = fragment2;
                        return true;

                    case R.id.navigation_notifications:
                        fm.beginTransaction().hide(active).show(fragment3).commit();
                        active = fragment3;
                        return true;

                    case R.id.navigation_graph:
                        fm.beginTransaction().hide(active).show(fragment4).commit();
                        active = fragment4;
                        return true;
                }
                return false;
            }
        });
    }
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        //화면회전시 recreate 방지(fragment add,hide라 중첩되기 떄문)

        int currentNightMode = newConfig.uiMode & Configuration.UI_MODE_NIGHT_MASK; // 테마변경시 recreate방지
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
            case Configuration.UI_MODE_NIGHT_YES:

                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                this.finish();
                break;
        }
        super.onConfigurationChanged(newConfig);//더 좋은 방법 찾아보기! >> 화면회전은 이걸로하고 다크모드는 다른 방법이 있는듯
    }//화면회전만 썻을경우엔 문제 없었는데 테마변경까지 합치니까 둘다 재실행으로 넘어감 화면회전시 에 테마를 새로 읽어서 그런듯?
}
