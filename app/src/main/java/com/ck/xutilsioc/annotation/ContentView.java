package com.ck.xutilsioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//程序运行时，注解也能生效
@Retention(RetentionPolicy.RUNTIME)
//注解定义的位置，TYPE是定义在类上面
@Target(ElementType.TYPE)
public @interface ContentView {
    int value();
}
