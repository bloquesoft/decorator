package io.github.bloquesoft.decorator.spring;

import io.github.bloquesoft.decorator.distributedLock.action.DecoratedDistributedLockActionReader;
import io.github.bloquesoft.decorator.distributedLock.annotation.DistributedLock;
import io.github.bloquesoft.decorator.action.DecorateActionDeclare;
import io.github.bloquesoft.decorator.action.DecorateActionDeclareRegister;
import io.github.bloquesoft.decorator.annotation.Decorate;
import io.github.bloquesoft.decorator.object.DecoratedObject;
import io.github.bloquesoft.decorator.object.DecoratedObjectReader;
import io.github.bloquesoft.decorator.object.DecoratedObjectRegister;
import io.github.bloquesoft.decorator.proxy.jdk.JdkProxyObjectFactory;
import io.github.bloquesoft.decorator.resource.ResourceRegister;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Import;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@Import({
        DecorateActionDeclareRegister.class,
        DecoratedObjectReader.class,
        DecoratedObjectRegister.class}
)
public class DecorateBeanInitialize implements BeanPostProcessor {

    @Autowired
    private DecorateActionDeclareRegister declareRegister;

    @Autowired
    private ResourceRegister resourceRegister;

    private DecoratedObjectReader decoratedObjectReader;

    public DecorateBeanInitialize() {
    }

    private DecoratedObjectReader getDecoratedObjectReader() {
        if (decoratedObjectReader == null) {
            decoratedObjectReader = new DecoratedObjectReader(this.declareRegister, this.resourceRegister);
        }
        return decoratedObjectReader;
    }

    @Autowired
    private DecoratedObjectRegister decoratedObjectRegister;

    private JdkProxyObjectFactory jdkProxyObjectFactory;

    private JdkProxyObjectFactory getJdkProxyObjectFactory() {
        if (jdkProxyObjectFactory == null) {
            jdkProxyObjectFactory = new JdkProxyObjectFactory(this.decoratedObjectRegister);
        }
        return jdkProxyObjectFactory;
    }

    @PostConstruct
    private void init() {
        declareRegister.register(new DecorateActionDeclare(DistributedLock.class, new DecoratedDistributedLockActionReader()));
    }

    @Nullable
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (bean.getClass().isAnnotationPresent(Decorate.class)) {
            DecoratedObject decoratedObject = this.getDecoratedObjectReader().read(bean);
            if (decoratedObject != null && decoratedObject.getDecoratedMethodNumber() > 0) {
                decoratedObjectRegister.register(decoratedObject);
                Object proxy = getJdkProxyObjectFactory().createProxyObject(bean);
                log.info("Decorated bean " + beanName + " initialized");
                return proxy;
            } else {
                log.error("No decorate action defined in decorate bean " + beanName);
            }
        }
        return bean;
    }
}