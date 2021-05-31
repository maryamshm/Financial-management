package ir.maryamsh.financialmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.tapadoo.alerter.Alerter;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {
    Button BtnLogin;
    EditText TxtEmail;
    EditText TxtPass;
    FirebaseAuth auth;
    SharedPreferences shPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ini();
        Locale locale = new Locale("fa");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        this.getApplicationContext().getResources().updateConfiguration(config, null);
        shPref = getSharedPreferences("shPref", MODE_PRIVATE);
        auth=FirebaseAuth.getInstance();
        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validdata()) {
                    String email = TxtEmail.getText().toString().trim();
                    String pass = TxtPass.getText().toString().trim();
                    auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                SharedPreferences.Editor editor = shPref.edit();
                                editor.putString("email", email);
                                editor.commit();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            } else {
                                if(task.getException().getLocalizedMessage().contains("403")){
                                    ShowAlert("از روشن بودن فیلتر شکن مطمئن شوید (:");
                                }
                                else
                                Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

    }
    private void ShowAlert(String s) {
        Alerter.create(this)
                .setTitle("ورود")
                .setText(s)
                .setIcon(R.drawable.ic_prof)
                .setIconColorFilter(0)
                .setBackgroundColorRes(R.color.toastbg)
                .setDuration(3000)
                .enableSwipeToDismiss() //seems to not work well with OnClickListener
                .show();

    }
    private boolean validdata() {
        if(TxtEmail.getText().toString().trim().isEmpty() || TxtPass.getText().toString().trim().isEmpty()){
            ShowAlert("لطفا فیلد ها را کامل پر کنید");
            return false;
        }
        else {
            return true;
        }
    }

    private void ini() {
       BtnLogin=findViewById(R.id.ButtonLoginin);
       TxtEmail=findViewById(R.id.TxtEmaillogin);
       TxtPass=findViewById(R.id.TxtPasslogin);
    }
}