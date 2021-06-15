package ir.maryamsh.financialmanagement;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.transition.MaterialSharedAxis;

import java.util.ArrayList;

public class report_fragement  extends Fragment {
    View view;
    Context context;
    SharedPreferences shPrefs;
    PieChart pieChart;
    public report_fragement(Context context){
        this.context=context;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.report_fragment,container,false);
        SharedPreferences shPref = context.getSharedPreferences("shPref", context.MODE_PRIVATE);
        String co=shPref.getString("co", "0");
        String earn=shPref.getString("earn", "0");
        pieChart=view.findViewById(R.id.piechart);
        ArrayList<PieEntry> pieEntries=new ArrayList<>();
        pieEntries.add(new PieEntry(Integer.parseInt(co),"هزینه"));
        pieEntries.add(new PieEntry(Integer.parseInt(earn),"درآمد"));
        pieEntries.add(new PieEntry(Integer.parseInt(earn) - Integer.parseInt(co),"باقی مانده"));

        PieDataSet pieDataSet=new PieDataSet(pieEntries,"عنوان");

        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);
        pieDataSet.setValueTypeface(Typeface.createFromAsset(context.getAssets(), "kalameh_regular.ttf"));


        PieData pieData=new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("گزارش مالی");
        pieChart.setCenterTextTypeface(Typeface.createFromAsset(context.getAssets(), "kalameh_regular.ttf"));
        pieChart.animate();
        return view;
    }
}
