package ir.maryamsh.financialmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    SmoothBottomBar navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        navigation.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                switch (i){
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new home_fragment(MainActivity.this)).commit();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new add_fragment(MainActivity.this)).commit();
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new report_fragment()).commit();
                        break;
                }
                return true;
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new home_fragment(MainActivity.this)).commit();
    }

    private void init() {
       frameLayout=findViewById(R.id.framelayout);
       navigation=findViewById(R.id.bottomNavigationView);
    }

}