package io.github.bloquesoft.decorator.distributedLock.action;

import io.github.bloquesoft.decorator.action.AbstractDecoratedAction;
import io.github.bloquesoft.decorator.executor.ExecutePhase;
import io.github.bloquesoft.decorator.executor.Executor;

public class TestExecutor implements Executor
{

    @Override
    public void onReady(AbstractDecoratedAction action, Object[] args) {

    }

    @Override
    public void doBefore(AbstractDecoratedAction action, Object[] args) {

    }

    @Override
    public void doAfter(AbstractDecoratedAction action, Object[] args) {

    }

    @Override
    public void onError(AbstractDecoratedAction action, Object[] args, ExecutePhase executePhase, Exception exception) {

    }

    @Override
    public void onFinally(AbstractDecoratedAction action, Object[] args) {

    }
}
