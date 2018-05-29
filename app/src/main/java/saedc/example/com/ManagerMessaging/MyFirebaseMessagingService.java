package saedc.example.com.ManagerMessaging;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

import saedc.example.com.R;
import saedc.example.com.View.MainActivity;


public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService{
final static String chanelid ="chanelid";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        showNotification(remoteMessage.getNotification().getBody());

    }

    private void showNotification(String message) {

        Intent i = new Intent(this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0,i,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,chanelid)
                .setAutoCancel(true)
                .setContentTitle(getBaseContext().getString(R.string.notifications_title))
                .setContentText(message)
                .setSmallIcon(R.drawable.stmproject)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(0,builder.build());
    }


}