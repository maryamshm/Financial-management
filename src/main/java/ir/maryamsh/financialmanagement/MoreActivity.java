package ir.maryamsh.financialmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MoreActivity extends AppCompatActivity {
    FirebaseFirestore database;
    Button BtnDel,BtnUpdate;
    EditText TxtDes,TxtPrice;
    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemsmore_layout);
        init();
        setvalue();
        BtnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteData();
            }
        });
        BtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateData();
            }
        });
    }

    private void UpdateData() {
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        Map<String, Object> data = new HashMap<>();
        data.put("price",TxtPrice.getText().toString());
        data.put("des", TxtDes.getText().toString());
        data.put("name", extras.getString("name"));
        data.put("type",extras.getString("type"));
        data.put("date",extras.getString("date"));
        rootRef.collection("transaction").document(extras.getString("id")).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(MoreActivity.this, "done", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MoreActivity.this,MainActivity.class));
            }
        });

    }

    private void DeleteData() {
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection("transaction").document(extras.getString("id")).delete();
        startActivity(new Intent(MoreActivity.this,MainActivity.class));
    }
    private void setvalue() {
        extras = getIntent().getExtras();
        String type= extras.getString("type"),
        des=extras.getString("des"),
        price=extras.getString("price");
        TxtPrice.setText(price);
        TxtDes.setText(des);
    }

    private void init() {
       BtnDel=findViewById(R.id.delete_more);
       BtnUpdate=findViewById(R.id.update_more);
       TxtDes=findViewById(R.id.txtdes_more);
       TxtPrice=findViewById(R.id.txtprice_more);
    }
}