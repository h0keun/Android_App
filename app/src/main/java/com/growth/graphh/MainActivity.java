package com.growth.graphh;

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

        getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); //화면전환이나 테마변경시 액티비티 중복 방지하기위해 스택제거(제대로 작동하는지는 모르겠음)

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        fm.beginTransaction().add(R.id.container, fragment3, "3").commit();//젤먼저 추가해줘야 함
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
        TypedValue typedValue = new TypedValue(); //테마별 바텀네비게이션 뷰 색상
        Resources.Theme theme = this.getTheme();
        theme.resolveAttribute(R.attr.tabBackground, typedValue, true);
        @ColorInt int color = typedValue.data;
        bottomNavigationView.setBackgroundColor(color);
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
    public void onConfigurationChanged(@NonNull Configuration newConfig) { //화면전환이나 테마변경시 recreate로 엑티비티 중복되는것 막기위해 그냥 액티비티 재실행
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

// 개선헤야할 점
// 1. 기존에 NavController에서 bottomnavigation 클릭시 프래그먼트 replace때문에 매번 광고요청을 해서 앱이 느려졌음.
// >> 프래그먼트 add,hide로 앱이 느려지는것 방지함

// 2. 프래그먼트 add,hide로 광고리퀘스트를 막고 앱이 느려지는것 막았으나 화면전환이나 테마변경시 recreate로 액티비티가 중첩되었음.
// >> configChanges를 통해 화면전환시에 액티비티 중첩을 막았지만, 테마변경이 안돼서 액티비티 재실행으로 화전전환시에도 동일하게 적용함
// >> 화면회전은 이대로 적용하고 테마변경시 recreate막는거는 다른 방법 있는듯함

// 3. 오늘탭에서 평점매기고 저장하면 그래프가 변해야하는데 프래그먼트 add,hide로 바뀌어서 그래프에 적용되는게 바로안보이고 재실행 해야 적용되서 보임
// >> 마찬가지로 저장누르면 액티비티 재 시작으로 구성함

// 4. 무슨경우인지 모르겠으만 어떤경우에는 앱 재실행시 프래그먼트 겹쳐서보임//엑티비티 스텍계속 쌓여서 생기는 문제인듯
// >>FLAG_ACTIVITY_CLEAR_TASK 랑, clearTaskOnLaunch="true" 적용했으나 제대로 작동하는지 확인 필요함

// 찾은 문제들 모두 엑티비티 재시작으로 해결한거라 불안정함. 스택관리,생명주기 등등 고려할게 많은거같음
// 기존에 navController 사용하면 프래그먼트 replace라 광고 리퀴스트때문에 앱 느려지는것 말고 문제가 하나도 없음
// 프래그먼트 replace로 두고 뷰모델 활용하는 방안 고려해 볼것