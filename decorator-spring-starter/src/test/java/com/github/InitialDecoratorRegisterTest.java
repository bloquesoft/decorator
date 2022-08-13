package com.github;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class InitialDecoratorRegisterTest extends TestCase {

    @Autowired
    private TargetClassInterface targetClassInterface;

    @Test
    public void init() {
        LockEntity le = new LockEntity();
        targetClassInterface.doDistributedLock(le);
    }
}