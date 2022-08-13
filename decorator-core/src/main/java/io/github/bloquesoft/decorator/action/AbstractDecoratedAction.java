package io.github.bloquesoft.decorator.action;

import io.github.bloquesoft.decorator.executor.Executor;
import io.github.bloquesoft.decorator.resource.AbstractResource;
import lombok.Getter;
import org.springframework.util.Assert;

public abstract class AbstractDecoratedAction
{
    @Getter
    private final AbstractResource resource;

    @Getter
    private final Executor executor;

    public AbstractDecoratedAction(AbstractResource resource, Executor executor){

        Assert.notNull(resource,"");
        this.resource = resource;
        Assert.notNull(executor, "");
        this.executor = executor;
    }
}