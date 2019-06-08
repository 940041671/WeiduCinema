package com.baway.weiducinema.core;

/**
 * com.baway.rxretrofitmvpdemo.core
 *
 * @author 李宁康
 * @date 2019 2019/05/14 16:54
 */
public interface RequestDataInterface {
    interface PresenterInterface{
        void success(Object obj, Object... args);
        void fail(String msg);
    }
}
