package ir.maryamsh.financialmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.tapadoo.alerter.Alerter;

import java.util.HashMap;
import java.util.Map;

public class MoreActivity extends AppCompatActivity {
    FirebaseFirestore database;
    Button BtnDel,BtnUpdate;
    TextView Txtype,TxtDate;
    EditText TxtDes,TxtPrice;
    Bundle extras;
    boolean ischange=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemsmore_layout);
        init();
        setvalue();

        TxtDes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               ischange=true;
               BtnUpdate.setBackgroundResource(R.drawable.button_style);
            }
        });
        TxtPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1 && s.toString().startsWith("0")) {
                    s.clear();
                }
                ischange=true;
                BtnUpdate.setBackgroundResource(R.drawable.button_style);
            }
        });
        BtnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteData();
            }
        });
        BtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ischange)
                UpdateData();
            }
        });
    }

    private void ShowAlert(String s,int id,int c) {
        Alerter.create((Activity) this)
                .setTitle("اطلاعات")
                .setText(s)
                .setIcon(id)
                .setIconColorFilter(0)
                .setBackgroundColorRes(c)
                .setDuration(2000)
                .setTitleTypeface(Typeface.createFromAsset(getAssets(), "kalameh_regular.ttf"))
                .setTextTypeface(Typeface.createFromAsset(getAssets(), "kalameh_regular.ttf"))
                .enableSwipeToDismiss() //seems to not work well with OnClickListener
                .show();
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
               ShowAlert("به روز رسانی شد",R.drawable.ic_list,R.color.green);
            }
        });

    }

    private void DeleteData() {
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection("transaction").document(extras.getString("id")).delete();
        ActivityCompat.finishAffinity(this);
        startActivity(new Intent(MoreActivity.this,MainActivity.class));
        MoreActivity.this.finish();
    }
    private void setvalue() {
        extras = getIntent().getExtras();
        TxtPrice.setText(extras.getString("price"));
        TxtDes.setText(extras.getString("des"));
        Txtype.setText(extras.getString("type"));
        TxtDate.setText(extras.getString("date"));
    }

    private void init() {
       TxtDate=findViewById(R.id.tvdate);
       Txtype=findViewById(R.id.tvtype);
       BtnDel=findViewById(R.id.delete_more);
       BtnUpdate=findViewById(R.id.update_more);
       TxtDes=findViewById(R.id.txtdes_more);
       TxtPrice=findViewById(R.id.txtprice_more);
    }
}