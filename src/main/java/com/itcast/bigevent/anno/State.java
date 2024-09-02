package com.itcast.bigevent.anno;


import com.itcast.bigevent.validation.StateValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented //元注解
@Target(ElementType.FIELD)  //元注解
@Retention(RetentionPolicy.RUNTIME) //元注解
@Constraint(validatedBy = StateValidation.class)
public @interface State {

    String message() default "文章状态只能是：已发布或者草稿";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
