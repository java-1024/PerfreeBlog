package com.perfree.dataBase;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
@Documented
public @interface UniqueConstraints {
    String[] value();
}
