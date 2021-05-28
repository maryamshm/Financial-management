package ir.maryamsh.financialmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
    public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeAdapterholder> {
        Context context;
        ArrayList<NewTransaction> categoryModels;
        public HomeAdapter(Context context, ArrayList<NewTransaction> categoryModels) {
            this.context = context;
            this.categoryModels = categoryModels;
        }
        @Override
        public HomeAdapterholder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.home_items, null);
            return new HomeAdapterholder(view);
        }
        @Override
        public void onBindViewHolder(HomeAdapterholder holder, int position) {
            NewTransaction model = categoryModels.get(position);
            holder.textView.setText(model.getType());
            holder.more_home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        @Override
        public int getItemCount() {
            return categoryModels.size();
        }
        public class HomeAdapterholder extends RecyclerView.ViewHolder {
            TextView textView;
            LinearLayout more_home;
            public HomeAdapterholder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.titlehome);
                more_home=itemView.findViewById(R.id.more_home);
            }
        }
    }