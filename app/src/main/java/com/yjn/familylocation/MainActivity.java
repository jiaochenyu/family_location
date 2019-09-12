package com.yjn.familylocation;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yjn.familylocation.bean.Constants;
import com.yjn.familylocation.event.RequestEvent;
import com.yjn.familylocation.service.BackLocationService;
import com.yjn.familylocation.service.BackPushService;
import com.yjn.familylocation.util.GDLocationUtil;
import com.yjn.familylocation.util.SPUtils;
import com.yjn.familylocation.util.ToastUtils;
import com.yjn.familylocation.util.Utils;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * <pre>
 *     author: Bruce_Yang
 *     blog  : https://yangjianan.gitee.io
 *     time  : 2019/9/12
 *     desc  : 首页
 * </pre>
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: 2019/9/12 动态授权之后初始化下面的所有逻辑
        new RxPermissions(this).request(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (!aBoolean) {
                            ToastUtils.showShort("权限不足，无法使用！");
                        } else {
                            // 定位工具初始化
                            GDLocationUtil.init(MainActivity.this);
                            if (!Utils.isServiceRunning(BackPushService.class.getName())) {
                                startService(new Intent(MainActivity.this, BackPushService.class));
                            }

                            if (!Utils.isServiceRunning(BackLocationService.class.getName())) {
                                startService(new Intent(MainActivity.this, BackLocationService.class));
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String installationId = SPUtils.getInstance(Constants.SP_NAME).getString(Constants.INSTALLATIONID_SP,
                "");
        TextView msg = findViewById(R.id.msg_tv);
        msg.setText("欢迎使用家人守护！\n\n" + "本机installationid:\n\n" + installationId);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 不退出程序，进入后台
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void getLocation(View view) {
        EditText targetId = findViewById(R.id.target_installid);
        //接收方 id
        String targetInstallationId = SPUtils.getInstance(Constants.SP_NAME).getString(Constants.TARGET_INSTALLATIONID_SP,
                targetId.getText().toString().trim());
        //发送“请求定位”消息
        EventBus.getDefault().post(new RequestEvent(targetInstallationId));
    }
}
