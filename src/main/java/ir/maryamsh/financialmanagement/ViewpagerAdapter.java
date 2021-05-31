package ir.maryamsh.financialmanagement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import static java.security.AccessController.getContext;

public class ViewpagerAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    public int ImageArray [] ={R.drawable.img_intro1,R.drawable.img_intro2};
    public String TitleArray [] ={"عنوان ۱","عنوان ۲"};
    public String DesArray [] ={"توضیحات ۱","توضیحات ۲"};
    public int ColorArray [] ={R.drawable.intro1,R.drawable.intro2};

    public ViewpagerAdapter(Context context){
        this.context=context;
    }
    @Override
    public int getCount() {
        return TitleArray.length;
    }

    @Override
    public boolean isViewFromObject(View view,Object object) {
        return (view==object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position,Object object) {
        container.removeView((RelativeLayout)object);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.intro_items,container,false);
        RelativeLayout layout=view.findViewById(R.id.layputintro);
        ImageView img=view.findViewById(R.id.ImgIntro);
        TextView Title=view.findViewById(R.id.TitleIntro);
        TextView Des=view.findViewById(R.id.DesIntro);
        RelativeLayout layoutL=view.findViewById(R.id.layputintro);
        Button btn1=view.findViewById(R.id.btn1),
                btn2=view.findViewById(R.id.btn2),
                btnstart=view.findViewById(R.id.btn_start);
        if(position==1){
            layoutL.setBackgroundResource(R.drawable.bg2);
            btn1.setVisibility(View.GONE);
            btn2.setVisibility(View.GONE);
            btnstart.setVisibility(View.VISIBLE);
        }
        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,AccountActivity.class));
                ((Activity)context).finish();
            }
        });
        RelativeLayout LyDot=view.findViewById(R.id.LyDot);
        LyDot.setBackgroundResource(ColorArray[position]);
          img.setImageResource(ImageArray[position]);
          Title.setText(TitleArray[position]);
          Des.setText(DesArray[position]);
          container.addView(view);
        return view;
    }
}
