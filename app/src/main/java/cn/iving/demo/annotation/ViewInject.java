package cn.iving.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;


import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * custom annotation
 */
@Retention(RUNTIME)  //运行时注解
@Target(ElementType.TYPE) //作用域：类，接口
public @interface ViewInject {
    int mainLayoutId() default -1;
}
