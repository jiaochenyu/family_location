package com.yjn.familylocation.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yjn.familylocation.service.BackLocationService;
import com.yjn.familylocation.service.BackPushService;
import com.yjn.familylocation.util.Utils;

/**
 * <pre>
 *     author: Bruce_Yang
 *     blog  : https://yangjianan.gitee.io
 *     time  : 2019/9/12
 *     desc  : 自启动广播
 * </pre>
 */
public class BootReceiver extends BroadcastReceiver {
    public static final String TAG = BootReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals("com.yjn.restart.action")) {
            try {
                //1.5秒后重启
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
