package ir.maryamsh.financialmanagement;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeAdapterholder> {
        Context context;
        ArrayList<NewTransaction> newTransactions;
        public HomeAdapter(Context context, ArrayList<NewTransaction> newTransactions) {
            this.context = context;
            this.newTransactions = newTransactions;
        }
        @Override
        public HomeAdapterholder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.home_items, null);
            return new HomeAdapterholder(view);
        }
        @Override
        public void onBindViewHolder(HomeAdapterholder holder, int position) {
            NewTransaction model = newTransactions.get(position);
            holder.textView.setText(model.getType());
            holder.more_home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, MoreActivity.class);
                    String strName = null;
                    i.putExtra("id", newTransactions.get(position).getId());
                    i.putExtra("price", newTransactions.get(position).getPrice());
                    i.putExtra("type", newTransactions.get(position).getType());
                    i.putExtra("des", newTransactions.get(position).getDes());
                    context.startActivity(i);
                }
            });
        }
        @Override
        public int getItemCount() {
            return newTransactions.size();
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