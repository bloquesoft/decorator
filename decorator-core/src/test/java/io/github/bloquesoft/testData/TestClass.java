package io.github.bloquesoft.testData;

import io.github.bloquesoft.decorator.annotation.Decorate;

@Decorate
public class TestClass implements TestClassInterface{

    @TestActionAnnotation
    public void action() {

        System.out.println("do action");
    }
}
