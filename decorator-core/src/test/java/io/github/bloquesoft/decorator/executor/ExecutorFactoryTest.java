package io.github.bloquesoft.decorator.executor;

import io.github.bloquesoft.decorator.action.AbstractDecoratedAction;
import io.github.bloquesoft.testData.TestExecutor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExecutorFactoryTest {

    @Test
    void create() {

        ExecutorFactory executorFactory = new ExecutorFactory();

        Executor executor1 = executorFactory.create(TestExecutor.class);
        Executor executor2 = executorFactory.create(TestExecutor.class);
        Assertions.assertEquals(executor1, executor2);
    }
}