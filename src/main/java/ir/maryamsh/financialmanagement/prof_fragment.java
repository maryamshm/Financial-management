package ir.maryamsh.financialmanagement;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class prof_fragment  extends Fragment {
    View view;
    Button BtnExt;
    ImageView ImgEdit;
    EditText TxtEmail,TxtName,TxtPass;
    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.prof_fragment,container,false);
        setview();
        return view;
    }

    private void setview() {
        ImgEdit=view.findViewById(R.id.imgedit);
        TxtName=view.findViewById(R.id.txtName_);
        TxtPass=view.findViewById(R.id.txtPass_);
        TxtEmail=view.findViewById(R.id.txtemail_);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
