package saedc.example.com.View.TotalSpendingPrice;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import saedc.example.com.Model.Pojo.PiechartPojo;
import saedc.example.com.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
//this class for connecting database with header of home page

public class TotalSpendingPriceFragment extends Fragment implements LifecycleOwner {
    ArrayList<PiechartPojo> piechart = new ArrayList<>();
    TotalSpendingPriceViewModel viewModel;




    @BindView(R.id.recycl)
    RecyclerView recyclerView;

    @BindView(R.id.salaryid)
    TextView Salary;

    @BindView(R.id.spindingsalary)
    TextView spindingsalary;

    @BindView(R.id.leftsalary)
    TextView leftsalary;

    Double total;
    private static categoryRecyclerViewAdapter adapter;

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(TotalSpendingPriceViewModel.class);
        //update the list every time database chenge
        subscribecategory();
        //update the UI salary,spending,salary_after_spendings every time database chenge
        subscribeTotalprice();

        adapter= new categoryRecyclerViewAdapter(getActivity(), piechart ,viewModel.getTotalSpending);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_total_spending_quantity, container, false);
        ButterKnife.bind(this, v);

        return v;
    }


    private void subscribecategory() {
viewModel.CstegoryNameWithTotalList.observe(this, new Observer<List<PiechartPojo>>() {
    @Override
    public void onChanged(@Nullable List<PiechartPojo> piechartPojos) {

        adapter.updateItems(piechartPojos );
    }
});
    }

    private void subscribeTotalprice() {
        viewModel.totalSpendingQuantity.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(final Double price) {
                if (price != null) {

                    adapter.updateItems(price );
                    // call SharedPreferences to take the salary from settings
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());

                    int salary = 0;
                    try {
                        //put salary value in variable
                        salary = Integer.parseInt(sharedPref.getString("Settings_salary", "0"));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    total = price;
                    int spinding_salary;
                    if (price.intValue()<=salary) {
                        spinding_salary = salary - price.intValue();
                    } else {
                        spinding_salary=0;
                    }

                    Salary.setText(String.valueOf(salary) + getActivity().getString(R.string.turkish_lira_symbol));
                    spindingsalary.setText(String.valueOf(price) + getActivity().getString(R.string.turkish_lira_symbol));
                    leftsalary.setText(String.valueOf(spinding_salary) + getActivity().getString(R.string.turkish_lira_symbol));

                } else {

                    total = 0.0;
                }
            }
        });



    }

}
