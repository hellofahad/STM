package saedc.example.com.View.ChartList;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import saedc.example.com.Model.Pojo.LinechartPojo;
import saedc.example.com.Model.Pojo.PiechartPojo;
import saedc.example.com.Model.Pojo.Spending;
import saedc.example.com.Model.Repository.SpendingRepository;
import saedc.example.com.MoneycimApp;

/**
 * Created by saedc on 09/02/18.
 */

public class chartViewModel extends AndroidViewModel {
    @Inject
    public SpendingRepository spendingRepository;
    final public LiveData<Double> totalSpendingQuantity;
    final public List<Spending> chartDetail;
    final public List<Spending> spendingdate;

    final public List<Spending> spendingmonths;
    final public List<PiechartPojo> Pichartcategor;
    final public List<PiechartPojo> PichartCategoryByMont;
    final public List<LinechartPojo> linechartdata;

    final public List<Double> totalSpendingforchart;
    String group;
    Date dayst, dayet;




    public chartViewModel(Application application) {
        super(application);
        ((MoneycimApp) getApplication()).getAppComponent().inject(this);

//        Calendar now = Calendar.getInstance();
//        now.add(Calendar.MONTH,7);
//        dayst=now.getTime();
//        Calendar befor = Calendar.getInstance();
//        dayet=befor.getTime();
//        befor.add(Calendar.MONTH,3);

        totalSpendingQuantity = getTotalSpendingForPieChart(dayst, dayet);

        totalSpendingforchart = getTotalSpendingforchart();
        linechartdata= linechartData();
        spendingdate = getDate();
//        Pichartcategor = Pichartcategory(dayst,dayet);
        Pichartcategor = Pichartcategory(dayst, dayet);
        spendingmonths = getMonths();
        chartDetail = ChartDetail(group);
        PichartCategoryByMont=PichartCategoryByMonth();
    }



    public LiveData<Double> getTotalSpendingForPieChart(Date dayst,Date dayet) {
        return spendingRepository.getTotalSpendingPricePieChart(dayst, dayet);
    }

    public List<Spending> getDate() {
        return spendingRepository.GetSpendingsdate();
    }
    public List<LinechartPojo> linechartData() {
        return spendingRepository.LinechartData();
    }

    public List<Spending> ChartDetail(String groub) {
        return spendingRepository.Chartdetail(groub);
    }

    public List<PiechartPojo> Pichartcategory(Date dayst,Date dayet) {
        return spendingRepository.pichartcategory(dayst,dayet);
    }
//    public List<PiechartPojo> Pichartcategory(Date dayst,Date dayet) {
//        return spendingRepository.pichartcategory(dayst,dayet);
//    }
    public List<PiechartPojo> PichartCategoryByMonth() {
        return spendingRepository.PichartcategoryByMonth();
    }

    public List<Spending> getMonths() {
        return spendingRepository.GetSpendingsdatejustmonths();
    }


    public List<Double> getTotalSpendingforchart() {
        return spendingRepository.GetTotalSpendingforchart();
    }

}
