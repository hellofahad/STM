package saedc.example.com.View.ChartList;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import saedc.example.com.Model.Pojo.Spending;
import saedc.example.com.R;
import saedc.example.com.View.SpendingList.SpendingListViewModel;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class tabchart2 extends Fragment {

    @BindView(R.id.listView1)
    ListView lv;

    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM/yyyy"); //you can add dd/MM/yyyy for date
    ArrayList<Spending> spendingList = new ArrayList<>();
    chartViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.tabchart2, container, false);
        ButterKnife.bind(this, rootview);

        return rootview;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(chartViewModel.class);
        List<Spending> Spendingdata = viewModel.getMonths();
        ArrayList<BarData> list = new ArrayList<BarData>();

        for (int i=1;i<Spendingdata.size();i++) {
            if(i==1){
                list.add(subscribeSpendings(Spendingdata.get(0).getRawSpending().getDate()));
            }else {

                if (!dateFormat.format(Spendingdata.get(i).getRawSpending().getDate()).equals(dateFormat.format(Spendingdata.get(i-1).getRawSpending().getDate()))) {
                    list.add(subscribeSpendings(Spendingdata.get(i).getRawSpending().getDate()));
                }
            }



        }

        ChartDataAdapter cda = new ChartDataAdapter(getContext(), list);
        lv.setAdapter(cda);


    }


    private BarData subscribeSpendings(Date date) {
        List<Spending> Spendingdata = viewModel.getDate();


        ArrayList<Date> dates = new ArrayList<Date>();
        ArrayList<Double> quantity = new ArrayList<Double>();


        for (Spending group : Spendingdata) {

            dates.add(group.getRawSpending().getDate());
            quantity.add(group.getRawSpending().getPrice());

        }


        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (int i = 0; i < dates.size(); i++) {


            if (dateFormat.format(date).equals(dateFormat.format(dates.get(i)))) {

                double pr = quantity.get(i);

                entries.add(new BarEntry(i, (float) (pr)));

            }
        }

        BarDataSet d = new BarDataSet(entries, dateFormat.format(date));

        d.setColors(ColorTemplate.MATERIAL_COLORS);
        d.setBarShadowColor(Color.rgb(203, 203, 203));

        ArrayList<IBarDataSet> sets = new ArrayList<IBarDataSet>();
        sets.add(d);

        BarData cd = new BarData(sets);

        cd.setBarWidth(0.9f);
        return cd;


    }


    private class ChartDataAdapter extends ArrayAdapter<BarData> {

        public ChartDataAdapter(Context context, List<BarData> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            BarData data = getItem(position);

            ViewHolder holder = null;

            if (convertView == null) {

                holder = new ViewHolder();

                convertView = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_item_barchart, null);
                holder.chart = (BarChart) convertView.findViewById(R.id.chart);

                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            // apply styling

            data.setValueTextColor(Color.BLACK);

            holder.chart.getDescription().setEnabled(false);
            holder.chart.setDrawGridBackground(false);

            XAxis xAxis = holder.chart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//
//            xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"ivukb","ouhij","ufiguh"}));
//
            xAxis.setDrawGridLines(false);
//            xAxis.setGranularity(1f);
//            xAxis.setGranularityEnabled(true);

            YAxis leftAxis = holder.chart.getAxisLeft();

            leftAxis.setLabelCount(5, false);
            leftAxis.setSpaceTop(15f);

            YAxis rightAxis = holder.chart.getAxisRight();

            rightAxis.setLabelCount(5, false);
            rightAxis.setSpaceTop(15f);

            // set data
            holder.chart.setData(data);
            holder.chart.setFitBars(true);

            // do not forget to refresh the chart
//            holder.chart.invalidate();
            holder.chart.animateY(700);

            return convertView;
        }

        private class ViewHolder {

            BarChart chart;
        }
    }


}
