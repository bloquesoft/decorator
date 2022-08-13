package io.github.bloquesoft.testData;

import io.github.bloquesoft.decorator.action.AbstractDecoratedAction;
import io.github.bloquesoft.decorator.executor.Executor;
import io.github.bloquesoft.decorator.object.DecoratedMethod;
import io.github.bloquesoft.decorator.resource.AbstractResource;

public class TestAction extends AbstractDecoratedAction {

    public TestAction(AbstractResource resource, Executor executor) {
        super(resource, executor);
    }
}