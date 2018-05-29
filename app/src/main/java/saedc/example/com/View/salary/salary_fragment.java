package saedc.example.com.View.salary;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import saedc.example.com.Model.Pojo.MaxSaving;
import saedc.example.com.Model.Pojo.Spending;
import saedc.example.com.R;
import saedc.example.com.View.TotalSpendingPrice.TotalSpendingPriceViewModel;


public class salary_fragment extends Fragment implements LifecycleOwner {

    TotalSpendingPriceViewModel viewModel;

    @BindView(R.id.salaryid)
    TextView Salary;

    @BindView(R.id.spindingsalary)
    TextView spindingsalary;

    @BindView(R.id.leftsalary)
    TextView leftsalary;

    @BindView(R.id.lastmonth)
    TextView lastmonth;

    @BindView(R.id.textView5)
    TextView totalgroubprice;

    @BindView(R.id.textView6)
    TextView totalgroubpercent;

    Double total;

    private LifecycleRegistry registry = new LifecycleRegistry(this);

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
        final SharedPreferences shrd = this.getActivity().getSharedPreferences("Data_User", Context.MODE_PRIVATE);

        viewModel = ViewModelProviders.of(this).get(TotalSpendingPriceViewModel.class);
        viewModel.totalSpendingQuantity.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(final Double quantity) {
                if (quantity != null) {
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());

                    int switchPr = 0;
                    try {
                        switchPr = Integer.parseInt(sharedPref.getString("Settings_salary", "0"));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    total = quantity;
                    int spinding_salary;
                    if (switchPr - quantity.intValue() >= 0) {
                        spinding_salary = switchPr - quantity.intValue();
                    } else {
                        spinding_salary = 0;
                    }

                    Salary.setText(String.valueOf(switchPr) + getActivity().getString(R.string.turkish_lira_symbol));
                    spindingsalary.setText(String.valueOf(quantity) + getActivity().getString(R.string.turkish_lira_symbol));
                    leftsalary.setText(String.valueOf(spinding_salary) + getActivity().getString(R.string.turkish_lira_symbol));

                } else {
                    total = 0.0;
                }
            }
        });
        SubscribeMostCompleteSaving();
        SubscribeSalaryAndSpinding();
    }

    public void SubscribeMostCompleteSaving() {
        viewModel.GetMaxgroub.observe(this, new Observer<List<Spending>>() {
            @Override
            public void onChanged(@Nullable List<Spending> spendings) {
                if (!(spendings.size() == 0)) {
                    lastmonth.setText(spendings.get(0).getGroupName());
                    switch (spendings.get(0).getGroupName()) {
                        case "Food":
                            lastmonth.setText("طعام");
                            break;
                        case "Bills":
                            lastmonth.setText("فواتير");
                            break;
                        case "Occasions":
                            lastmonth.setText("مناسبات");
                            break;
                        case "Premium":
                            lastmonth.setText("اقساط");
                            break;
                        case "kids":

                            lastmonth.setText("الاطفال");
                            break;
                        case "travel":
                            lastmonth.setText("سفر");
                            break;
                        case "Transportation":
                            lastmonth.setText("مواصلات");
                            break;
                        case "Other":
                            lastmonth.setText("اخرى");
                            break;
                        case "Shopping":

                            lastmonth.setText("تسوق");
                            break;
                        case "debt":
                            lastmonth.setText("ديون");
                            break;
                        case "healthcare":
                            lastmonth.setText("العناية الصحية");
                            break;
                        case "Fixes":
                            lastmonth.setText("الاصلاحات");
                            break;
                        default:
                    }
                } else {
                    lastmonth.setText("لايوجد");

                }

            }
        });

    }

    public void SubscribeSalaryAndSpinding() {
        viewModel.maxsaving().observe(this, new Observer<MaxSaving>() {
            @Override
            public void onChanged(@Nullable MaxSaving maxSaving) {
                if (maxSaving != null) {
                    lastmonth.setText(maxSaving.getSavename());
                    totalgroubpercent.setText(String.valueOf(maxSaving.getMax()) + "%");
                    totalgroubprice.setText(String.valueOf(maxSaving.getPrice()) + " " + getActivity().getString(R.string.turkish_lira_symbol));
                } else {
                    lastmonth.setText("لايوجد");
                    totalgroubpercent.setText("0.0%");
                    totalgroubprice.setText("0.0" + " " + getActivity().getString(R.string.turkish_lira_symbol));
                }
            }
        });

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.salary_fragment, container, false);
        ButterKnife.bind(this, v);
        return v;
    }


}
