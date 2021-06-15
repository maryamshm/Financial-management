package ir.maryamsh.financialmanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
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
        int earn=0,co=0;
         SharedPreferences shPref;
        ArrayList<NewTransaction> newTransactions;
        public HomeAdapter(Context context, ArrayList<NewTransaction> newTransactions) {
            this.context = context;
            this.newTransactions = newTransactions;
            shPref = context.getSharedPreferences("shPref", context.MODE_PRIVATE);
        }
        @Override
        public HomeAdapterholder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.home_items, null);
            return new HomeAdapterholder(view);
        }
        @Override
        public void onBindViewHolder(HomeAdapterholder holder, int position) {
            String type="درآمد";
            if(newTransactions.get(position).getType().contains("1")){
                holder.img_home_items.setBackgroundResource(R.drawable.earning);
                holder.more_home.setBackgroundResource(R.drawable.earning);
                 type="درآمد";
            }
            else{
                holder.img_home_items.setBackgroundResource(R.drawable.expenditures);
                holder.more_home.setBackgroundResource(R.drawable.expenditures);
                 type="هزینه";
            }
            NewTransaction transaction = newTransactions.get(position);
            holder.txttitle.setText(type);
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
            if (newTransactions.get(position).getType().contains("1")) {
                earn += Integer.parseInt(newTransactions.get(position).getPrice());
                Log.i("tag", "daramad" + newTransactions);
            } else if(newTransactions.get(position).getType().contains("0")) {
                co += Integer.parseInt(newTransactions.get(position).getPrice());
                Log.i("tag", "hasineh" + co);
            }
            SharedPreferences.Editor editor = shPref.edit();
            editor.putString("co",String.valueOf(co));
            editor.putString("earn",String.valueOf(earn));
            editor.commit();

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