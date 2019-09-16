package com.yjn.familylocation.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.yjn.familylocation.service.BackLocationService;
import com.yjn.familylocation.service.BackPushService;
import com.yjn.familylocation.util.Utils;

import cn.leancloud.push.PushService;

/**
 * <pre>
 *     author: Bruce_Yang
 *     blog  : https://yangjianan.gitee.io
 *     time  : 2019/9/16
 *     desc  : 锁屏&解锁&开屏广播监听
 * </pre>
 */
public class ScreenBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = ScreenBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_SCREEN_OFF)) {
            Log.i(TAG, "onReceive: 锁屏");
        } else if (action.equals(Intent.ACTION_SCREEN_ON)) {
            Log.i(TAG, "onReceive: 解锁");
        } else if (action.equals(Intent.ACTION_USER_PRESENT)) {
            Log.i(TAG, "onReceive: 开屏");
        }

        //启动2个后台服务
        if (!Utils.isServiceRunning(BackPushService.class.getName())) {
            Utils.getApp().startService(new Intent(context, BackPushService.class));
        }

        if (!Utils.isServiceRunning(BackLocationService.class.getName())) {
            Utils.getApp().startService(new Intent(context, BackLocationService.class));
        }
    }
}
