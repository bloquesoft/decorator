package io.github.bloquesoft.decorator.executor;

import java.util.HashMap;
import java.util.Map;

public class ExecutorFactory {

    private final Map<Class<?>, Executor> MAP = new HashMap<>();

    private static final ExecutorFactory factory = new ExecutorFactory();

    public static ExecutorFactory singleton(){
        return factory;
    }

    public <T extends Executor> T create(Class<T> executorClass)
    {
        if (!MAP.containsKey(executorClass))
        {
            synchronized (ExecutorFactory.class)
            {
                if (!MAP.containsKey(executorClass))
                {
                    try {
                        T executor = executorClass.newInstance();
                        MAP.put(executorClass, executor);
                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return (T)MAP.get(executorClass);
    }
}
