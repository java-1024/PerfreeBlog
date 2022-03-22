package com.perfree.dataBase;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Component
public @interface Table {
    String value();
    Index[] index() default {};
    UniqueConstraints[] uniqueConstraints() default {};
}
