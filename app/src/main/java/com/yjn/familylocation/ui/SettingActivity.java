package com.yjn.familylocation.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yjn.familylocation.R;
import com.yjn.familylocation.bean.Constants;
import com.yjn.familylocation.util.MMKVUtil;

/**
 * <pre>
 *     author: Bruce_Yang
 *     blog  : https://yangjianan.gitee.io
 *     time  : 2019/9/16
 *     desc  : 设置页面
 * </pre>
 */
public class SettingActivity extends AppCompatActivity {
    private static final String TAG = SettingActivity.class.getSimpleName();
    private ImageView idIV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        this.setTitle("设置");
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        String installationId = MMKVUtil.getString(Constants.INSTALLATIONID_SP,
                "");
        TextView msg = findViewById(R.id.msg_tv);
        msg.setText("欢迎使用家人守护！\n\n" + "本机installationid:\n\n" + installationId);

        idIV = findViewById(R.id.id_iv);
        Glide.with(SettingActivity.this)
                .load(Constants.INSTALLATIONID_BARCODE + installationId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(idIV);
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
