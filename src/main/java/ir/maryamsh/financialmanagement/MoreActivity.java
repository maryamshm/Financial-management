package ir.maryamsh.financialmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MoreActivity extends AppCompatActivity {
    Button BtnDel,BtnUpdate;
    EditText TxtDes,TxtPrice;
    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemsmore_layout);
        init();
        setvalue();
        
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