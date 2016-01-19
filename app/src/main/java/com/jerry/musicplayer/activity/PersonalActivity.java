package com.jerry.musicplayer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.jerry.musicplayer.R;

/**
 * Created by Jerry on 2016/1/3.
 */
public class PersonalActivity extends Activity implements View.OnClickListener {
    public final String mTag = "PersonalActivity";

    private TextView mPerBack;
    private TextView mCount;
    private Switch mIsQuality;

    /**
     * 创建activity时执行的
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        initView();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        mPerBack = (TextView) findViewById(R.id.per_back);
        mCount = (TextView) findViewById(R.id.per_account);
        mIsQuality = (Switch) findViewById(R.id.per_is_quality);

        mPerBack.setOnClickListener(this);
        mCount.setOnClickListener(this);
        mIsQuality.setOnClickListener(this);
    }

    /**
     * 界面加载完成后执行的方法
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 暂停时执行的方法
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 销毁activity时调用
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        Intent mIntent;
        switch (v.getId()) {
            case R.id.per_back://返回
                mIntent=new Intent(PersonalActivity.this, MainActivity.class);
                mIntent.putExtra("username", "zhujj");
                mIntent.putExtra("psd", "123");
                startActivity(mIntent);
                break;
            case R.id.per_account://账户


                break;
            case R.id.per_is_quality://是否关闭


                break;
        }

    }
}
