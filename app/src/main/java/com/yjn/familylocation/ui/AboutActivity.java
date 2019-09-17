package com.yjn.familylocation.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drakeet.about.AbsAboutActivity;
import com.drakeet.about.Card;
import com.drakeet.about.Category;
import com.drakeet.about.Contributor;
import com.drakeet.about.License;
import com.drakeet.about.Recommendation;
import com.drakeet.about.provided.GlideImageLoader;
import com.yjn.familylocation.BuildConfig;
import com.yjn.familylocation.R;

import java.util.List;


/**
 * <pre>
 *     author: Bruce_Yang
 *     blog  : https://yangjianan.gitee.io
 *     time  : 2019/9/16
 *     desc  : 关于
 * </pre>
 */
@SuppressLint("SetTextI18n")
public class AboutActivity extends AbsAboutActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setImageLoader(new GlideImageLoader());
    }

    @Override
    protected void onCreateHeader(@NonNull ImageView icon, @NonNull TextView slogan, @NonNull TextView version) {
        icon.setImageResource(R.mipmap.ic_logo);
        slogan.setText(getString(R.string.app_name));
        version.setText("v" + BuildConfig.VERSION_NAME);
    }

    @Override
    protected void onItemsCreated(@NonNull List<Object> items) {
        items.add(new Category("介绍与帮助"));
        items.add(new Card(getString(R.string.card_content)));

        items.add(new Category("Developers"));
        items.add(new Contributor(R.mipmap.author, "Bruce Yang", "Developer & designer", "https://yangxiaoge.github.io/"));

        items.add(new Category("我独立开发的应用"));
        items.add(new Recommendation(
                0, "family_location",
                "https://raw.githubusercontent.com/yangxiaoge/family_location/master/app/src/main/res/mipmap-xxhdpi/ic_logo.png",
                "com.yjn.familylocation",
                "\uD83D\uDC6A 定位亲人位置，守护亲人！",
                "https://github.com/yangxiaoge/family_location",
                "2019-09-12 14:49:18",
                "2019-9-17 10:08:32",
                13.7,
                false
        ));
        items.add(new Recommendation(
                0, "MumuXi",
                "https://raw.githubusercontent.com/yangxiaoge/MumuXi/master/app/src/main/res/mipmap-hdpi/zz.png",
                "com.yang.bruce.mumuxi",
                "原生开发，技术文档学习。",
                "https://github.com/yangxiaoge/MumuXi",
                "2016-6-27 19:38:04",
                "2019-9-17 09:34:10",
                6,
                false
        ));
        items.add(new Recommendation(
                0, "MumuXi_flutter",
                "https://raw.githubusercontent.com/yangxiaoge/wanandroid_flutter/master/assets/images/zz.png",
                "com.yang.bruce.mumuxi",
                "Flutter 版 MumuXi，是查阅技术文章与美图的一款应用，全新版本使用 Flutter 跨平台框架开发。",
                "https://android.myapp.com/myapp/detail.htm?apkName=com.yang.bruce.mumuxi",
                "2018-12-28 18:06:08",
                "2019-9-17 09:09:12",
                7.99,
                false
        ));
        items.add(new Recommendation(
                0, "Zz 天气预报",
                "https://raw.githubusercontent.com/yangxiaoge/wechat_weather/master/assets/icons/103.png",
                "微信小程序",
                "微信小程序开发的天气预报软件，目前未上架处于测试版。",
                "https://github.com/yangxiaoge/wechat_weather",
                "2017-08-28 18:20:08",
                "2018-9-19 17:09:20",
                0,
                false
        ));
        items.add(new Recommendation(
                0, "flutter_shop",
                "https://raw.githubusercontent.com/yangxiaoge/flutter_shop/master/android/app/src/main/res/mipmap-xxhdpi/ic_launcher.png",
                "com.bruce.flutter_shop",
                "\uD83D\uDD25flutter - 百姓生活+",
                "https://github.com/yangxiaoge/flutter_shop",
                "2019-03-04 11:50:01",
                "2019-4-26 17:57:32",
                60.07,
                false
        ));
        items.add(new Recommendation(
                0, "全局水印",
                "https://raw.githubusercontent.com/yangxiaoge/GlobalWaterMarker/master/app/src/main/res/mipmap-xxhdpi/ic_launcher.png",
                "com.yjn.global.watermarker",
                "手机添加全局水印",
                "https://github.com/yangxiaoge/GlobalWaterMarker",
                "2019-09-10 11:06:30",
                "2019-09-10 11:09:50",
                1.3,
                false
        ));
        items.add(new Recommendation(
                0, "应用安装播报",
                "https://raw.githubusercontent.com/yangxiaoge/app-install-listen/master/app/src/main/res/mipmap-xxhdpi/icon.png",
                "com.yjn.applisten",
                "应用安装监控并播报语音",
                "https://github.com/yangxiaoge/app-install-listen",
                "2019-09-09 14:14:30",
                "2019-09-09 14:14:30",
                1.5,
                false
        ));

        items.add(new Category("Open Source Licenses"));
        items.add(new License("LeanPush", "leancloud", License.APACHE_2, "https://github.com/leancloud/android-push-demo"));
        items.add(new License("amap", "Amap", "The AMap Software License, Version 1.0", "https://lbs.amap.com"));
        items.add(new License("fastjson", "alibaba", License.APACHE_2, "https://github.com/alibaba/fastjson"));
        items.add(new License("AndPermission", "yanzhenjie", License.APACHE_2, "https://github.com/yanzhenjie/AndPermission"));
        items.add(new License("RxJava", "ReactiveX", License.APACHE_2, "https://github.com/ReactiveX/RxJava"));
        items.add(new License("EventBus", "greenrobot", License.APACHE_2, "https://github.com/greenrobot/EventBus"));
        items.add(new License("AppUpdate", "azhon", License.APACHE_2, "https://github.com/azhon/AppUpdate"));
        items.add(new License("MultiType", "drakeet", License.APACHE_2, "https://github.com/drakeet/MultiType"));
        items.add(new License("about-page", "drakeet", License.APACHE_2, "https://github.com/drakeet/about-page"));
    }
}
