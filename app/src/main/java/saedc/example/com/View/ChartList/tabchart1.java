package saedc.example.com.View.ChartList;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.github.mikephil.charting.charts.LineChart;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;

import saedc.example.com.Model.Pojo.LinechartPojo;
import saedc.example.com.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * barch1
 * Created by saedc on 24/01/18.
 */

public class tabchart1 extends Fragment {


    @BindView(R.id.barch1)
    LineChart lineChart;
final String Tag="chart";
    chartViewModel viewModel;
    String group_name;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM/yyyy");
    SimpleDateFormat Format = new SimpleDateFormat("dd");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.tabchart1, container, false);

        ButterKnife.bind(this, rootview);

        return rootview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(chartViewModel.class);
        List<LinechartPojo> LinechartDataSource = viewModel.linechartdata;

        showTotalQuantityInUi(LinechartDataSource);
    }


    private void showTotalQuantityInUi(List<LinechartPojo> LinechartDataSource) {

            final List<Entry> entries = new ArrayList<>();


            Calendar now = Calendar.getInstance();


            for (int i = 0; i < LinechartDataSource.size(); i++) {

                if (dateFormat.format(now.getTime()).equals(dateFormat.format(LinechartDataSource.get(i).getSpendDate()))) {
                    double Percentage = LinechartDataSource.get(i).getPrice();

                    entries.add(new Entry(i, (float) Percentage));

                }

            }

            if (entries.isEmpty()) {
                entries.add(new Entry(0, (float) 0));
            }


            lineChart.getAxisLeft().setDrawGridLines(false);
            lineChart.getXAxis().setDrawGridLines(false);
            lineChart.getDescription().setEnabled(false);

            LineDataSet set1 = new LineDataSet(entries, "");
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
//            set1.setDrawHighlightIndicators(true);
            set1.setHighLightColor(Color.BLACK);
            set1.setDrawIcons(false);

            set1.setColor(R.color.colorPrimary);
            set1.setCircleColor(R.color.colorAccent);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(15f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            LineData data = new LineData(set1);

            // set data
            if (set1 == null) {
                lineChart.clear();
                lineChart.invalidate();
            } else {
                lineChart.setData(data);


            }



    }
}
