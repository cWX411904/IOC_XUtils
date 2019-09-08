package com.ck.xutilsioc;

import android.view.View;

import com.ck.xutilsioc.annotation.ContentView;
import com.ck.xutilsioc.annotation.EventBase;
import com.ck.xutilsioc.annotation.ViewInject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class InjectUtils {

    public static void inject(Object context) {

        injectLayout(context);
        injectView(context);
        injectClick(context);
    }

    private static void injectClick(Object context) {
        Class<?> aClass = context.getClass();
        //先拿到MainActivity的所有方法
        Method[] methods = aClass.getDeclaredMethods();
        for (Method method : methods) {
            //找到被注解的方法
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                //annotation 就是类似：OnClick这个注解
                Class<? extends Annotation> annotationType = annotation.annotationType();
                //找到注解上面的注解（EventBase）
                EventBase eventBase = annotationType.getAnnotation(EventBase.class);
                if (eventBase == null) {
                    continue;
                }
                //获取事件三要素
                String listenerSetter = eventBase.listenerSetter();
                Class<?> listenerType = eventBase.listenerType();
                String callbackMethod = eventBase.callbackMethod();

                //事件三要素有了，现在还差调用这个事件的主角（textView）
                Method valueMethod = null;
                try {
                    //怎么取拿到view的对象呢？用反射，拿到value，然后再通过viewId反射拿对象
                    valueMethod = annotationType.getDeclaredMethod("vaule");

                    int[] viewId = (int[]) valueMethod.invoke(annotation);
                    for (int id : viewId) {
                        Method findViewById = aClass.getMethod("findViewById", int.class);
                        View view = (View) findViewById.invoke(context, id);
                        if (view == null) continue;

                        Method onClickMethod = view.getClass().getMethod(listenerSetter, listenerType);

                        //这里用到了动态代理，在使用动态代理之前一定要搞清楚你代理的是哪个对象的哪个方法
                        //现在我们需要代理MainActivity对象的click方法,mothod是MainActivity内部的方法
                        ListenerInvocationHandler listenerInvocationHandler =
                                new ListenerInvocationHandler(context, method);

                        Object proxyInstance =
                                Proxy.newProxyInstance(listenerType.getClassLoader(),
                                        new Class[]{listenerType},
                                        listenerInvocationHandler);
                        onClickMethod.invoke(view, proxyInstance);

                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    private static void injectView(Object context) {
//
//        Class<?> aClass = context.getClass();
//        Field[] fields =
//                aClass.getDeclaredFields();
//
//        for (Field field : fields) {
//            ViewInject viewInject = field.getAnnotation(ViewInject.class);
//            if (null != viewInject) {
//                int valueId = viewInject.value();
//                try {
//                    Method method = aClass.getMethod("findViewById", int.class);
//                    View view = (View) method.invoke(context, valueId);
//                    field.setAccessible(true);
//                    field.set(context, view);
//                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }
//    }

    private static void injectView(Object context) {
        Class<?> aClass=context.getClass();
        Field[] fields=aClass.getDeclaredFields();
//        MainActivity mainActivity = (MainActivity) context;

        for (Field field:fields)
        {
            ViewInject viewInject = field.getAnnotation(ViewInject.class);
            if (viewInject != null) {
                int valueId = viewInject.value();
                try {
                    Method method=aClass.getMethod("findViewById",int.class);
                    View view = (View) method.invoke(context, valueId);
//                    View view= mainActivity.findViewById(valueId);
                    field.setAccessible(true);
                    field.set(context,view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

//    private static void injectLayout(Object context) {
//        int layoutId=0;
//        Class<?> clazz=context.getClass();
//        ContentView contentView = clazz.getAnnotation(ContentView.class);
//        if (contentView != null) {
//            layoutId = contentView.value();
//
//            try {
//                Method method = context.getClass().getMethod("setContentView", int.class);
//                method.invoke(context, layoutId);
//            } catch ( Exception e) {
//                e.printStackTrace();
//            }
//
//
//        }
//    }

    private static void injectLayout(Object context) {
        int layoutId = 0;
        Class<?> clazz = context.getClass();
        //反射找到class类中使用的ContentView注解
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (null != contentView) {
            //拿到注解的value（R.layout.activity_main）
            layoutId = contentView.value();
            try {
                //反射找到setContentView的方法
                Method method = context.getClass().getMethod("setContentView", int.class);
                //最后，反射执行setContentView
                method.invoke(context, layoutId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
