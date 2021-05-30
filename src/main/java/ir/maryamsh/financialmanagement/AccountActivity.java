package ir.maryamsh.financialmanagement;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import android.widget.Toast;




public class AccountActivity extends AppCompatActivity {
    Button BtnLogin,BtnSingin;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        SharedPreferences shPref;


        init();
        BtnSingin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this,SingupActivity.class));

            }
        });
        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this,LoginActivity.class));
            }
        });

    }

    private void init() {
      BtnLogin=findViewById(R.id.BtnLogin);
      BtnSingin=findViewById(R.id.BtnSingin);
    }
}