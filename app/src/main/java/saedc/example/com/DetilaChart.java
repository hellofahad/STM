package saedc.example.com;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import saedc.example.com.Model.Pojo.Spending;
import saedc.example.com.View.ChartList.chartViewModel;

public class DetilaChart extends AppCompatActivity {

    chartViewModel viewModel;
    @BindView(R.id.rsycler)
    RecyclerView rsycler;
    ChartRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detila_chart);

        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this).get(chartViewModel.class);
        Bundle bundle = getIntent().getExtras();
        String valueReceived1 = bundle.getString("Key1");
//       String[] stringGroups = getResources().getStringArray(R.array.groups);

        adapter = new ChartRecyclerViewAdapter(this, (ArrayList<Spending>) viewModel.ChartDetail(EnglishToArabic(valueReceived1)));
        rsycler.setAdapter(adapter);
        rsycler.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));


    }

    public String EnglishToArabic(String valueReceived1) {
        String groupname;

        switch (valueReceived1) {
            case "طعام":
                groupname = "Food";
                break;
            case "فواتير":
                groupname = "Bills";
                break;
            case "مناسبات":
                groupname = "Occasions";
                break;
            case "اقساط":
                groupname = "Premium";
                break;
            case "الاطفال":
                groupname = "kids";
                break;
            case "سفر":
                groupname = "travel";
                break;
            case "مواصلات":
                groupname = "Transportation";
                break;
            case "اخرى":
                groupname = "Other";
                break;
            case "تسوق":
                groupname = "Shopping";
                break;
            case "ديون":
                groupname = "debt";
                break;
            case "العناية الصحية":
                groupname = "healthcare";
                break;
            case "الاصلاحات":
                groupname = "Fixes";
                break;
            default:
                groupname = valueReceived1;

        }
        return groupname;
    }
}
