package com.yjn.familylocation.util;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;

/**
 * @author frank.fun@qq.com
 * @ClassName: GDLocationUtil
 * @Description: 高德地图定位工具类
 * @date 2017年1月8日 下午1:51:47
 * url: https://segmentfault.com/a/1190000008048506
 */
public class GDLocationUtil {
    public static final String TAG = GDLocationUtil.class.getSimpleName();
    private static AMapLocationClient mlocationClient;
    public static AMapLocationClientOption mLocationOption = null;
    public static AMapLocation sLocation = null;

    /**
     * @param context
     * @Title: init
     * @Description: 初始化地图导航，在Application onCreate中调用，只需调用一次
     */
    public static void init(Context context) {
        //初始化之前先destroy
        GDLocationUtil.destroy();

        if (mlocationClient == null) {
            // 声明mLocationOption对象
            mlocationClient = new AMapLocationClient(context);
            // 初始化定位参数
            mLocationOption = new AMapLocationClientOption();
        } else {
            mlocationClient.stopLocation();
        }
        // 设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
        // 设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(1000);

        //获取一次定位结果
        mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);

        // 设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
    }

    /**
     * @author frank.fun@qq.com
     * @ClassName: MyLocationListener
     * @Description: 定位结果回调
     * @date 2017年1月8日 下午1:53:11
     */
    public interface MyLocationListener {
        void result(AMapLocation location);
    }

    /**
     * @param listener
     * @Title: getLocation
     * @Description: 获取位置，如果之前获取过定位结果，则不会重复获取
     * 通常来说单独的定位功能是用于确定用户所在城市、位置，仅作显示并通过上传位置信息对用户提供相应周边服务用，
     * 因此无需重复定位。重复定位耗电量较多且大多无实际作用，因此此处只做单次定位的处理。
     */
    public static void getLocation(MyLocationListener listener) {
        if (sLocation == null) {
            getCurrentLocation(listener);
        } else {
            listener.result(sLocation);
        }
    }

    private static long lastLocateTime;

    /**
     * @param listener
     * @Title: getCurrentLocation
     * @Description: 获取位置，重新发起获取位置请求
     */
    public static void getCurrentLocation(final MyLocationListener listener) {
        if (mlocationClient == null) {
            return;
        }
        // 设置定位监听
        mlocationClient.setLocationListener(location -> {
            if (location != null) {
                //定位成功，取消定位
                mlocationClient.stopLocation();
                sLocation = location;

                long end = System.currentTimeMillis();
                if (end - lastLocateTime > 1000) {
                    lastLocateTime = end;
                    listener.result(location);
                } else {
                    Log.i(TAG, "onLocationChanged: 返回定位不要太频繁");
                }

            } else {
                //获取定位数据失败
                ToastUtils.showShort("定位失败");
            }
        });
        // 启动定位
        mlocationClient.startLocation();
    }

    /**
     * @Title: destroy
     * @Description: 销毁定位，必须在退出程序时调用，否则定位会发生异常
     */
    public static void destroy() {
        if (mlocationClient != null) {
            mlocationClient.onDestroy();
            mlocationClient = null;
        }
    }
}