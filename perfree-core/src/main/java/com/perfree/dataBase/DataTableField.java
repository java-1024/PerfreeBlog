package com.perfree.dataBase;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface DataTableField {
    String name();
    String type();
    int length() default 0;
    String defaultValue() default "";
    boolean isEmpty() default true;
    boolean isPrimary() default false;
    boolean autoIncrement() default false;
    String comment() default "";
}
