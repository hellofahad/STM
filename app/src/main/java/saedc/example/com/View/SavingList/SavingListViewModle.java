package saedc.example.com.View.SavingList;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import saedc.example.com.Model.Entity.Saving;
import saedc.example.com.Model.Repository.SpendingRepository;
import saedc.example.com.MoneycimApp;

/**
 * Created by saedc on 11/02/18.
 */

public class SavingListViewModle extends AndroidViewModel {


    final public LiveData<List<Saving>> savings;
    final public Double totalsavings;
    @Inject
    public SpendingRepository spendingRepository;

    public SavingListViewModle(Application application) {
        super(application);
        ((MoneycimApp) getApplication()).getAppComponent().inject(this);

        savings = Getsavings();
        totalsavings = GetTotalSavingprice();
    }

    public LiveData<List<Saving>> Getsavings() {
        return spendingRepository.Getsaving();
    }

    public Double GetTotalSavingprice() {
        return spendingRepository.GetTotalSavingprice();
    }

    public void DeleteSaving(int id) {
        spendingRepository.deletesaving(id);
    }
}
