package ir.maryamsh.financialmanagement;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class add_fragment extends Fragment {
    View view;
    Context context;
    Spinner spinner;
    EditText TxtDes,TxtPrice,TxtType;
    Button BtnAdd;
    FirebaseFirestore database;
    RelativeLayout AddDate;
    NewTransaction newTransaction;
    SharedPreferences shPref;
    public add_fragment(Context context){
        this.context=context;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.add_fragment,container,false);
        setview();
        shPref = context.getSharedPreferences("shPref", MODE_PRIVATE);
        AddDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                InsertData();
            }
        });
        return view;
    }
    private void setview() {
        database=FirebaseFirestore.getInstance();
        TxtDes=view.findViewById(R.id.txtdes);
        TxtPrice=view.findViewById(R.id.txtprice);
        AddDate=view.findViewById(R.id.add_data);
        spinner=(Spinner)view.findViewById(R.id.spinnertype);
        List<String> list = new ArrayList<String>();
        list.add("هزینه");
        list.add("درامد");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void InsertData() {
        String email=shPref.getString("email", null);
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Tehran"));
        DateTimeFormatter userFormatter
                = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
        String date=today.format(userFormatter);
        String id = database.collection("transaction").document().getId();
        newTransaction=new NewTransaction(email,TxtPrice.getText().toString().trim(),date,TxtDes.getText().toString().trim(),spinner.getSelectedItem().toString(),id);
        database.collection("transaction")
                .document().set(newTransaction).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
//                        startActivity(new Intent(SingupActivity.this, LoginActivity.class));
                if (task.isSuccessful()){
                    Toast.makeText(context, "yesss", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
