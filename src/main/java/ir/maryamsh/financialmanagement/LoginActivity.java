package ir.maryamsh.financialmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    Button BtnLogin;
    EditText TxtEmail;
    EditText TxtPass;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ini();
        auth=FirebaseAuth.getInstance();
        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String email=TxtEmail.getText().toString().trim();
               String pass=TxtPass.getText().toString().trim();
               auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull  Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           startActivity(new Intent(LoginActivity.this, MainActivity.class));
                       }
                       else{
                           Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                       }
                   }
               });
            }
        });
    }

    private void ini() {
       BtnLogin=findViewById(R.id.ButtonLoginin);
       TxtEmail=findViewById(R.id.TxtEmaillogin);
       TxtPass=findViewById(R.id.TxtPasslogin);
    }
}