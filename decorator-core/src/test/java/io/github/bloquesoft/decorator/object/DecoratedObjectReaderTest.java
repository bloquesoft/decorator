package io.github.bloquesoft.decorator.object;

import io.github.bloquesoft.decorator.action.AbstractDecoratedAction;
import io.github.bloquesoft.decorator.action.DecorateActionDeclare;
import io.github.bloquesoft.decorator.action.DecorateActionDeclareRegister;
import io.github.bloquesoft.decorator.action.DecorateActionReader;
import io.github.bloquesoft.decorator.annotation.Decorate;
import io.github.bloquesoft.decorator.resource.ResourceRegister;
import io.github.bloquesoft.testData.TestActionAnnotation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.powermock.api.mockito.PowerMockito;

import java.lang.reflect.Method;

import static org.mockito.ArgumentMatchers.any;

class DecoratedObjectReaderTest {

    @Test
    void read() {

        DecorateActionDeclareRegister declareRegister = PowerMockito.mock(DecorateActionDeclareRegister.class);
        DecorateActionReader actionReader = PowerMockito.mock(DecorateActionReader.class);
        AbstractDecoratedAction action = PowerMockito.mock(AbstractDecoratedAction.class);
        PowerMockito.when(actionReader.read(any(Method.class), any(ResourceRegister.class))).thenReturn(action);
        PowerMockito.when(declareRegister.contain(TestActionAnnotation.class)).thenReturn(true);
        PowerMockito.when(declareRegister.get(TestActionAnnotation.class)).thenReturn(new DecorateActionDeclare(TestActionAnnotation.class, actionReader));
        ResourceRegister resourceRegister = PowerMockito.mock(ResourceRegister.class);
        DecoratedObjectReader reader = new DecoratedObjectReader(declareRegister, resourceRegister);
        DecoratedObject nullDecorateObject =  reader.read(new NoDecorateClass());
        Assertions.assertNull(nullDecorateObject);
        DecoratedObject decoratedObject =  reader.read(new DecorateClass());
        Assertions.assertNotNull(decoratedObject);
        try {
            Method method = DecorateClass.class.getMethod("method");
            Assertions.assertEquals(decoratedObject.getDecoratedMethod(method).getDecoratedActions().size(),1);
            Assertions.assertEquals(decoratedObject.getDecoratedMethod(method).getDecoratedActions().get(0), action);
        } catch (NoSuchMethodException e) {
            Assertions.assertTrue(false, "error");
            throw new RuntimeException(e);
        }
    }

    private class NoDecorateClass { }

    @Decorate
    private class DecorateClass{

        @TestActionAnnotation
        public void method(){

        }
    }
}