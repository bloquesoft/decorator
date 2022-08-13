package io.github.bloquesoft.decorator;

import io.github.bloquesoft.decorator.action.DecorateActionDeclare;
import io.github.bloquesoft.decorator.action.DecorateActionDeclareRegister;
import io.github.bloquesoft.decorator.object.DecoratedObject;
import io.github.bloquesoft.decorator.object.DecoratedObjectReader;
import io.github.bloquesoft.decorator.object.DecoratedObjectRegister;
import io.github.bloquesoft.decorator.proxy.jdk.JdkProxyObjectFactory;
import io.github.bloquesoft.decorator.resource.ResourceRegister;
import io.github.bloquesoft.testData.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ProxyObjectFactoryTest {

    @Test
    public void createProxyObject() {

        DecorateActionDeclare actionDeclare = new DecorateActionDeclare(TestActionAnnotation.class, new TestActionReader());

        DecorateActionDeclareRegister declareRegister = new DecorateActionDeclareRegister();
        declareRegister.register(actionDeclare);

        TestResource resource = new TestResource("testName");
        ResourceRegister resourceRegister = new ResourceRegister();
        resourceRegister.register(resource);

        TestClass targetObject = new TestClass();
        DecoratedObjectReader objectReader = new DecoratedObjectReader(declareRegister,resourceRegister);
        DecoratedObject decoratedObject = objectReader.read(targetObject);

        DecoratedObjectRegister objectRegister = new DecoratedObjectRegister();
        objectRegister.register(decoratedObject);

        JdkProxyObjectFactory objectFactory = new JdkProxyObjectFactory(objectRegister);
        Object proxyObject = objectFactory.createProxyObject(targetObject);

        ((TestClassInterface)proxyObject).action();



    }
}