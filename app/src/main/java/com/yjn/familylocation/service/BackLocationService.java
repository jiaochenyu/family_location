package com.yjn.familylocation.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.yjn.familylocation.event.GetLocationEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * <pre>
 *     author: Bruce_Yang
 *     blog  : https://yangjianan.gitee.io
 *     time  : 2019/9/12
 *     desc  : 后台定位服务，使用高德
 * </pre>
 */
public class BackLocationService extends Service {
    public static final String TAG = BackLocationService.class.getSimpleName();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    /**
     * 开始定位
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void getCurrentLocation(GetLocationEvent event) {
        if (event.getType() == GetLocationEvent.Type.定位) {
            // TODO: 2019/9/12 调用高德定位
            Log.i(TAG, "getCurrentLocation: 调用高德地图");

            // TODO: 2019/9/12 经纬度
            String lanlon = "111,1232";

            EventBus.getDefault().post(new GetLocationEvent(
                    GetLocationEvent.Type.返回定位,
                    event.getRequestInstallationId(),
                    lanlon)
            );
        }
    }
}
