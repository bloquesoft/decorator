package io.github.bloquesoft.decorator.distributedLock.executor;

import io.github.bloquesoft.decorator.distributedLock.action.DecoratedDistributedLockAction;
import io.github.bloquesoft.decorator.action.AbstractDecoratedAction;
import io.github.bloquesoft.decorator.executor.ExecutePhase;
import io.github.bloquesoft.decorator.executor.Executor;

public abstract class AbstractDistributedLockExecutor implements Executor {

    protected abstract void onReady(DecoratedDistributedLockAction action, Object[] args);

    @Override
    public void onReady(AbstractDecoratedAction action, Object[] args) {
        this.onReady((DecoratedDistributedLockAction) action, args);
    }

    protected abstract void onBefore(DecoratedDistributedLockAction action, Object[] args);

    @Override
    public void doBefore(AbstractDecoratedAction action, Object[] args) {
        this.onBefore((DecoratedDistributedLockAction) action, args);
    }

    protected abstract void doAfter(DecoratedDistributedLockAction action, Object[] args);

    @Override
    public void doAfter(AbstractDecoratedAction action, Object[] args) {
        this.doAfter((DecoratedDistributedLockAction) action, args);
    }

    protected abstract void onError(DecoratedDistributedLockAction action, Object[] args);

    @Override
    public void onError(AbstractDecoratedAction action, Object[] args, ExecutePhase executePhase, Exception exception) {
        this.onError((DecoratedDistributedLockAction) action, args);
    }

    protected abstract void onFinally(DecoratedDistributedLockAction action, Object[] args);

    @Override
    public void onFinally(AbstractDecoratedAction action, Object[] args) {
        this.onFinally((DecoratedDistributedLockAction) action, args);
    }
}
