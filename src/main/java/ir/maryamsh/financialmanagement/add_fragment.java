package ir.maryamsh.financialmanagement;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tapadoo.alerter.Alerter;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Calendar;
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
    TextView tvtdate;
    Boolean ischange=false;
    int Position=0;
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
        tvtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDateOicker();
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Position=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        AddDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (ischange) {
                    if (TxtDes.getText().toString().isEmpty() || TxtPrice.getText().toString().isEmpty()) {
                        ShowAlert("لطفا اطلاعات را تکمیل کنید");
                    } else {
                        InsertData();
                        AddDate.setBackgroundResource(R.drawable.disablebtn);
                    }
                }
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
                else{
                    ischange = true;
                    AddDate.setBackgroundResource(R.drawable.button_style);
                }
            }
        });
        TxtDes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ischange = true;
                AddDate.setBackgroundResource(R.drawable.button_style);
            }
        });

        return view;
    }

    private void ShowDateOicker() {
        DialogFragment datePicker = new DatePickerFragment(context);
        datePicker.show(getActivity().getSupportFragmentManager(), "date picker");
    }

    private void setview() {
        tvtdate=view.findViewById(R.id.tvdate);
        database=FirebaseFirestore.getInstance();
        TxtDes=view.findViewById(R.id.txtdes);
        TxtPrice=view.findViewById(R.id.txtprice);
        AddDate=view.findViewById(R.id.add_data);
        spinner=(Spinner)view.findViewById(R.id.spinnertype);
        List<String> list = new ArrayList<String>();
        list.add("هزینه");
        list.add("درامد");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(R.layout.spinner);
        spinner.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void InsertData() {
        String email=shPref.getString("email", null);
        String date=tvtdate.getText().toString().trim();
        String id = database.collection("transaction").document().getId();
        newTransaction=new NewTransaction(email,TxtPrice.getText().toString().trim(),date,TxtDes.getText().toString().trim(),String.valueOf(Position),id);
        database.collection("transaction")
                .document().set(newTransaction).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    ShowAlert("اطلاعات اضافه شد (:");
                    TxtDes.setText("");
                    TxtPrice.setText("");
                    TextView tvtdate=view.findViewById(R.id.tvdate);
                    tvtdate.setText("");
                }
                else{
                    ShowAlert(task.getException().getLocalizedMessage());
                }
            }
        });
    }
    private void ShowAlert(String s) {
        Alerter.create((Activity) context)
                .setTitle("اضافه کردن داده")
                .setText(s)
                .setIcon(R.drawable.ic_list)
                .setBackgroundColorRes(R.color.toastbg)
                .setDuration(2000)
                .enableSwipeToDismiss() //seems to not work well with OnClickListener
                .setTitleTypeface(Typeface.createFromAsset(context.getAssets(), "kalameh_regular.ttf"))
                .setTextTypeface(Typeface.createFromAsset(context.getAssets(), "kalameh_regular.ttf"))
                .show();
    }

}
