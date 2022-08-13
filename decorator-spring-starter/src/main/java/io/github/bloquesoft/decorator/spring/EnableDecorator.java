package io.github.bloquesoft.decorator.spring;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({DecorateBeanInitialize.class})
public @interface EnableDecorator {

}