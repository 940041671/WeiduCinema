package com.baway.weiducinema.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * com.baway.weiducinema.activity
 *
 * @author 李宁康
 * @date 2019 2019/06/08 19:25
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setView());
        ButterKnife.bind(this);
        initView();
        initData();
    }
    public abstract int setView();
    public abstract void initView();
    public abstract void initData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BasePresenter.cancelMemory();
    }
}
