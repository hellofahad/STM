package saedc.example.com.View.TotalSpendingPrice;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import saedc.example.com.Model.Pojo.PiechartPojo;
import saedc.example.com.R;
import saedc.example.com.View.SpendingList.RecyclerVewItemClickListener;

import static android.content.ContentValues.TAG;


public class categoryRecyclerViewAdapter extends RecyclerView.Adapter<categoryRecyclerViewAdapter.MyViewHolder> {
    String[] stringGroups;
    TotalSpendingPriceViewModel viewModel;
    Context mContext;
    Double totalprice;
    RecyclerVewItemClickListener recyclerVewItemClickListener;
    Context context;
    private ArrayList<PiechartPojo> dataSet;
    private LayoutInflater layoutInflater;
    private int lastPosition = -1;
    Double total;
    public categoryRecyclerViewAdapter(Context context, ArrayList<PiechartPojo> dataSet ,Double total) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.dataSet = dataSet;
        this.total = total;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = layoutInflater.inflate(R.layout.categorylistitem, viewGroup, false);
        Log.d(TAG, "*** onCreateViewHolder: created");
        MyViewHolder viewHolder = new MyViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        PiechartPojo s = dataSet.get(position);
        Double price = s.getCategoryTotal();
        String group = s.getCategoryName();


        String quantityWithCurrency = context.getString(R.string.turkish_lira_symbol) + String.valueOf(price);
        viewHolder.totaltext().setText(quantityWithCurrency);

        double Percentage;
        if (total==null) {
            Percentage = ((price * 100.0) / price);
        } else {
            Percentage = ((price * 100.0) / total);
        }

        int rsult = (int) Percentage;
        viewHolder.progressBar().setProgress(rsult);

//        viewHolder.progressBar().setReachedBarColor(R.color.alerter_default_success_background);

        stringGroups = context.getResources().getStringArray(R.array.groups);
        switch (group) {
            case "Food":

                viewHolder.groubnametext().setText(stringGroups[1]);

                break;
            case "Bills":
                viewHolder.groubnametext().setText(stringGroups[2]);

                break;
            case "Occasions":
                viewHolder.groubnametext().setText(stringGroups[3]);

                break;
            case "Premium":
                viewHolder.groubnametext().setText(stringGroups[4]);

                break;
            case "kids":

                viewHolder.groubnametext().setText(stringGroups[5]);

                break;
            case "travel":
                viewHolder.groubnametext().setText(stringGroups[6]);

                break;
            case "Transportation":
                viewHolder.groubnametext().setText(stringGroups[7]);

                break;
            case "Other":
                viewHolder.groubnametext().setText(stringGroups[8]);

                break;
            case "Shopping":

                viewHolder.groubnametext().setText(stringGroups[9]);

                break;
            case "debt":
                viewHolder.groubnametext().setText(stringGroups[10]);

                break;
            case "healthcare":
                viewHolder.groubnametext().setText(stringGroups[11]);

                break;
            case "Fixes":
                viewHolder.groubnametext().setText(stringGroups[12]);

                break;
            default:

        }


        setAnimation(viewHolder.itemView, position);

        Log.d(TAG, "*** onBindViewHolder: binded");
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void updateItems(List<PiechartPojo> dataSet ) {
        final CategoryDiffCallback diffCallback = new CategoryDiffCallback(this.dataSet, dataSet);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.dataSet.clear();
        this.dataSet.addAll(dataSet);
        diffResult.dispatchUpdatesTo(this);


    }
    public void updateItems(Double total) {
       this.total=total;
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation slide_up = AnimationUtils.loadAnimation(context, R.anim.slide_up);
            viewToAnimate.startAnimation(slide_up);
            lastPosition = position;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.price1)
        TextView totaltext;

        @BindView(R.id.price2)
        TextView groubnametext;

        @BindView(R.id.progressBar3)
        NumberProgressBar progressBar;


        public MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
        public TextView totaltext() {
            return totaltext;
        }

        public TextView groubnametext() {
            return groubnametext;
        }

        public NumberProgressBar progressBar() {
            return progressBar;
        }


    }
}
