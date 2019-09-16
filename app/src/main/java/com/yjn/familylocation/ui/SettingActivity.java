package com.yjn.familylocation.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.yjn.familylocation.R;
import com.yjn.familylocation.bean.Constants;
import com.yjn.familylocation.util.SPUtils;

/**
 * <pre>
 *     author: Bruce_Yang
 *     blog  : https://yangjianan.gitee.io
 *     time  : 2019/9/16
 *     desc  : 设置页面
 * </pre>
 */
public class SettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        this.setTitle("设置");
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        String installationId = SPUtils.getInstance(Constants.SP_NAME).getString(Constants.INSTALLATIONID_SP,
                "");
        TextView msg = findViewById(R.id.msg_tv);
        msg.setText("欢迎使用家人守护！\n\n" + "本机installationid:\n\n" + installationId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
