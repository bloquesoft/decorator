package io.github.bloquesoft.decorator.action;

import io.github.bloquesoft.decorator.common.register.SyncRegister;

import java.lang.annotation.Annotation;

public class DecorateActionDeclareRegister extends SyncRegister<Class<? extends Annotation>, DecorateActionDeclare> {

    @Override
    public void register(DecorateActionDeclare declare) {
        super.register(declare);
    }
}