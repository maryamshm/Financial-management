package ir.maryamsh.financialmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class IntroActivity extends AppCompatActivity {
    ViewpagerAdapter view_Adp;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        viewPager=findViewById(R.id.viewpager);
        view_Adp=new ViewpagerAdapter(this);
        viewPager.setAdapter(view_Adp);
        SharedPreferences shPref = getSharedPreferences("shPref", MODE_PRIVATE);
        if(shPref.getString("email", null)!=null){
            startActivity(new Intent(IntroActivity.this,MainActivity.class));
        }
    }
}