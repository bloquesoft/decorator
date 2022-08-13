package io.github.bloquesoft.decorator.action;

import io.github.bloquesoft.decorator.common.register.Registrable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.annotation.Annotation;

@AllArgsConstructor
public class DecorateActionDeclare implements Registrable<Class<? extends Annotation>> {

    @Getter
    private Class<? extends Annotation> actionAnnotationClass;

    @Getter
    private DecorateActionReader actionReader;

    @Override
    public Class<? extends Annotation> getContentKey() {
        return actionAnnotationClass;
    }
}