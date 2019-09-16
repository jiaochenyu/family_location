package com.yjn.familylocation.ui;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drakeet.about.AbsAboutActivity;
import com.drakeet.about.Card;
import com.drakeet.about.Category;
import com.drakeet.about.Contributor;
import com.drakeet.about.License;
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
public class AboutActivity extends AbsAboutActivity {
    @Override
    protected void onCreateHeader(@NonNull ImageView icon, @NonNull TextView slogan, @NonNull TextView version) {
        icon.setImageResource(R.mipmap.ic_logo);
        slogan.setText(getString(R.string.app_name));
        version.setText("v" + BuildConfig.VERSION_NAME);
    }

    @Override
    protected void onItemsCreated(@NonNull List<Object> items) {
        items.add(new Category("介绍与帮助"));
        items.add(new Card("\uD83D\uDC6A 定位亲人位置，守护亲人！"));

        items.add(new Category("Developers"));
        items.add(new Contributor(R.mipmap.author, "Bruce Yang", "Developer & designer", "https://yangxiaoge.github.io/"));

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
