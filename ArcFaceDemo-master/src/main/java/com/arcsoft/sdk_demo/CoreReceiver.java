package com.arcsoft.sdk_demo;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

/**
 * Created by Administrator on 2018-03-16.
 */

public class CoreReceiver extends BroadcastReceiver{

    private Context mContext;


    @Override
    public void onReceive(Context context, Intent intent) {
        mContext=context;
        String action=intent.getAction();
        Log.d("wzb","facelock CoreReceiver action:"+action);
        if(action.equals("com.android.custom.screen_on")){
            Log.d("wzb","facelock CoreReceiver screen on");
            startUnlockService();
        }else if(action.equals("com.android.custom.unlock")){
            stopUnlockService();
        }else if(action.equals("com.android.custom.notmatch_test")){
            sendNotification(context);
        }else if(action.equals("com.android.custom.screen_off")){
            stopUnlockService();
        }
    }

    private void sendNotification(Context context) {
        //获取NotificationManager实例
        NotificationManager notifyManager = (NotificationManager)mContext. getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)//设置小图标
                .setContentTitle("face lock")
                .setContentText("not match")
                .build();
        notifyManager.notify(0, notification);
    }

    private void startUnlockService(){
        Intent intent=new Intent(mContext,UnlockService.class);
        mContext.startService(intent);
    }

    private void stopUnlockService(){
        Intent intent=new Intent(mContext,UnlockService.class);
        mContext.stopService(intent);
    }



}
