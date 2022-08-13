package io.github.bloquesoft.decorator.object;

import io.github.bloquesoft.decorator.action.AbstractDecoratedAction;
import lombok.Getter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class DecoratedMethod {

    @Getter
    private final Method method;

    private final List<AbstractDecoratedAction> actionList = new ArrayList<>();

    public void addAction(AbstractDecoratedAction action) {
        this.actionList.add(action);
    }

    public DecoratedMethod(Method method) {
        this.method = method;
    }

    public List<AbstractDecoratedAction> getDecoratedActions() {
        return actionList;
    }
}