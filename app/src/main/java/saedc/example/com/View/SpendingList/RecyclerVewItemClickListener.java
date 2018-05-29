package saedc.example.com.View.SpendingList;

import saedc.example.com.Model.Entity.RawSpending;

/**
 * Created by Volkan Şahin on 1.09.2017.
 */

public interface RecyclerVewItemClickListener {
    void onItemClick(RawSpending clickedSpending);
    void onItemLongClick(int longClickedSpendingId);
}
