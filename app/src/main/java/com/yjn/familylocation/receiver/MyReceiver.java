package com.yjn.familylocation.receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.alibaba.fastjson.JSON;
import com.yjn.familylocation.MsgActivity;
import com.yjn.familylocation.R;
import com.yjn.familylocation.bean.MsgBean;
import com.yjn.familylocation.event.GetLocationEvent;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import cn.leancloud.AVOSCloud;

/**
 * <pre>
 *     author: Bruce_Yang
 *     blog  : https://yangjianan.gitee.io
 *     time  : 2019/9/12
 *     desc  : 自定义Receiver，接收推动消息
 * </pre>
 */
public class MyReceiver extends BroadcastReceiver {
    public static final String TAG = MyReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (intent.getAction().equals("com.msgpush.action")) {
                // 获取推送消息数据
                String message = intent.getStringExtra("com.avoscloud.Data");
                String channel = intent.getStringExtra("com.avoscloud.Channel");
                Log.i(TAG, "onReceive: " + "message=" + message + ", channel=" + channel);

                JSONObject json = new JSONObject(intent.getExtras().getString("com.avoscloud.Data"));
                final String alert = json.getString("alert");
                Log.i(TAG, "onReceive: " + alert);

                MsgBean msgBean = JSON.parseObject(alert, MsgBean.class);
                Log.i(TAG, "onReceive: type = " + msgBean.getType() + " data = " + msgBean.getContent());

                // TODO: 2019/9/12 处理不同消息
                if (MsgBean.REQUEST_LOCATION == msgBean.getType()) {
                    Log.i(TAG, "onReceive: 定位指令");
                    //准备获取定位
                    EventBus.getDefault().post(new GetLocationEvent(GetLocationEvent.Type.定位, msgBean.getRequestInstallationId()));
                } else if (MsgBean.RETURN_LOCATION == msgBean.getType()) {
                    //定位数据，准备显示在地图上
                    Log.i(TAG, "onReceive: 定位数据 " + msgBean.getContent()
                            //requestInstallationId用于地图展示匹配用户
                            + " requestInstallationId = " + msgBean.getRequestInstallationId());

                } else if (MsgBean.MSG == msgBean.getType()) {
                    //普通消息，可以考虑显示在状态栏
                    //如果有必要可以根据设置不同的channel，跳转到消息页面
                    Log.i(TAG, "onReceive: 普通消息");
                    notification(msgBean.getContent());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 状态栏通知
     *
     * @param data 数据内容
     */
    private void notification(String data) {
        //通知
        Intent resultIntent = new Intent(AVOSCloud.getContext(), MsgActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(AVOSCloud.getContext(), 0, resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(AVOSCloud.getContext())
                        .setSmallIcon(R.mipmap.ic_logo)
                        .setContentTitle(
                                AVOSCloud.getContext().getResources().getString(R.string.app_name))
                        .setContentText(data)
                        .setTicker(data);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setAutoCancel(true);

        int mNotificationId = 921021;
        NotificationManager mNotifyMgr =
                (NotificationManager) AVOSCloud.getContext()
                        .getSystemService(
                                Context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
}
