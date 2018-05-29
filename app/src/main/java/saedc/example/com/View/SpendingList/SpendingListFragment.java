package saedc.example.com.View.SpendingList;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import saedc.example.com.Model.Entity.RawSpending;
import saedc.example.com.Model.Pojo.Spending;
import saedc.example.com.R;
import saedc.example.com.View.AddAndEditSpending.AddAndEditSpendingFragment;
import saedc.example.com.View.MainActivity;

//this class connecting database withe Spending List in Home Page

public class SpendingListFragment extends Fragment implements RecyclerVewItemClickListener,LifecycleOwner {
    ArrayList<Spending> spendingList = new ArrayList<>();
    SpendingListViewModel viewModel;
    SpendingRecyclerViewAdapter adapter;
    View view;

    @BindView(R.id.spending_recyclerview)
    RecyclerView spendingRecyclerView;
    @BindView(R.id.textView4)
    TextView textView4;


    private LifecycleRegistry registry=new LifecycleRegistry(this);
    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return (registry);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        setRetainInstance(true);
    }

    @Override
    public void onStart() {
        super.onStart();

        registry.handleLifecycleEvent(Lifecycle.Event.ON_START);
    }

    @Override
    public void onResume() {
        super.onResume();

        registry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
    }

    @Override
    public void onPause() {
        super.onPause();

        registry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
    }

    @Override
    public void onStop() {
        super.onStop();

        registry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        registry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    }

    public static SpendingListFragment newInstance() {

        return new SpendingListFragment();
    }

    Calendar enD = Calendar.getInstance();
    Calendar starT = Calendar.getInstance();




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_spending_list, container, false);
        ButterKnife.bind(this, view);






        adapter = new SpendingRecyclerViewAdapter(getActivity(), spendingList, this);
        spendingRecyclerView.setAdapter(adapter);
        spendingRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(SpendingListViewModel.class);
        starT.set(Calendar.DATE, starT.getActualMinimum(Calendar.DAY_OF_MONTH));

        enD.set(Calendar.DATE, enD.getActualMaximum(Calendar.DAY_OF_MONTH));

//        enD.set(Calendar.HOUR_OF_DAY, 11);
//        enD.set(Calendar.MINUTE, 59);
//        enD.set(Calendar.SECOND, 59);
//
//
//        starT.set(Calendar.HOUR_OF_DAY, 0);
//        starT.set(Calendar.MINUTE, 0);
//        starT.set(Calendar.SECOND, 0);
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


        subscribeSpendings(starT.getTime(),enD.getTime());
    }

    private void subscribeSpendings(Date start ,Date end) {
        viewModel.getSpendings(start,end).observe(this, new Observer<List<Spending>>() {
            @Override
            public void onChanged(final List<Spending> spendings) {
                adapter.updateItems(spendings);
            }
        });
    }

    @Override
    public void onItemClick(RawSpending clickedSpending) {
        ((MainActivity) getActivity()).showFragment(AddAndEditSpendingFragment.newInstance(clickedSpending));
    }

// delete item whene user click on the item for long time
    @Override
    public void onItemLongClick(final int longClickedSpendingId) {
        AlertDialog alertbox = new AlertDialog.Builder(getActivity())
                .setMessage("هل انت متاكد من الحذف؟")
                .setPositiveButton("نعم", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {



                        viewModel.deleteSpending(longClickedSpendingId);

                    }
                })
                .setNegativeButton("لا", new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();

    }



}
