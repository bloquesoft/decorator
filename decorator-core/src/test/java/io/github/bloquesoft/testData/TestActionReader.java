package io.github.bloquesoft.testData;

import io.github.bloquesoft.decorator.action.AbstractDecoratedAction;
import io.github.bloquesoft.decorator.action.DecorateActionReader;
import io.github.bloquesoft.decorator.object.DecoratedMethod;
import io.github.bloquesoft.decorator.resource.ResourceRegister;
import org.powermock.api.mockito.PowerMockito;

import java.lang.reflect.Method;

public class TestActionReader implements DecorateActionReader {

    @Override
    public AbstractDecoratedAction read(Method method, ResourceRegister resourceRegister) {

        return new TestAction(new TestResource("resource"), new TestExecutor());
    }
}
