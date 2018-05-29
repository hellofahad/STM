package saedc.example.com.Alarm;

import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import saedc.example.com.View.MainActivity;
import saedc.example.com.View.SavingActivity;
import saedc.example.com.View.SpendingList.SpendingListViewModel;
import saedc.example.com.View.TotalSpendingPrice.TotalSpendingPriceViewModel;


public class AlarmReceiver extends BroadcastReceiver {

    String TAG = "AlarmReceiver";
    TotalSpendingPriceViewModel viewModel;


    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Bundle saving = intent.getExtras();

        String name = saving.getString("savingName");
        Boolean enableNotification = sharedPreferences.getBoolean("notifications_new_message", true);
        Toast.makeText(context, enableNotification.toString(), Toast.LENGTH_SHORT).show();
        if (enableNotification) {
            if (!(name == null)) {
                NotificationScheduler.showNotifications(context, SavingActivity.class,
                       "لاتنسى ان تتدخر", "اضغط للذهاب للمدخرات");
            }
            Log.d(TAG, "onReceive: ");
            if (name == null) {
                NotificationScheduler.showNotification(context, MainActivity.class,
                        "مصاريفي", "إضافة مصاريفك تساعدك على معرفة اين يذهب مالك");

            }
        }
        //Trigger the notification


    }
}


