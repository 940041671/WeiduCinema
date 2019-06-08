package com.baway.weiducinema.base;


import android.util.Log;

import com.baway.weiducinema.bean.Result;
import com.baway.weiducinema.core.RequestDataInterface;
import com.baway.weiducinema.model.IRequest;
import com.baway.weiducinema.util.RetrofitUtil;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * com.baway.rxretrofitmvpdemo.presenter
 *
 * @author 李宁康
 * @date 2019 2019/05/14 16:47
 */
public abstract class BasePresenter {
    public static RequestDataInterface.PresenterInterface presenterInterface;
    private boolean isRunning;
    private Disposable disposable;

    public BasePresenter(RequestDataInterface.PresenterInterface presenterInterface) {
        this.presenterInterface = presenterInterface;
    }

    public void requestData(final Object...args){
       if (isRunning){
            return;
        }
        isRunning=true;
        final IRequest iRequest = RetrofitUtil.getRetrofitUtil().retrofit.create(IRequest.class);
        disposable = getModel(iRequest, args)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Function<Throwable, Result>() {
                    @Override
                    public Result apply(Throwable e) throws Exception {
                        e.printStackTrace();
                        final Result result = returnExpection(e);
                        Log.d("dd", "apply: " + result.getMessage());
                        return result;
                    }
                })
                .subscribe(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) throws Exception {
                        isRunning = false;
                        if (presenterInterface == null)
                            return;
                        if (!result.getStatus().isEmpty()) {
                            presenterInterface.success(result, args);
                        } else {
                            presenterInterface.fail("拉了");
                        }
                    }
                });


    }

    public abstract Observable getModel(IRequest iRequest,Object...args);

    public static Result returnExpection(Throwable e){

        Result ex;
        if (e instanceof JsonParseException
                ||e instanceof JSONException
                ||e instanceof ParseException){
            ex=new Result("解析异常"+e.getMessage(),"1001");
        }else if(e instanceof ConnectException||e instanceof UnknownHostException){
            ex=new Result("网络错误"+e.getMessage(),"1002");
        }else{
            ex=new Result("未知错误"+e.getMessage(),"1003");
        }
        return ex;
    }

    public void cancelRequest(){
        if (disposable!=null){
            disposable.dispose();
        }
    }
    public static void cancelMemory(){
        if (presenterInterface!=null){
            presenterInterface=null;
        }
    }
}
