package ir.maryamsh.financialmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    FrameLayout frameLayout;
    BottomNavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        navigation.setOnNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new home_fragment(MainActivity.this)).commit();
    }

    private void init() {
       frameLayout=findViewById(R.id.framelayout);
       navigation=findViewById(R.id.bottomBar);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
         switch (item.getItemId()){
             case R.id.home_item:
                 getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new home_fragment(MainActivity.this)).commit();
                 break;
             case R.id.add_item:
                 getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new add_fragment(MainActivity.this)).commit();
                 break;
             case R.id.report_item:
                 getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new report_fragment()).commit();
                 break;
         }
        return true;
    }
}