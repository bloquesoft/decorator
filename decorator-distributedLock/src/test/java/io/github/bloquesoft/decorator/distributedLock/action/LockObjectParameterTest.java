package io.github.bloquesoft.decorator.distributedLock.action;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

class LockObjectParameterTest {

    @Test
    void create() {

        Method method = Class.class.getDeclaredMethods()[0];
        InstanceParameter parameter1 = InstanceParameter.create(method, "p1");
        Assertions.assertEquals(parameter1.getParameterName(), "p1");
        Assertions.assertEquals(parameter1.getParameterIndex(), 0);
        InstanceParameter parameter2 = InstanceParameter.create(method, "p3");
        Assertions.assertEquals(parameter2.getParameterName(), "p3");
        Assertions.assertEquals(parameter2.getParameterIndex(), 2);
    }

    public class Class {

        public void method1(String p1, String p2, String p3){

        }
    }
}