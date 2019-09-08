package com.ck.xutilsioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)

//该注解在另一个注解上使用
@Target(ElementType.ANNOTATION_TYPE)
public @interface EventBase {

    //事件三要素之一的：订阅者
    String listenerSetter();

    //事件三要素之二：事件源
    Class<?> listenerType();

    //事件三要素之三：事件类型（长按或者短按）
    String callbackMethod();
}
