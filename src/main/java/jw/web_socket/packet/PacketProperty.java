package jw.web_socket.packet;

import jw.dependency_injection.Injectable;
import jw.dependency_injection.InjectionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PacketProperty
{
    int maxStringSize() default 10;
}
