package com.baway.weiducinema.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baway.weiducinema.base.BaseActivity;

/**
 * com.baway.weiducinema.fragment
 *
 * @author 李宁康
 * @date 2019 2019/06/08 19:37
 */
public abstract class BaseFragment extends Fragment {
    public BaseActivity mActivity;

    public abstract int setView();

    public abstract void initView(View view);

    public abstract void initData(Bundle savedInstanceState);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(setView(), container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        BasePresenter.cancelMemory();
    }
}
