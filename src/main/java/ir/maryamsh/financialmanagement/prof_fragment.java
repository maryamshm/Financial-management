package ir.maryamsh.financialmanagement;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.security.keystore.StrongBoxUnavailableException;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.tapadoo.alerter.Alerter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;

public class prof_fragment  extends Fragment {
    View view;
    Button BtnExt;
    ImageView ImgEdit;
    Context context;
    Users user;
    Boolean ischange=false;
    EditText TxtEmail,TxtName,TxtPass;
    String email,CurDoc,CurEmail,CurPass,CurName,Curid;
    FirebaseFirestore database;
    ArrayList<Users> users=new ArrayList<>();
    SharedPreferences shPref;
    public prof_fragment(Context context){
        this.context=context;
    }
    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.prof_fragment,container,false);
        setview();
        TxtPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                 ischange=true;
            }
        });
        TxtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ischange=true;
            }
        });
        TxtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ischange=true;
            }
        });

        ImgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ImgEdit.getTag() == "0") {
                    TxtName.setEnabled(true);
                    ShowAlert("حالت ویرایش نام فعال شد (:",R.drawable.ic_edit,R.color.green);
                    ImgEdit.setImageResource(R.drawable.ic_done);
                    ImgEdit.setTag("1");
                }
                else{
                        UpdateData();
                        ImgEdit.setImageResource(R.drawable.ic_edit);
                        TxtName.setEnabled(false);
                        TxtPass.setEnabled(false);
                        TxtEmail.setEnabled(false);
                        ImgEdit.setTag("0");
                    }
            }
        });

        BtnExt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = context.getSharedPreferences("shPref", MODE_PRIVATE);
                settings.edit().clear().commit();
                Intent intent = new Intent(context, AccountActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                ((Activity)context).finish();
            }
        });
        return view;
    }

    private void SetVal() {
        TxtName.setText(CurName);
        TxtEmail.setText(CurEmail);
        TxtPass.setText(CurPass);
        ischange=false;
    }

    private void UpdateData() {
            if (ischange && validata()){
                FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
                Map<String, Object> data = new HashMap<>();
                data.put("email", TxtEmail.getText().toString());
                data.put("name", TxtName.getText().toString());
                data.put("pass", TxtPass.getText().toString());
                rootRef.collection("users").document(shPref.getString("curid", "email")).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        ShowAlert("به روز رسانی شد", R.drawable.ic_list, R.color.green);
                        SharedPreferences.Editor editor = shPref.edit();
                        editor.putString("email", TxtEmail.getText().toString().trim());
                        editor.commit();
                    }
                });
            }
        }

    private boolean validata() {
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(TxtEmail.getText().toString().trim());

        final String PASS_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d$@$!%*#?&]{6,}$";
        Pattern patternpass = Pattern.compile(PASS_PATTERN);
        Matcher matcherpass = patternpass.matcher(TxtEmail.getText().toString().trim());

        if(!(matcher.matches())){
            ShowAlert("ایمیل معتبر نیست",R.drawable.ic_prof,R.color.red);
            return false;
        }
       else if(TxtEmail.getText().toString().trim().isEmpty() || TxtPass.getText().toString().trim().isEmpty() || TxtName.getText().toString().trim().isEmpty()){
            ShowAlert("لطفا فیلد ها را کامل پر کنید",R.drawable.ic_prof,R.color.red);
            return false;
        }

        else {
            return true;
        }
    }

    private void GetCurDoc(String email) {
        database=FirebaseFirestore.getInstance();
        database.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                user=document.toObject(Users.class);
                                CurEmail=user.getEmail();
                                CurName=user.getName();
                                CurPass=user.getPass();
                                SharedPreferences.Editor editor = shPref.edit();
                                editor.putString("curid", document.getId());
                                editor.commit();
                                users.add(new Users(CurName,CurPass,CurEmail));
                            }
                            SetVal();
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setview() {
        shPref = context.getSharedPreferences("shPref", MODE_PRIVATE);
        email=shPref.getString("email", "email");
        GetCurDoc(email);
        ImgEdit=view.findViewById(R.id.imgedit);
        ImgEdit.setTag("0");
        TxtName=view.findViewById(R.id.txtName_);
        TxtPass=view.findViewById(R.id.txtPass_);
        TxtEmail=view.findViewById(R.id.txtemail_);
        BtnExt=view.findViewById(R.id.btnexit);
    }

    private void ShowAlert(String s,int ic,int color) {
        Alerter.create((Activity) context)
                .setTitle("ساخت حساب کابری")
                .setText(s)
                .setTitleTypeface(Typeface.createFromAsset(context.getAssets(), "kalameh_regular.ttf"))
                .setTextTypeface(Typeface.createFromAsset(context.getAssets(), "kalameh_regular.ttf"))
                .setIcon(ic)
                .setBackgroundColorRes(color)
                .setDuration(1000)
                .enableSwipeToDismiss()
                .show();
    }
}

