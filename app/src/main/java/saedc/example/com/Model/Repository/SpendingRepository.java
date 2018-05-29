package saedc.example.com.Model.Repository;

import android.arch.lifecycle.LiveData;

import saedc.example.com.Model.Database.AppDatabase;
import saedc.example.com.Model.Entity.RawSpending;

import saedc.example.com.Model.Entity.Saving;
import saedc.example.com.Model.Entity.SpendingGroup;
import saedc.example.com.Model.Pojo.LinechartPojo;
import saedc.example.com.Model.Pojo.MaxSaving;
import saedc.example.com.Model.Pojo.PiechartPojo;
import saedc.example.com.Model.Pojo.Spending;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class SpendingRepository {
    private AppDatabase appDatabase;
    @Inject
    public SpendingRepository(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }


    public LiveData<MaxSaving> Maxsaving(){
        return appDatabase.savingDao().maxsaving();
    }
    public LiveData<List<Saving>> Getsaving(){
        return appDatabase.savingDao().getsaving();
    }
    public Double GetTotalSavingprice(){
        return appDatabase.savingDao(). getTotalSavingprice();
    }

    public void deletesaving(int id){
        appDatabase.savingDao().deleteSaving(id);
    }
    public void Addsaving(Saving s){
        appDatabase.savingDao().addSaving(s);
    }

    public LiveData<List<Spending>> getMaxGroub(){
        return appDatabase.spendingDao(). getMaxGroub();
    }

    public LiveData<List<LinechartPojo>> changecolorpercent(){
        return appDatabase.spendingDao().changecolorpercent();
    }
    public LiveData<List<Spending>> getSpendings(Date start,Date end){
        return appDatabase.spendingDao().getSpendingsWithGroups(start,end);
    }
    public List<LinechartPojo> LinechartData(){
        return appDatabase.spendingDao().Linechartdata();
    }
    public List<Spending> GetSpendingsdate(){
        return appDatabase.spendingDao().getSpendingsdate();
    }
    public List<Spending> Chartdetail(String Category){
        return appDatabase.spendingDao().chartdetail(Category);
    }
    public List<PiechartPojo> pichartcategory(Date dayst,Date dayet){
        return appDatabase.spendingDao().pichartcategory(dayst,dayet);
    }
    public List<PiechartPojo> PichartcategoryByMonth(){
        return appDatabase.spendingDao().pichartcategoryByMonth();
    }
    public LiveData<List<PiechartPojo>> CstegoryNameWithTotalList(){
        return appDatabase.spendingDao().CstegoryNameWithTotalList();
    }
    public List<Spending> GetSpendingsdatejustmonths(){
        return appDatabase.spendingDao().getSpendingsdatejustmonths();
    }

// i change it
    public List<Double> GetTotalSpendingforchart(){
        return appDatabase.spendingDao().getTotalByGroub();

    }

    public LiveData<Double> getTotalSpendingQuantity(){
        return appDatabase.spendingDao().getTotalSpendingQuantity();
    }


    public LiveData<Double> getTotalSpendingPricePieChart(Date dayst,Date dayet){
        return appDatabase.spendingDao().getTotalSpendingPriceForPieChart( dayst, dayet);
    }

    public Double getTotalSpending(){
        return appDatabase.spendingDao().getTotalSpendingQuantit();
    }
    public LiveData<Double> getTotalSpendingprice(int groub){
        return appDatabase.spendingDao().getTotalSpendingQuantity1(groub);
    }

    public void addSpending(RawSpending s){
        appDatabase.spendingDao().addSpending(s);
    }

    public LiveData<List<SpendingGroup>> getSpendingGroups(){
        return appDatabase.spendingGroupDao().getAllSpendingGroups();
    }

    public void deleteSpending(int id){
        appDatabase.spendingDao().deleteSpending(id);
    }

}
