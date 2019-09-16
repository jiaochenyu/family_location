package com.yjn.familylocation;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.azhon.appupdate.config.UpdateConfiguration;
import com.azhon.appupdate.manager.DownloadManager;
import com.yanzhenjie.permission.AndPermission;
import com.yjn.familylocation.bean.Constants;
import com.yjn.familylocation.bean.LatLonBean;
import com.yjn.familylocation.bean.UpdateBean;
import com.yjn.familylocation.event.RequestEvent;
import com.yjn.familylocation.service.BackLocationService;
import com.yjn.familylocation.service.BackPushService;
import com.yjn.familylocation.ui.AboutActivity;
import com.yjn.familylocation.ui.SettingActivity;
import com.yjn.familylocation.util.GDLocationUtil;
import com.yjn.familylocation.util.SPUtils;
import com.yjn.familylocation.util.ToastUtils;
import com.yjn.familylocation.util.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <pre>
 *     author: Bruce_Yang
 *     blog  : https://yangjianan.gitee.io
 *     time  : 2019/9/12
 *     desc  : 首页
 * </pre>
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private MapView mMapView = null;
    //初始化地图控制器对象
    private AMap aMap;
    private MarkerOptions markerOption;
    private Marker marker;
    private LatLng latLng;
    private boolean followMove;
    private EditText targetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        targetId = findViewById(R.id.target_installid);
        targetId.setText(SPUtils.getInstance(Constants.SP_NAME).getString(Constants.TARGET_INSTALLATIONID_SP,
                targetId.getText().toString().trim()));

        //mapview初始化
        initMapView(savedInstanceState);

        // TODO: 2019/9/12 动态授权之后初始化下面的所有逻辑

        AndPermission.with(this)
                .runtime()
                .permission(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE)
                .onGranted(permissions -> {
                    // 定位工具初始化
                    GDLocationUtil.init(MainActivity.this);
                    if (!Utils.isServiceRunning(BackPushService.class.getName())) {
                        startService(new Intent(MainActivity.this, BackPushService.class));
                    }

                    if (!Utils.isServiceRunning(BackLocationService.class.getName())) {
                        startService(new Intent(MainActivity.this, BackLocationService.class));
                    }
                    //升级检查
                    checkUpdate();
                })
                .onDenied(permissions -> ToastUtils.showShort("权限不足，无法使用！"))
                .start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_menu:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.setting_menu:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private void initMapView(Bundle savedInstanceState) {
        //获取地图控件引用
        mMapView = findViewById(R.id.map_view);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);

        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        aMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            public void onMapLoaded() {
                setUp(aMap);
            }
        });

        //位置变化监听器
        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                latLng = new LatLng(latitude, longitude);
                if (followMove) {
                    aMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                }
            }

        });

        //setOnMapTouchListener监听
        aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                //用户拖动地图后，不再跟随移动，直到用户点击定位按钮
                followMove = false;
            }

        });

    }

    private void setUp(AMap amap) {
        UiSettings uiSettings = amap.getUiSettings();
        amap.showIndoorMap(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setScaleControlsEnabled(true);
        //true:显示地图默认右上方圆形定位图标  false:不显示
        uiSettings.setMyLocationButtonEnabled(true);

       /* amap.setOnMapClickListener(this);
        amap.setLocationSource(this);// 设置定位监听*/
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        /*myLocationStyle.myLocationIcon(
                BitmapDescriptorFactory.fromResource(R.mipmap.ic_logo));*/
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        // 自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(0);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
        // 将自定义的 myLocationStyle 对象添加到地图上
        amap.setMyLocationStyle(myLocationStyle);
        amap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        amap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
    }

    /**
     * 在地图上添加marker
     */
    private void addMarkersToMap(LatLng latlng) {
        //先清除定图上的marker
        if (aMap != null && marker != null) {
            marker.destroy();
        }

        markerOption = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker))
                .position(latlng)
                .draggable(true);
        marker = aMap.addMarker(markerOption);
    }

    /**
     * 检查应用升级
     */
    private void checkUpdate() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(Constants.UPDATE_URL_CHECK).build();
        //enqueue就是将此次的call请求加入异步请求队列，会开启新的线程执行，并将执行的结果通过Callback接口回调的形式返回。
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求失败的回调方法
                Log.e(TAG, "onFailure: ", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    //请求成功的回调方法
                    String result = response.body().string();
                    Log.i(TAG, result);
                    //关闭body
                    response.body().close();

                    JSONArray jsonArray = JSONArray.parseArray(result);

                    final UpdateBean updateBean = JSON.parseObject(jsonArray.get(0).toString(), UpdateBean.class);
                    if (BuildConfig.VERSION_CODE < updateBean.getApkData().getVersionCode()) {
                        runOnUiThread(() -> startUpdate(updateBean));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "onResponse: 更新异常 ", e);
                }
            }
        });

    }

    private void startUpdate(UpdateBean updateBean) {
        /*
         * 整个库允许配置的内容
         * 非必选
         */
        UpdateConfiguration configuration = new UpdateConfiguration()
                //输出错误日志
                .setEnableLog(true)
                //设置自定义的下载
                //.setHttpManager()
                //下载完成自动跳动安装页面
                .setJumpInstallPage(true)
                //设置对话框背景图片 (图片规范参照demo中的示例图)
                //.setDialogImage(R.drawable.ic_dialog)
                //设置按钮的颜色
                //.setDialogButtonColor(Color.parseColor("#E743DA"))
                //设置按钮的文字颜色
                .setDialogButtonTextColor(Color.WHITE)
                //设置是否显示通知栏进度
                .setShowNotification(true)
                //设置是否提示后台下载toast
                .setShowBgdToast(false)
                //设置强制更新
                .setForcedUpgrade(false)
                /*//设置对话框按钮的点击监听
                .setButtonClickListener(this)
                //设置下载过程的监听
                .setOnDownloadListener(this)*/;

        DownloadManager manager = DownloadManager.getInstance(MainActivity.this);
        manager.setApkName("family_keep.apk")
                .setApkUrl(Constants.UPDATE_URL_BASE + updateBean.getApkData().getOutputFile())
                .setSmallIcon(R.mipmap.app_update)
                .setShowNewerToast(true)
                .setConfiguration(configuration)
                //.setDownloadPath(Environment.getExternalStorageDirectory() + "/AppUpdate")
                .setApkVersionCode(updateBean.getApkData().getVersionCode())
                .setApkVersionName(updateBean.getApkData().getVersionName())
                .setApkSize(manager.getApkSize())
                .setAuthorities(getPackageName())
                .setApkDescription("修复bug")
                .download();
    }

    /**
     * 地图显示目标设备的位置
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showTargetPosition(LatLonBean latLonBean) {
        ToastUtils.showShort("获取位置成功");
        followMove = true;
        if (followMove) {
            LatLng latLng = new LatLng(
                    latLonBean.getLatitude(), latLonBean.getLongitude());
            aMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

            addMarkersToMap(latLng);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 不退出程序，进入后台
            moveTaskToBack(true);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MENU) {
            //监控/拦截MENU键
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void getLocation(View view) {
        //接收方 id
        String targetInstallationId = targetId.getText().toString().trim();
        //缓存targetid，下次可以继续使用
        SPUtils.getInstance(Constants.SP_NAME).put(Constants.TARGET_INSTALLATIONID_SP,
                targetInstallationId);
        //发送“请求定位”消息
        EventBus.getDefault().post(new RequestEvent(targetInstallationId));
    }
}
