package com.example.graphh;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{
    public SharedPreferences pref_start;
    long day_first;
    long day_count;
    long count;
    int k_count;

/*    final Fragment fragment1 = new HomeFragment();
    final Fragment fragment2 = new DashboardFragment();
    final Fragment fragment3 = new NotificationsFragment();
    final Fragment fragment4 = new graph();
    final FragmentManager fm = getSupportFragmentManager();*/
   /* Fragment active = fragment3;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Graphh);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref_start = getSharedPreferences("pref_start", MODE_PRIVATE);
        //앱 처음 실행했을 때 1번만 감지하기위해 ifFirstRun true로 두고 실행

        boolean isFirstRun = pref_start.getBoolean("isFirstRun", true);
        if (isFirstRun==true) {

            Date start_day = new Date();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy", Locale.getDefault());
            SimpleDateFormat sdf2 = new SimpleDateFormat("M",Locale.getDefault());
            SimpleDateFormat sdf3 = new SimpleDateFormat("d",Locale.getDefault());

            int year_st = Integer.parseInt(sdf1.format(start_day));
            int month_st = Integer.parseInt(sdf2.format(start_day));
            int day_st = Integer.parseInt(sdf3.format(start_day));

            Calendar first_day = new GregorianCalendar(year_st,month_st,day_st);
            day_first = first_day.getTimeInMillis() / 86400000 ;
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
        SimpleDateFormat sdf11 = new SimpleDateFormat("yyyy",Locale.getDefault());
        SimpleDateFormat sdf22 = new SimpleDateFormat("M",Locale.getDefault());
        SimpleDateFormat sdf33 = new SimpleDateFormat("d",Locale.getDefault());

        int year_cd = Integer.parseInt(sdf11.format(count_day));
        int month_cd = Integer.parseInt(sdf22.format(count_day));
        int day_cd = Integer.parseInt(sdf33.format(count_day));
        Calendar counting = new GregorianCalendar(year_cd,month_cd,day_cd);
        day_count = counting.getTimeInMillis()  / 86400000; //앱 실행하는 날짜(최초 실행날짜 아님)

        count = day_count - day_first + 1; // 앱 실행하는 오늘날짜 - 앱 최초 처음 실행한 날짜 = d-day

        k_count = (int)count; // int k 로 형변환
        SharedPreferences pref_day = getSharedPreferences("pref_day", MODE_PRIVATE);
        SharedPreferences.Editor editor_day = pref_day.edit();
        editor_day.putInt("key_day",k_count);
        editor_day.commit();

        /*fm.beginTransaction().add(R.id.container, fragment4, "4").hide(fragment4).commit();
        fm.beginTransaction().add(R.id.container, fragment1, "1").hide(fragment1).commit();
        fm.beginTransaction().add(R.id.container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.container,fragment3, "3").hide(fragment3).commit();*/



        // 프래그먼트 replace 아닌 add, hide하기위해 따로 만듦
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_graph)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide(); //액션바 숨기기
    }
/*    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

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
    };*/


  /*  @Override // 키보드 올라왔을 때 다른 영역 클릭시 키보드 내려가도록 ,
                 // 스크롤 아래쪽에 저장버튼이있어서 저장버튼 누르려 스크롤시 키보드가 내려가서 이 레이아웃하고는 안어울림
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }*/
}
