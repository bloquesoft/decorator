package io.github.bloquesoft.decorator.action;

import io.github.bloquesoft.testData.TestActionAnnotation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DecorateActionDeclareRegisterTest {

    @Test
    void register() {
        DecorateActionDeclareRegister register = new DecorateActionDeclareRegister();

        DecorateActionDeclare declare = new DecorateActionDeclare(TestActionAnnotation.class, null);
        register.register(declare);
        DecorateActionDeclare declare1 = new DecorateActionDeclare(TestActionAnnotation.class, null);
        register.register(declare1);
        DecorateActionDeclare returnDeclare = register.get(TestActionAnnotation.class);
        Assertions.assertEquals(returnDeclare, declare);
        Assertions.assertNotEquals(returnDeclare, declare1);
    }
}