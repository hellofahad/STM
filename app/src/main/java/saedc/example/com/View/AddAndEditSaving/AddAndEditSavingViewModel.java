package saedc.example.com.View.AddAndEditSaving;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import javax.inject.Inject;

import saedc.example.com.Model.Entity.Saving;
import saedc.example.com.Model.Repository.SpendingRepository;
import saedc.example.com.MoneycimApp;

/**
 * Created by saedc on 12/02/18.
 */

public class AddAndEditSavingViewModel extends AndroidViewModel {
    @Inject
    public SpendingRepository spendingRepository;
    final public Double totalsavings;
    public AddAndEditSavingViewModel(Application application) {
        super(application);
        ((MoneycimApp)getApplication()).getAppComponent().inject(this);

        totalsavings = GetTotalSavingprice();
    }
    public void ADDSaving(Saving s){
        addsavingTask task = new addsavingTask();
        task.execute(s);
    }
    public void DeleteSaving(int id){
        deletesavingTask task = new deletesavingTask();
        task.execute(id);
    }
    public Double GetTotalSavingprice() {
        return spendingRepository.GetTotalSavingprice();
    }

    class deletesavingTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... integers) {
            spendingRepository.deletesaving(integers[0]);

            return null;
        }
    }
    class addsavingTask extends AsyncTask<Saving, Void, Void> {



        @Override
        protected Void doInBackground(Saving... savings) {
            spendingRepository.Addsaving(savings[0]);
            return null;
        }
    }
}
