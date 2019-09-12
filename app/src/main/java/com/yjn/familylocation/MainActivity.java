package com.yjn.familylocation;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.yjn.familylocation.bean.Constants;
import com.yjn.familylocation.bean.MsgBean;
import com.yjn.familylocation.event.GetLocationEvent;
import com.yjn.familylocation.event.RequestEvent;
import com.yjn.familylocation.service.BackLocationService;
import com.yjn.familylocation.service.BackPushService;
import com.yjn.familylocation.util.SPUtils;
import com.yjn.familylocation.util.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

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

        if (!Utils.isServiceRunning(BackPushService.class.getName())) {
            startService(new Intent(this, BackPushService.class));
        }

        if (!Utils.isServiceRunning(BackLocationService.class.getName())) {
            startService(new Intent(this, BackLocationService.class));
        }
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
