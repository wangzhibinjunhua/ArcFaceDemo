package com.arcsoft.sdk_demo;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
/**
 * Created by Administrator on 2018-03-16.
 */

public class UnlockService extends Service{

    private UnlockWindow myWindow;
    private WindowManager mWindowManager;

    private LayoutParams Params;


    @Override
    public void onCreate() {
        Log.d("wzb","UnlockService onCreate");
        super.onCreate();
        //对于6.0以上的设备
        if (Build.VERSION.SDK_INT >= 23) {
            //如果支持悬浮窗功能
            if (Settings.canDrawOverlays(getApplicationContext())) {
                showWindow();
            } else {
                //手动去开启悬浮窗
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        } else {
            //6.0以下的设备直接开启
            showWindow();
        }
        if(myWindow!=null) {
            if (!myWindow.isAttachedToWindow()) {
                mWindowManager.addView(myWindow, Params);
            }
        }
    }

    @Override
    public void onDestroy() {
        Log.d("wzb","UnlockService onDestroy");
        if (myWindow.isAttachedToWindow()) {
        	mWindowManager.removeView(myWindow);
        }
        myWindow.exit();
        super.onDestroy();
    }

    private void showWindow() {
        //创建MyWindow的实例
        myWindow = new UnlockWindow(getApplicationContext());
        //窗口管理者
        mWindowManager = (WindowManager) getSystemService(Service.WINDOW_SERVICE);
        //窗口布局参数
        Params = new WindowManager.LayoutParams();
        //布局坐标,以屏幕左上角为(0,0)
        Params.x = 0;
        Params.y = 0;

        //布局类型

        Params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;//system err 可以显示在锁屏之上
        //布局flags
        Params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE; // 不能抢占聚焦点
        Params.flags = Params.flags | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        Params.flags = Params.flags | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS; // 排版不受限制
        Params.flags |= WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED;

        //布局的gravity
        Params.gravity = Gravity.LEFT | Gravity.TOP;

        //布局的宽和高
        Params.width =  1;//500;
        Params.height = 1;//500;

       /* myWindow.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_MOVE:
                        Params.x = (int) event.getRawX() - myWindow.getWidth() / 2;
                        Params.y = (int) event.getRawY() - myWindow.getHeight() / 2;
                        //更新布局位置
                        mWindowManager.updateViewLayout(myWindow, Params);

                        break;
                }
                return false;
            }
        });*/

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
