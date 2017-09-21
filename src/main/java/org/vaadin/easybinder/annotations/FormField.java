package org.vaadin.easybinder.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(FIELD)
@Retention(RUNTIME)
@Documented
public @interface FormField {

    int order() default 0;

    String formLayout() default "";

    // Alignment align() default Alignment.MIDDLE_LEFT;

    // String i18n() default "";
}