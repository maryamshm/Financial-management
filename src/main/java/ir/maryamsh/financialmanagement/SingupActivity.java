package ir.maryamsh.financialmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.tapadoo.alerter.Alerter;

import java.util.Locale;

public class SingupActivity extends AppCompatActivity {
    Button BtnSignup;
    FirebaseFirestore database;
    TextView TxtEmail,TXTName,TxtPass;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration overrideConfiguration = this.getResources().getConfiguration();
        Locale locale = new Locale("fa_IR");
        overrideConfiguration.setLocale(locale);
        Context context  = createConfigurationContext(overrideConfiguration);
        Resources resources = context.getResources();

        setContentView(R.layout.activity_singup);
        init();
        database=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        BtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validdata()) {
                    String Email = TxtEmail.getText().toString().trim();
                    String Pass = TxtPass.getText().toString().trim();
                    String Name = TXTName.getText().toString().trim();
                    Users users = new Users(Name, Pass, Email);
                    auth.createUserWithEmailAndPassword(Email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                database.collection("users")
                                        .document().set(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        startActivity(new Intent(SingupActivity.this, LoginActivity.class));
                                    }
                                });
                                ShowAlert("حساب ساخته شد");
                            } else {
                                if(task.getException().getLocalizedMessage().contains("403")){
                                   ShowAlert("از روشن بودن فیلتر شکن مطمئن شوید (:");
                                }
                                else {
                                    ShowAlert(task.getException().getLocalizedMessage());
                                }
                            }
                        }
                    });
                    ShowAlert("لطفا کمی صبر کنید");
                }
            }
        });
    }
    private void ShowAlert(String s) {
        Alerter.create(this)
                .setTitle("ساخت حساب کابری")
                .setText(s)
                .setIcon(R.drawable.ic_prof)
                .setBackgroundColorRes(R.color.purple_700)
                .setDuration(2000)
                .enableSwipeToDismiss() //seems to not work well with OnClickListener
                .show();
    }

    private boolean validdata() {
        if(TxtEmail.getText().toString().trim().isEmpty() || TxtPass.getText().toString().trim().isEmpty() || TXTName.getText().toString().trim().isEmpty()){
            ShowAlert("لطفا فیلد ها را کامل پر کنید");
            return false;
        }
        else {
            return true;
        }
    }

    private void init() {
       BtnSignup=findViewById(R.id.BtnSign);
       TXTName=findViewById(R.id.TXTName);
       TxtEmail=findViewById(R.id.TxtEmail);
       TxtPass=findViewById(R.id.TxtPass);
    }
    private void SetLanguage(){
        String languageToLoad  = "fa";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }
}
