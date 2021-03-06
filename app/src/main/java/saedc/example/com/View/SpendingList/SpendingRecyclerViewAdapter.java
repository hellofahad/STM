package saedc.example.com.View.SpendingList;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import saedc.example.com.Model.Pojo.Spending;
import saedc.example.com.R;
import saedc.example.com.View.SpendingList.*;
import saedc.example.com.View.SpendingList.RecyclerVewItemClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;


public class SpendingRecyclerViewAdapter extends RecyclerView.Adapter<saedc.example.com.View.SpendingList.SpendingRecyclerViewAdapter.MyViewHolder> {
    String[]  stringGroups;
    private List<Spending> spendings;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); //you can add dd/MM/yyyy for date
    private  LayoutInflater layoutInflater;
    RecyclerVewItemClickListener recyclerVewItemClickListener;
    private int lastPosition = -1;
    Context context;

    public SpendingRecyclerViewAdapter(Context context, ArrayList<Spending> spendings, RecyclerVewItemClickListener listener) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.spendings = spendings;
        this.recyclerVewItemClickListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = layoutInflater.inflate(R.layout.item_spending_list_row, viewGroup, false);
        Log.d(TAG, "*** onCreateViewHolder: created");
        MyViewHolder viewHolder = new MyViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        Spending s = spendings.get(position);
        Double quantity = s.getRawSpending().getPrice();
        String description = s.getRawSpending().getDescription();
        Date date = s.getRawSpending().getDate();
        String group = s.getGroupName();


        String quantityWithCurrency = context.getString(R.string.turkish_lira_symbol) + String.valueOf(quantity);
        viewHolder.getTxtQuantity().setText(quantityWithCurrency);
        viewHolder.getTxtDescription().setText(description);
        viewHolder.getTxtDate().setText(dateFormat.format(date));
        stringGroups = context.getResources().getStringArray(R.array.groups);
        switch(group) {
            case "Food":

                viewHolder.getTxtSpendingGroup().setText(stringGroups[1]);

                break;
            case "Bills":
                viewHolder.getTxtSpendingGroup().setText(stringGroups[2]);

                break;
            case "Occasions":
                viewHolder.getTxtSpendingGroup().setText(stringGroups[3]);

                break;
            case "Premium":
                viewHolder.getTxtSpendingGroup().setText(stringGroups[4]);

                break;
            case "kids":

                viewHolder.getTxtSpendingGroup().setText(stringGroups[5]);

                break;
            case "travel":
                viewHolder.getTxtSpendingGroup().setText(stringGroups[6]);

                break;
            case "Transportation":
                viewHolder.getTxtSpendingGroup().setText(stringGroups[7]);

                break;
            case "Other":
                viewHolder.getTxtSpendingGroup().setText(stringGroups[8]);

                break;
            case "Shopping":

                viewHolder.getTxtSpendingGroup().setText(stringGroups[9]);

                break;
            case "debt":
                viewHolder.getTxtSpendingGroup().setText(stringGroups[10]);

                break;
            case "healthcare":
                viewHolder.getTxtSpendingGroup().setText(stringGroups[11]);

                break;
            case "Fixes":
                viewHolder.getTxtSpendingGroup().setText(stringGroups[12]);
                break;

            default:

        }


        setAnimation(viewHolder.itemView, position);

        Log.d(TAG, "*** onBindViewHolder: binded");
    }

    @Override
    public int getItemCount() {
        return spendings.size();
    }

    public void updateItems(List<Spending> spendings){
        final SpendingDiffCallback diffCallback = new SpendingDiffCallback(this.spendings, spendings);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.spendings.clear();
        this.spendings.addAll(spendings);
        diffResult.dispatchUpdatesTo(this);
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation slide_up = AnimationUtils.loadAnimation(context, R.anim.slide_up);
            viewToAnimate.startAnimation(slide_up);
            lastPosition = position;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {

        @BindView(R.id.txtQuantity)
        TextView txtQuantity;

        @BindView(R.id.txtDescription)
        TextView txtDescription;

        @BindView(R.id.txtSpendingGroup)
        TextView txtSpendingGroup;

        @BindView(R.id.txtDate)
        TextView txtDate;

        public MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
        }

        public TextView getTxtDescription() {
            return txtDescription;
        }

        public TextView getTxtSpendingGroup() {
            return txtSpendingGroup;
        }

        public TextView getTxtDate() {
            return txtDate;
        }

        public TextView getTxtQuantity() {
            return txtQuantity;
        }

        @Override
        public boolean onLongClick(View view) {
            int position = getAdapterPosition();
            if(position != -1){
                recyclerVewItemClickListener.onItemLongClick(spendings.get(position).getRawSpending().getId());
            }
            return false;
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if(position != -1){
                recyclerVewItemClickListener.onItemClick(spendings.get(position).getRawSpending());
            }
        }
    }
}
