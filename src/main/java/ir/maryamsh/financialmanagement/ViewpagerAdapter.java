package ir.maryamsh.financialmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class ViewpagerAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    public int ImageArray [] ={R.drawable.img_intro1,R.drawable.img_intro2,R.drawable.img_intro3};
    public String TitleArray [] ={"عنوان ۱","عنوان ۲","عنوان ۳"};
    public String DesArray [] ={"توضیحات ۱","توضیحات ۲","توضیحات ۳"};
    public int ColorArray [] ={R.drawable.intro1,R.drawable.intro2,R.drawable.intro3};

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
        RelativeLayout LyDot=view.findViewById(R.id.LyDot);
//        LyDot.setBackground(ColorArray[position]);
          img.setImageResource(ImageArray[position]);
          Title.setText(TitleArray[position]);
          Des.setText(DesArray[position]);
          container.addView(view);
        return view;
    }
}
