package ir.maryamsh.financialmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class AccountActivity extends AppCompatActivity {
    Button BtnLogin,BtnSingin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
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