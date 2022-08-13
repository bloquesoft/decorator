## Decorator简介
decorator是基于Java开发、装饰器模式思想，对java对象以动态代理的方式扩展常用的装饰行为。常用的装饰行为可以是分布式锁，数据同步到消息队列等.即将程序的公共行为、非功能性行为以装饰扩展的方式实现。降低业务开发的难度和重复性代码。
## Decorator设计思想
每个Method可以定义一个或多个Action扩展行为，如分布式锁控制行为DistributedLock。
一个Action对应一个Executor执行器和一个Resource资源。一个Action可以通过不同的Executor、Resourc组合实现。如分布式锁可以Redis资源实现、也可以是Zookeeper资源实现。不同的资源对应不同的Executor实现。
## 快速开始
1. Maven引用
~~~
<>
~~~
2. 添加 @EnableDecorator注解
~~~ 
@SpringBootApplication
@EnableDecorator
public class Application extends SpringBootServletInitializer {

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
~~~
3. 注册资源

    注册需要用到的资源，如Redis、Zookeeper等。
~~~ 
@Configuration
public class DecorateConfiguration {

    @Bean
    public ResourceRegister createResourceRegister(){
        ResourceRegister resourceRegister = new ResourceRegister();
        resourceRegister.register(new SingleServerRedisResource("localRedis", "redis://localhost:6380"));
        return resourceRegister;
    }
}
~~~ 
系统必须定义ResourceRegister Bean，否则会报错。 
执行器会从ResourceRegister Bean中根据资源name获取需要的资源。
每个资源必须定义名称，且名称不能重复。

## 分布式锁
1. 全局锁，即当前系统内只有一个线程可以执行的分布式锁控制，
~~~ 
@Decorate
@Component
public class TestClass implements TestClassInterface {

    @DistributedLock(
            resourceName = "localRedis",
            lockType = DistributedLockType.GlobalLock,
            globalLockKey = "globalKey",
            executorClass = RedissonDistributedLockExecutor.class)
    @Override
    public void doDistributedLock(LockEntity entity) {

    }
}
~~~
@Decorate注解必须配置在类中，否则系统不对该类进行分析
@DistributedLock：表示对方法实现分布式功能。
resourceName：分布式涉及的资源，其值与资源注册器中配置的资源name一致。
lockType：DistributedLockType.GlobalLock，表示当前分布式锁为全局锁。
globalLockKey：全局锁在Redis中的key值。
executeClass:系统内置的对应Redis资源的执行器类，该类继承自Executor接口。
~~~

2. 实体锁，即当前进程中只有获取实体对象锁的线程可以运行，如出库处理，只有获取相应产品库存的线程可以处理库存操作。
~~~ 
@Decorate
@Component
public class TestClass implements TestClassInterface {

    @DistributedLock(
         resourceName = "localRedis",
         lockType = DistributedLockType.InstanceLock,
         instanceParameterName = "entity",
         executorClass = RedissonDistributedLockExecutor.class)
    @Override
    public void doDistributedLock(LockEntity entity) {

    }
}
~~~
lockType：DistributedLockType.GlobalLock，表示当前分布式锁为实体锁。
instanceParameterName：实体对象对应方法参数的参数名，即配置那个参数对象是实体锁，
注：由于分布式锁都是以Key值作为唯一标识的，如在Redis中的Key值。所以对应的实体参数对象需要实现io.github.bloquesoft.decorator.action.InstanceKey接口，其getInstanceKey()方法返回的就是Key标识。
即LockEntity实现了InstanceKey接口。

## 自定义扩展
+ 自定义Action
+ 自定义Resource资源
+ 自定义Executor执行器

