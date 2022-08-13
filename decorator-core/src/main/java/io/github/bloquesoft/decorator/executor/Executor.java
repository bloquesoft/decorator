package io.github.bloquesoft.decorator.executor;

import io.github.bloquesoft.decorator.action.AbstractDecoratedAction;

public interface Executor {

    void onReady(AbstractDecoratedAction action, Object[] args);

    void doBefore(AbstractDecoratedAction action, Object[] args);

    void doAfter(AbstractDecoratedAction action, Object[] args);

    void onError(AbstractDecoratedAction action, Object[] args, ExecutePhase executePhase, Exception exception);

    void onFinally(AbstractDecoratedAction action, Object[] args);
}