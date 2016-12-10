package com.meishubao.app.common.commonactivity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by kongweixian on 16/8/19.
 * fragment路径接口
 */
//
//@Documented
//@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FragmentPath {
    String value() default "";
}