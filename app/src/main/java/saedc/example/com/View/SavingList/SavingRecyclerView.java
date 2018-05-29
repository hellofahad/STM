package saedc.example.com.View.SavingList;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import saedc.example.com.Model.Entity.Saving;
import saedc.example.com.R;

/**
 * Created by saedc on 10/02/18.
 */
public class SavingRecyclerView extends RecyclerView.Adapter<SavingRecyclerView.MyViewHolder> {



    private List<Saving> savingslist;
    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d"); //you can add dd/MM/yyyy for date
    private LayoutInflater layoutInflater;
    Recyclerviewclick recyclerVewItemClickListener;
    private int lastPosition = -1;
    Context context;

    public SavingRecyclerView(Context context, ArrayList<Saving> spendings, Recyclerviewclick listener) {

        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.savingslist = spendings;
        this.recyclerVewItemClickListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = layoutInflater.inflate(R.layout.item_saving_list_row, viewGroup, false);

        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;

    }

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    //    calculate days
    public static long getDayCount(String start, String end) {
        long diff = -1;
        try {
            Date dateStart = simpleDateFormat.parse(start);
            Date dateEnd = simpleDateFormat.parse(end);

            //time is always 00:00:00 so rounding should help to ignore the missing hour when going from winter to summer time as well as the extra hour in the other direction
            diff = Math.round((dateEnd.getTime() - dateStart.getTime()) / (double) 86400000);
        } catch (Exception e) {
            //handle the exception according to your own situation
        }
        return diff;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Saving s = savingslist.get(position);
        String namesaving = s.getSavingname();
        Double quantity1 = s.getItem_price();
        Double quantity2 = s.getItem_saveing();

        Date date = s.getEnd_date();

        if (quantity2 <= quantity1) {
            double Percentage = ((quantity2 * 100.0) / quantity1);
            holder.getprogresbar().setProgress((int) Percentage);
        } else {
            holder.getprogresbar().setProgress(100);
        }
        holder.getTxtname().setText(namesaving);
        holder.getTxtPrice().setText(String.valueOf(quantity1) + " " + context.getString(R.string.turkish_lira_symbol));
        holder.getTxtprice_part().setText(context.getString(R.string.saving_price_amount) + " " + String.valueOf(quantity2) + " " + context.getString(R.string.turkish_lira_symbol));
        int b = holder.getprogresbar().getProgress();
        holder.getTxtEndDate().setText(dateFormat.format(date));
        Date now = new Date(System.currentTimeMillis());
        // calculate days
        if (now.before(date)) {
            holder.getTxtDayslift().setText(String.valueOf(Math.round(quantity1) / getDayCount(simpleDateFormat.format(now), simpleDateFormat.format(date))) + " Ryal Per Day");
        }

        if ( holder.getprogresbar().getProgress()==100) {
            holder.getimagebutton().setVisibility(View.VISIBLE);
        }

        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return savingslist.size();
    }

    public void updateItems(List<Saving> savings) {
        final SavingDiffCallback diffCallback = new SavingDiffCallback(this.savingslist, savings);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.savingslist.clear();
        this.savingslist.addAll(savings);
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

        @BindView(R.id.item_price)
        TextView price;

        @BindView(R.id.item_name)
        TextView name;

        @BindView(R.id.item_price_part)
        TextView price_part;

        @BindView(R.id.progressBar2)
        NumberProgressBar bar;

        @BindView(R.id.endDate)
        TextView EndDate;

        @BindView(R.id.dayslift)
        TextView Dayslift;

        @BindView(R.id.imageButton)
        ImageButton imageButton;
//        @BindView(R.id.switchico)
//        SwitchIconView switchico;

        public MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
        }

        public TextView getTxtPrice() {
            return price;
        }

        public ImageButton getimagebutton() {
            return imageButton;
        }

        public TextView getTxtname() {
            return name;
        }


        public NumberProgressBar getprogresbar() {
            return bar;
        }

        public TextView getTxtprice_part() {
            return price_part;
        }


        public TextView getTxtEndDate() {
            return EndDate;
        }

        public TextView getTxtDayslift() {
            return Dayslift;
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != -1) {
                recyclerVewItemClickListener.onItemClick(savingslist.get(position));
            }
        }

        @Override
        public boolean onLongClick(View view) {
            int position = getAdapterPosition();
            if (position != -1) {
                recyclerVewItemClickListener.onItemLongClick(savingslist.get(position).getId());
            }
            return false;
        }

    }


}

