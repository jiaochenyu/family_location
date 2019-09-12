package com.yjn.familylocation.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yjn.familylocation.MainActivity;
import com.yjn.familylocation.service.BackLocationService;
import com.yjn.familylocation.service.BackPushService;
import com.yjn.familylocation.util.GDLocationUtil;
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
    @Override
    public void onReceive(Context context, Intent intent) {
        // 定位工具初始化
        GDLocationUtil.init(Utils.getApp());

        if (!Utils.isServiceRunning(BackPushService.class.getName())) {
            Utils.getApp().startService(new Intent(context, BackPushService.class));
        }

        if (!Utils.isServiceRunning(BackLocationService.class.getName())) {
            Utils.getApp().startService(new Intent(context, BackLocationService.class));
        }
    }
}
