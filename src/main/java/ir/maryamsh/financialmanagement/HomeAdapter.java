package ir.maryamsh.financialmanagement;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
            if(newTransactions.get(position).getType().contains("د")){
                holder.img_home_items.setBackgroundResource(R.drawable.earning);
                holder.more_home.setBackgroundResource(R.drawable.earning);
            }
            else{
                holder.img_home_items.setBackgroundResource(R.drawable.expenditures);
                holder.more_home.setBackgroundResource(R.drawable.expenditures);
            }
            NewTransaction transaction = newTransactions.get(position);
            holder.txttitle.setText(transaction.getType());
            holder.txtdes.setText(transaction.getDes());
            holder.txtdate.setText(transaction.getDate());
            holder.more_home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, MoreActivity.class);
                    String strName = null;
                    i.putExtra("id", newTransactions.get(position).getId());
                    i.putExtra("price", newTransactions.get(position).getPrice());
                    i.putExtra("type", newTransactions.get(position).getType());
                    i.putExtra("des", newTransactions.get(position).getDes());
                    i.putExtra("name",newTransactions.get(position).getName());
                    i.putExtra("date",newTransactions.get(position).getDate());
                    context.startActivity(i);
                }
            });
        }
        @Override
        public int getItemCount() {
            return newTransactions.size();
        }
        public class HomeAdapterholder extends RecyclerView.ViewHolder {
            TextView txttitle,txtdes,txtdate;
            LinearLayout more_home;
            ImageView img_home_items;

            public HomeAdapterholder(View itemView) {
                super(itemView);
                img_home_items=itemView.findViewById(R.id.img_home_items);
                txtdes=itemView.findViewById(R.id.deshome);
                txttitle = itemView.findViewById(R.id.titlehome);
                more_home=itemView.findViewById(R.id.more_home);
                txtdate=itemView.findViewById(R.id.txtdate);
            }
        }
    }