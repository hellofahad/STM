package saedc.example.com.View.AddAndEditSpending;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import saedc.example.com.Model.Entity.RawSpending;
import saedc.example.com.Model.Entity.SpendingGroup;
import saedc.example.com.Model.Repository.SpendingRepository;
import saedc.example.com.MoneycimApp;

import java.util.List;

import javax.inject.Inject;


public class AddAndEditSpendingViewModel extends AndroidViewModel {

    @Inject
    public SpendingRepository spendingRepository;

    final public LiveData<List<SpendingGroup>> spendingGroups;

    public AddAndEditSpendingViewModel(Application application) {
        super(application);
        ((MoneycimApp)getApplication()).getAppComponent().inject(this);

        spendingGroups = getSpendingGroups();
    }

    public LiveData<List<SpendingGroup>> getSpendingGroups() {
        return spendingRepository.getSpendingGroups();
    }

    public void addSpending(RawSpending s){

        addSpendingTask task = new addSpendingTask();
        task.execute(s);
    }

    public void deleteSpending(int id){
        deleteSpendingTask task =new deleteSpendingTask();
        task.execute(id);
    }

    class deleteSpendingTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... integers) {
            spendingRepository.deleteSpending(integers[0]);
            return null;
        }
    }
    class addSpendingTask extends AsyncTask<RawSpending, Void, Void> {


        @Override
        protected Void doInBackground(RawSpending... rawSpendings) {
            spendingRepository.addSpending(rawSpendings[0]);
            return null;
        }
    }
}
