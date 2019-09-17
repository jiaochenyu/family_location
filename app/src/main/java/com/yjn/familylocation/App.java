package com.yjn.familylocation;

import android.app.Application;

import cn.leancloud.AVLogger;
import cn.leancloud.AVOSCloud;
import cn.leancloud.push.PushService;

/**
 * <pre>
 *     author: Bruce_Yang
 *     blog  : https://yangjianan.gitee.io
 *     time  : 2019/9/12
 *     desc  : Application
 * </pre>
 */
public class App extends Application {
    public static final String TAG = App.class.getSimpleName();

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();
    }

    public void init() {
        //开启调试日志
        //AVOSCloud.setLogLevel(BuildConfig.DEBUG ? AVLogger.Level.DEBUG : AVLogger.Level.OFF);
        AVOSCloud.setLogLevel(AVLogger.Level.OFF);
        // 初始化应用信息
        AVOSCloud.initialize(
                this,
                "m41obU3Oy7OLtuVv5vMACAc2-gzGzoHsz",
                "5QXimgii1twDEJ35Ynpvv4lq"
        );
        //Android 8.0 推送适配，设置通知展示的默认 channel，否则消息无法展示。
        PushService.setDefaultChannelId(this, TAG);
    }
}
