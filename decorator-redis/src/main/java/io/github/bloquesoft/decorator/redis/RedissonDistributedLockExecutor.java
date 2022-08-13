package io.github.bloquesoft.decorator.redis;

import io.github.bloquesoft.decorator.distributedLock.executor.AbstractDistributedLockExecutor;
import io.github.bloquesoft.decorator.distributedLock.action.DecoratedDistributedLockAction;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

@Slf4j
public class RedissonDistributedLockExecutor extends AbstractDistributedLockExecutor {

    private final ThreadLocal<RLock> threadLocalLock = new InheritableThreadLocal<>();

    @Override
    public void onReady(DecoratedDistributedLockAction action, Object[] args) {

        AbstractRedisResource redisResource = (AbstractRedisResource) action.getResource();
        RedissonClient redissonClient = RedissonFactory.singleton().getRedissonClient(redisResource);
        String lockKey = action.getLockKey(args);
        RLock lock = redissonClient.getLock(lockKey);
        log.debug("Create Lock Object Success. LockKey:" + lockKey);
        threadLocalLock.set(lock);
    }

    @Override
    public void onBefore(DecoratedDistributedLockAction action, Object[] args) {
        RLock lock = threadLocalLock.get();
        if (lock != null) {
            lock.lock();
            log.debug("Lock Success On doBefore, lockObject:" + lock);
        }
    }

    @Override
    public void doAfter(DecoratedDistributedLockAction action, Object[] args) {
        RLock lock = threadLocalLock.get();
        if (lock != null) {
            lock.unlock();
            log.debug("UnLock Success On doAfter, lockObject:" + lock);
        }
    }

    @Override
    public void onError(DecoratedDistributedLockAction action, Object[] args) {
        RLock lock = threadLocalLock.get();
        if (lock != null) {
            lock.unlock();
            log.debug("UnLock Success On Error, lockObject:" + lock);
        }
    }

    @Override
    public void onFinally(DecoratedDistributedLockAction action, Object[] args) {
        threadLocalLock.remove();
        log.debug("Remove Lock Object Success On Finally");
    }
}