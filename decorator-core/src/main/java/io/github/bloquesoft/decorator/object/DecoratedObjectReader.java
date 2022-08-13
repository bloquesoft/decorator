package io.github.bloquesoft.decorator.object;

import io.github.bloquesoft.decorator.action.AbstractDecoratedAction;
import io.github.bloquesoft.decorator.action.DecorateActionDeclare;
import io.github.bloquesoft.decorator.action.DecorateActionDeclareRegister;
import io.github.bloquesoft.decorator.annotation.Decorate;
import io.github.bloquesoft.decorator.resource.ResourceRegister;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class DecoratedObjectReader {

    private final DecorateActionDeclareRegister actionDeclareRegister;

    private final ResourceRegister resourceRegister;

    public DecoratedObjectReader(DecorateActionDeclareRegister actionDeclareRegister, ResourceRegister resourceRegister)
    {
        this.actionDeclareRegister = actionDeclareRegister;
        this.resourceRegister = resourceRegister;
    }

    public DecoratedObject read(Object object) {
        if (object.getClass().isAnnotationPresent(Decorate.class)) {
            DecoratedObject decoratedObject = new DecoratedObject(object);
            for (Method method : object.getClass().getDeclaredMethods()) {
                DecoratedMethod decoratedMethod = null;
                for (Annotation annotation : method.getDeclaredAnnotations()) {
                    if (actionDeclareRegister.contain(annotation.annotationType())) {
                        DecorateActionDeclare declare = actionDeclareRegister.get(annotation.annotationType());
                        if (decoratedMethod == null) {
                            decoratedMethod = new DecoratedMethod(method);
                        }
                        AbstractDecoratedAction action = declare.getActionReader().read(method, resourceRegister);
                        Assert.notNull(action, "Reader return null, reader:" + declare.getActionReader().toString() + ", method:" + method.getName());
                        decoratedMethod.addAction(action);
                    }
                }
                if (decoratedMethod != null) {
                    decoratedObject.addDecoratedMethod(decoratedMethod);
                }
            }
            return decoratedObject;
        }
        return null;
    }
}