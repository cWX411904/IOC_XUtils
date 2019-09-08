package com.ck.xutilsioc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理
 * 前提需要搞清楚代理谁？
 * 这里是代理了MainActivity对象的onClick方法
 */
public class ListenerInvocationHandler implements InvocationHandler {

    private Object activity;
    private Method activityMethod;

    public ListenerInvocationHandler(Object activity, Method activityMethod) {
        this.activity = activity;
        this.activityMethod = activityMethod;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return activityMethod.invoke(activity, args);
    }
}
