package com.example.yourgrowth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NaviActivity extends AppCompatActivity {

    FrameLayout mainFrameLayout;
    BottomNavigationView navigationView;

    HomeFragment homeFragment;
    MyPageFragment myPageFragment;
    CalenderFragment calenderFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi);

        init();//객체 정의
        SettingListener(); //리스너 등록

    }
    private void init() {
        mainFrameLayout = findViewById(R.id.mainFrameLayout);
        navigationView = findViewById(R.id.navigationView);
    }

    private void SettingListener() {
        //선택 리스너 등록
        navigationView.setOnNavigationItemSelectedListener(new TabSelectedListener());
    }


    class TabSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.homeFragment: {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainFrameLayout, new HomeFragment())
                            .commit();
                    return true;
                }
                case R.id.calenderFragment: {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainFrameLayout, new CalenderFragment())
                            .commit();
                    return true;
                }
                case R.id.myPageFragment: {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainFrameLayout, new MyPageFragment())
                            .commit();
                    return true;
                }
            }

            return false;
        }
    }
}