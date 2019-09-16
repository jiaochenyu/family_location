package com.yjn.familylocation.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.amap.api.location.AMapLocation;
import com.yjn.familylocation.event.GetLocationEvent;
import com.yjn.familylocation.util.GDLocationUtil;
import com.yjn.familylocation.util.ToastUtils;

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
    private String requestInstallationId;

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
            ToastUtils.showShort("开始定位");
            requestInstallationId = event.getRequestInstallationId();

            // 获取当前位置，无论是否定位过，重新进行定位
            GDLocationUtil.getCurrentLocation(new GDLocationUtil.MyLocationListener() {
                @Override
                public void result(AMapLocation location) {
                    //针对location进行相关操作，如location.getCity()，无需验证location是否为null;
                    //定位成功回调信息，设置相关消息. 纬度+经度
                    if (location.getLatitude() == 0.0 && location.getLongitude() == 0.0) {
                        Log.e(TAG, "result: 定位失败 errorcode = " + location.getErrorCode()
                                + " errorinfo = " + location.getErrorInfo());
                        return;
                    }
                    String lanLon = location.getLatitude() + "," + location.getLongitude();
                    Log.i(TAG, "result: lanLon = " + lanLon);
                    // TODO: 2019/9/12 定位成功后返回坐标
                    EventBus.getDefault().post(new GetLocationEvent(
                            GetLocationEvent.Type.返回定位,
                            requestInstallationId,
                            lanLon));
                }
            });
        }
    }
}
