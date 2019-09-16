package com.yjn.familylocation.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yjn.familylocation.MainActivity;
import com.yjn.familylocation.bean.Constants;
import com.yjn.familylocation.bean.MsgBean;
import com.yjn.familylocation.event.GetLocationEvent;
import com.yjn.familylocation.event.RequestEvent;
import com.yjn.familylocation.util.SPUtils;
import com.yjn.familylocation.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.leancloud.AVInstallation;
import cn.leancloud.AVObject;
import cn.leancloud.AVPush;
import cn.leancloud.AVQuery;
import cn.leancloud.push.PushService;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * <pre>
 *     author: Bruce_Yang
 *     blog  : https://yangjianan.gitee.io
 *     time  : 2019/9/12
 *     desc  : 后台推送服务
 * </pre>
 */
public class BackPushService extends Service {
    public static final String TAG = BackPushService.class.getSimpleName();
    //群组id
    public static final String CHANNEL_ID = "zhuzhuyang";
    private String installationId;

    private SPUtils spUtils;

    private boolean sendMsg;

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
        spUtils = SPUtils.getInstance(Constants.SP_NAME);

        init();
    }

    /**
     * 初始化Leancloud消息推送
     * https://leancloud.cn/docs/android_push_guide.html#hash1103064005
     */
    private void init() {
        AVInstallation.getCurrentInstallation().saveInBackground();
        AVInstallation.getCurrentInstallation().saveInBackground().subscribe(new Observer<AVObject>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(AVObject avObject) {
                // 关联 installationId 到用户表等操作。
                installationId = AVInstallation.getCurrentInstallation().getInstallationId();
                spUtils.put(Constants.INSTALLATIONID_SP, installationId);
                Log.i(TAG, "onNext: " + "保存成功：" + installationId);
            }

            @Override
            public void onError(Throwable e) {
                installationId = "";
                spUtils.put(Constants.INSTALLATIONID_SP, installationId);
                Log.e(TAG, "onError: 保存失败，错误信息：", e);
            }

            @Override
            public void onComplete() {
            }
        });

        // 启动推送服务
        // 设置默认打开的 Activity(点击通知栏消息跳转)
        PushService.setDefaultPushCallback(this, MainActivity.class);
        // 订阅频道，当该频道消息到来的时候，打开对应的 Activity
        // 参数依次为：当前的 context、频道名称、回调对象的类
        PushService.subscribe(this, CHANNEL_ID, MainActivity.class);
    }

    /**
     * 单对单发送（通知栏展示setMessage）通过targetDeviceId
     * 类似私信的功能
     */
    private void sendOne(String msg, String targetInstallationId) {

        AVQuery pushQuery = AVInstallation.getQuery();
        // 假设 targetInstallationId 是保存在用户表里的 installationId，
        // 可以在应用启动的时候获取并保存到用户表
        pushQuery.whereEqualTo("installationId", targetInstallationId);
        AVPush.sendMessageInBackground(msg, pushQuery).subscribe(new Observer() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object object) {
                ToastUtils.showShort("消息发送成功");
            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.showShort("消息发送失败: " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 群发
     */
    private void sendGroup(String msg, String targetInstallationId) {
        if (targetInstallationId.equals(installationId)) {
            ToastUtils.showShort("发给自己干嘛呀(*￣︶￣)");
            return;
        }
        AVPush push = new AVPush();
        //如果不设置setQuery，那么就是发送给CHANNEL_ID组下的所有人
        push.setQuery(AVInstallation.getQuery().whereEqualTo("installationId",
                targetInstallationId));
        push.setChannel(CHANNEL_ID);

        // 设置消息
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", "com.msgpush.action");
        jsonObject.put("alert", msg);
        //jsonObject.put("alert", "哈哈哈");
        //透传不显示通知栏 https://leancloud.cn/docs/push_guide.html#hash79355699
        jsonObject.put("silent", true);

        push.setData(jsonObject);
        push.setPushToAndroid(true);

        push.sendInBackground().subscribe(new Observer<JSONObject>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(JSONObject jsonObject) {
                ToastUtils.showShort(sendMsg ? "指令发送成功" : "定位发送成功");
            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.showShort("消息发送失败: " + e.getMessage());
            }

            @Override
            public void onComplete() {
            }
        });

    }

    /**
     * 拿到定位数据，并把消息推送返回给请求方
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void sendLocation(GetLocationEvent event) {
        if (event.getType() == GetLocationEvent.Type.返回定位) {
            // TODO: 2019/9/12 消息推送
            // 可选 sendOne ，sendGroup
            MsgBean msgBean = new MsgBean(MsgBean.RETURN_LOCATION);
            //发送方
            msgBean.setRequestInstallationId(installationId);
            msgBean.setContent(event.getLanlon());

            //目标方
            String targetInstallationId = event.getRequestInstallationId();

            sendMsg = false;
            sendGroup(JSON.toJSONString(msgBean), targetInstallationId);
        }
    }

    /**
     * 发起定位请求
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void dealRequest(RequestEvent event) {
        //发起方 id
        if (TextUtils.isEmpty(installationId)) {
            ToastUtils.showShort("出错了请稍后再试，或者联系小羊！");
            init();
            return;
        }
        //接收方 id
        String targetInstallationId = event.getTargetInstallationId();
        if (TextUtils.isEmpty(targetInstallationId)) {
            ToastUtils.showShort("请选择定位目标！");
            return;
        }

        MsgBean msgBean = new MsgBean(MsgBean.REQUEST_LOCATION);
        //请求方
        msgBean.setRequestInstallationId(installationId);
        //目标方
        msgBean.setTargetInstallationId(targetInstallationId);

        sendMsg = true;
        sendGroup(JSON.toJSONString(msgBean), targetInstallationId);
//        sendGroup(new Gson().toJson(msgBean), targetInstallationId);
    }
}
