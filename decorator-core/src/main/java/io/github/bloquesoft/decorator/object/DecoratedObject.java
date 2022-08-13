package io.github.bloquesoft.decorator.object;

import io.github.bloquesoft.decorator.common.register.Registrable;
import lombok.Getter;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DecoratedObject implements Registrable<Object> {

    @Getter
    private final Object targetObject;

    public Integer getDecoratedMethodNumber() {
        return this.map.size();
    }

    private final Map<String, DecoratedMethod> map = new ConcurrentHashMap<>();

    public DecoratedObject(Object targetBean) {
        this.targetObject = targetBean;
    }

    public void addDecoratedMethod(DecoratedMethod decoratedMethod) {
        String methodName = decoratedMethod.getMethod().getName();
        if (!map.containsKey(methodName)) {
            map.put(methodName, decoratedMethod);
        }
    }

    public DecoratedMethod getDecoratedMethod(Method method) {
        return map.get(method.getName());
    }

    @Override
    public Object getContentKey() {
        return this.targetObject;
    }
}