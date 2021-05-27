package ir.maryamsh.financialmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class SingupActivity extends AppCompatActivity {
    Button BtnSignup;
    FirebaseFirestore database;
    TextView TxtEmail,TXTName,TxtPass;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
        init();
        database=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        BtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email=TxtEmail.getText().toString().trim();
                String Pass=TxtPass.getText().toString().trim();
                String Name=TXTName.getText().toString().trim();
                Users users=new Users(Name,Pass,Email);
                if(validdata(Name,Email,Pass)){
                    auth.createUserWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                database.collection("users")
                                        .document().set(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        startActivity(new Intent(SingupActivity.this, LoginActivity.class));
                                    }
                                });
                                Toast.makeText(SingupActivity.this, "Account is Created!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(SingupActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }

    private boolean validdata(String name, String email, String pass) {
          if(!(name.matches("/[a-zA-Z]{4,}/"))){
              Toast.makeText(SingupActivity.this, "name is invalid", Toast.LENGTH_SHORT).show();
              return false;
          }
        return true;
    }

    private void init() {
       BtnSignup=findViewById(R.id.BtnSign);
       TXTName=findViewById(R.id.TXTName);
       TxtEmail=findViewById(R.id.TxtEmail);
       TxtPass=findViewById(R.id.TxtPass);
    }
}
