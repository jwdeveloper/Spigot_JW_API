package jw.api.data.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Repeatable(DescriptionContainer.class)
@Retention(RetentionPolicy.RUNTIME)
public  @interface Description
{
    public String value() default "";
}
