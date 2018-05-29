package saedc.example.com.View.SpendingList;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;
import android.widget.Toast;

import saedc.example.com.Model.Entity.RawSpending;
import saedc.example.com.Model.Pojo.LinechartPojo;
import saedc.example.com.Model.Pojo.Spending;
import saedc.example.com.Model.Repository.SpendingRepository;
import saedc.example.com.MoneycimApp;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;


public class SpendingListViewModel extends AndroidViewModel {

    @Inject public SpendingRepository spendingRepository;
    final public LiveData<List<Spending>> spendings;
    final public LiveData<List<LinechartPojo>> ChangecolorPercent;
    Date start, end;

    public SpendingListViewModel(Application application) {
        super(application);
        ((MoneycimApp)getApplication()).getAppComponent().inject(this);

//        enD.set(Calendar.MONTH,noW.get(Calendar.MONTH-1));
//        starT.set(Calendar.MONTH,noW.get(Calendar.MONTH+1));
//        enD.set(Calendar.MONTH, 5);
//        starT.set(Calendar.MONTH,4);


        spendings = getSpendings(start, end);
        ChangecolorPercent=Changecolorpercent();
    }

    public LiveData<List<LinechartPojo>> Changecolorpercent(){
        return spendingRepository.changecolorpercent();
    }

    public LiveData<List<Spending>> getSpendings(Date start,Date end){
        return spendingRepository.getSpendings(start,end);
    }
    public void deleteSpending(int id){
        spendingRepository.deleteSpending(id);
    }
}
