package saedc.example.com.View.ChartList;

import android.app.FragmentManager;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import saedc.example.com.DetilaChart;
import saedc.example.com.Model.Pojo.PiechartPojo;
import saedc.example.com.R;


public class tabchart3 extends Fragment implements LifecycleOwner, DatePickerDialog.OnDateSetListener {

    @BindView(R.id.barch2)
    PieChart pieChart;

    chartViewModel viewModel;
    String group_name;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.tabchart3, container, false);
        ButterKnife.bind(this, rootview);

        return rootview;
    }
    Calendar enD = Calendar.getInstance();
    Calendar starT = Calendar.getInstance();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(chartViewModel.class);
        starT.set(Calendar.DATE, starT.getActualMinimum(Calendar.DAY_OF_MONTH));
        enD.set(Calendar.DATE, enD.getActualMaximum(Calendar.DAY_OF_MONTH));

        subscribeTotalQuantity(starT.getTime(), enD.getTime());

    }


    private void subscribeTotalQuantity(Date star,Date end) {
        viewModel.getTotalSpendingForPieChart(star,end).observe(this, new Observer<Double>() {
            @Override
            public void onChanged(final Double quantity) {
                List<PiechartPojo> piechart = viewModel.Pichartcategory(star, end);
                if (quantity != null) {

                        showTotalQuantityInUi(quantity,piechart);



                }else Toast.makeText(getActivity(), "لايوجد", Toast.LENGTH_SHORT).show();

            }

        });
        pieChart.invalidate();
    }



    private void showTotalQuantityInUi(Double quantity ,List<PiechartPojo> piechart) {
        final List<PieEntry> entries = new ArrayList<>();
        String[] stringGroups = getActivity().getResources().getStringArray(R.array.groups);

        if (quantity != null) {
            for (PiechartPojo Piechart : piechart) {
                if (Piechart.getCategoryTotal() != null) {
                    double Percentage = ((Piechart.getCategoryTotal() * 100.0) / quantity);
                    switch (Piechart.getCategoryName()) {
                        case "Food":
                            group_name = stringGroups[1];
                            break;
                        case "Bills":
                            group_name = stringGroups[2];
                            break;
                        case "Occasions":
                            group_name = stringGroups[3];
                            break;
                        case "Premium":
                            group_name = stringGroups[4];
                            break;
                        case "kids":
                            group_name = stringGroups[5];
                            break;
                        case "travel":
                            group_name = stringGroups[6];
                            break;
                        case "Transportation":
                            group_name = stringGroups[7];
                            break;
                        case "Other":
                            group_name = stringGroups[8];
                            break;
                        case "Shopping":
                            group_name = stringGroups[9];
                            break;
                        case "debt":
                            group_name = stringGroups[10];
                            break;
                        case "healthcare":
                            group_name = stringGroups[11];
                            break;
                        case "Fixes":
                            group_name = stringGroups[12];
                            break;
                        default:

                    }
                    entries.add(new PieEntry((float) Percentage, group_name));
                }
            }

        }

        customization(entries);

    }

public void customization(List<PieEntry> entries){

    PieDataSet set = new PieDataSet(entries, "");
    PieData data = new PieData(set);
    set.setColors(ColorTemplate.VORDIPLOM_COLORS);
    set.setSelectionShift(15);
    data.setValueTextSize(14f);
    data.setValueFormatter(new PercentFormatter());

    pieChart.setData(data);
    pieChart.animateY(1500);
    pieChart.setCenterText("100%");
    pieChart.setCenterTextSize(45);
    pieChart.setCenterTextColor(R.color.centertext);
    pieChart.setEntryLabelColor(R.color.centertext);
    pieChart.setHoleRadius(50);
    pieChart.getDescription().setEnabled(false);
    pieChart.notifyDataSetChanged();
    pieChart.setTransparentCircleRadius(61f);
    pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {


        @Override
        public void onValueSelected(Entry e, Highlight h) {

            PieEntry pe = (PieEntry) e;
            Intent in = new Intent(getActivity(), DetilaChart.class);
            Bundle bundle = new Bundle();
            bundle.putString("Key1", pe.getLabel());
            in.putExtras(bundle);
            startActivity(in);
//                Alerter.create(getActivity())
//                        .setTitle(pe.getLabel())
//                        .setText("%" + Math.round(pe.getValue() * 10.0) / 10.0)
//                        .enableProgress(false)
//                        .setIcon(R.drawable.about)
//                        .setProgressColorRes(R.color.colorAccent)
//                        .setBackgroundColorRes(R.color.colorPrimaryDark)
//                        .show();
//                Toast.makeText(getContext(),h.toString(),Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected() {

        }
    });



}


    @OnClick(R.id.datebutton)
    public void onViewClicked() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setEndTitle("النهاية");
        dpd.setStartTitle("البداية");
        dpd.show(getActivity().getFragmentManager(), "tabchart3");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {

        Calendar enD = Calendar.getInstance();
        Calendar starT = Calendar.getInstance();


        starT.set(Calendar.YEAR, year);
        starT.set(Calendar.MONTH, monthOfYear);
        starT.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        enD.set(Calendar.YEAR, yearEnd);
        enD.set(Calendar.MONTH, monthOfYearEnd);
        enD.set(Calendar.DAY_OF_MONTH, dayOfMonthEnd);

        subscribeTotalQuantity(starT.getTime(), enD.getTime());

    }
}