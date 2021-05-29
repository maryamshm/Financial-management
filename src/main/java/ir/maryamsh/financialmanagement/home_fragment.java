package ir.maryamsh.financialmanagement;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class home_fragment extends Fragment {
    View view;
    Context context;
    RecyclerView recyclerView;
    FirebaseFirestore database;
    ArrayList<NewTransaction> transactionList=new ArrayList<>();
    public home_fragment(Context context){
        this.context=context;
    }
    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.home_fragment,container,false);
        setview();
        return view;
    }
    private void setview() {
        SharedPreferences shPref = context.getSharedPreferences("shPref", MODE_PRIVATE);
        String email=shPref.getString("email", "emial");
        recyclerView=view.findViewById(R.id.recyclerhome);
        database=FirebaseFirestore.getInstance();
        database.collection("transaction")
                .whereEqualTo("name", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        transactionList.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NewTransaction transaction=document.toObject(NewTransaction.class);
                                String name=transaction.getName(),
                                        price=transaction.getPrice(),
                                        date=transaction.getDate(),
                                        des=transaction.getDes(),
                                        type=transaction.getType(),
                                        id=String.valueOf(document.getId());
                                transactionList.add(new NewTransaction(name,price,date,des,type,id));
                            }
                        setrecycleritems(transactionList);
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    private void setrecycleritems(ArrayList<NewTransaction> transactionList) {
        HomeAdapter adapter = new HomeAdapter(context,transactionList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}