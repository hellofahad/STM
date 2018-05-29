package saedc.example.com.View.TotalSpendingPrice;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import saedc.example.com.Model.Pojo.MaxSaving;
import saedc.example.com.Model.Pojo.PiechartPojo;
import saedc.example.com.Model.Pojo.Spending;
import saedc.example.com.Model.Repository.SpendingRepository;
import saedc.example.com.MoneycimApp;

import java.util.List;

import javax.inject.Inject;


public class TotalSpendingPriceViewModel extends AndroidViewModel {
    @Inject
    public SpendingRepository spendingRepository;
    final public LiveData<Double> totalSpendingQuantity;


    final public LiveData<Double> getTotalSpendingprice;
    final public LiveData<List<Spending>> GetMaxgroub;
    final public LiveData<MaxSaving> maxsave;
    final public LiveData<List<PiechartPojo>> CstegoryNameWithTotalList;

    int groub;
    final public List<Double> totalSpendingforchart;
    final public Double getTotalSpending;
    public TotalSpendingPriceViewModel(Application application) {
        super(application);
        ((MoneycimApp) getApplication()).getAppComponent().inject(this);

        totalSpendingQuantity = getTotalSpendingQuantity();
        getTotalSpendingprice = getTotalSpendingprice(groub);
        GetMaxgroub = getMaxgroub();

        totalSpendingforchart = getTotalSpendingforchart();
        CstegoryNameWithTotalList=CstegoryNameWithTotalList();
        getTotalSpending =getTotalSpending();
        maxsave=maxsaving();

    }


    public LiveData<Double> getTotalSpendingQuantity() {
        return spendingRepository.getTotalSpendingQuantity();
    }
    public LiveData<MaxSaving> maxsaving() {
        return spendingRepository.Maxsaving();
    }

    public LiveData<Double> getTotalSpendingprice(int groub) {
        return spendingRepository.getTotalSpendingprice(groub);
    }

    public LiveData<List<PiechartPojo>> CstegoryNameWithTotalList() {
        return spendingRepository.CstegoryNameWithTotalList();
    }
    public Double getTotalSpending() {

        return spendingRepository.getTotalSpending();
    }



    public LiveData<List<Spending>> getMaxgroub() {
        return spendingRepository.getMaxGroub();
    }


    public List<Double> getTotalSpendingforchart() {
        return spendingRepository.GetTotalSpendingforchart();
    }


}
