package io.github.bloquesoft.decorator.executor;

public enum ExecutePhase {

    OnReady,
    DoBefore,
    DoTargetMethod,
    DoAfter,

    OnFinally
}
