package com.example.entpe.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.example.entpe.R;
import com.example.entpe.activity.EntreArret;
import com.example.entpe.application.MyApplication;
import com.example.entpe.model.EnqueteCSV;
import com.example.entpe.model.TrackerGPS;
import java.util.Objects;

public class GpsService extends Service {

    //////////////////////////////////////////////////////////////////
    //// Attributes //////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    private TrackerGPS trackerGPS;
    private int enqueteId;

    private NotificationManager notifManager;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        //Gestion du gps
        MyApplication.setNbService(startId);
        notifManager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        trackerGPS = new TrackerGPS(MyApplication.getEnqueteId(),this,MyApplication.getNbService());
        trackerGPS.launch();
        return envoiNotifConstante();
    }

    private void createNotificationChannel() {
        //Vérification version oreo ou supérieur
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel("ChannelId1","Foreground notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy(){
        stopForeground(true);
        trackerGPS.stop();
        stopSelf();
        super.onDestroy();
    }
    
    public int envoiNotifConstante(){
        createNotificationChannel();
        Intent intent1 = new Intent(MyApplication.getAppContext(), EntreArret.class);
        intent1.putExtra("EnqueteId", enqueteId);
        PendingIntent pendingIntent = PendingIntent.getActivity(MyApplication.getAppContext(),0,intent1,0);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(MyApplication.getAppContext(),"ChannelId1")
                .setContentTitle("Collecte")
                .setNotificationSilent()
                .setContentText("Collecte de données gps en cours")
                .setSmallIcon(R.drawable.emotecamion)
                .setContentIntent(pendingIntent);
        startForeground(1,notification.build());
        return START_STICKY;
    }

    public int envoiNotifArret(){
        //gestion de la notif
        Intent intent1 = new Intent(MyApplication.getAppContext(), EntreArret.class);
        intent1.putExtra("Enquete", trackerGPS.getEnquete());
        intent1.putExtra("EnqueteId", trackerGPS.getEnqueteId());
        PendingIntent pendingIntent = PendingIntent.getActivity(MyApplication.getAppContext(),0,intent1,0);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(MyApplication.getAppContext(),"ChannelId1")
                .setContentTitle("Arret detecter")
                .setContentText("Etes-vous à un arrêt ?")
                .setSmallIcon(R.drawable.emotecamion)
                .addAction(R.drawable.emotecamion, "Oui", pendingIntent);
        notifManager.notify(2,notification.build());
        return START_NOT_STICKY;
    }



}


