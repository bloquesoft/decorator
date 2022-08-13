package io.github.bloquesoft.testData;

import io.github.bloquesoft.decorator.action.AbstractDecoratedAction;
import io.github.bloquesoft.decorator.executor.ExecutePhase;
import io.github.bloquesoft.decorator.executor.Executor;

public class TestExecutor implements Executor {

    @Override
    public void onReady(AbstractDecoratedAction action, Object[] args) {
        System.out.println("do test ready");
    }

    @Override
    public void doBefore(AbstractDecoratedAction action, Object[] args) {
        System.out.println("do test before");
    }

    @Override
    public void doAfter(AbstractDecoratedAction action, Object[] args) {
        System.out.println("do test after");
    }

    @Override
    public void onError(AbstractDecoratedAction action, Object[] args, ExecutePhase executePhase, Exception exception) {
        System.out.println("do test error");
    }

    @Override
    public void onFinally(AbstractDecoratedAction action, Object[] args) {
        System.out.println("do test finally");
    }
}
