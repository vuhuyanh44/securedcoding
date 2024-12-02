package com.lgcns.hrm.cv.common.definition;

import com.lgcns.hrm.cv.common.domain.Pool;
import com.lgcns.hrm.cv.common.exception.pool.BorrowObjectFromPoolErrorException;
import jakarta.annotation.Nonnull;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractObjectPool<T> {

    private static final Logger log = LoggerFactory.getLogger(AbstractObjectPool.class);

    private final GenericObjectPool<T> genericObjectPool;

    protected AbstractObjectPool(@Nonnull PooledObjectFactory<T> pooledObjectFactory, @Nonnull Pool pool) {
        GenericObjectPoolConfig<T> config = new GenericObjectPoolConfig<>();
        config.setMaxTotal(pool.getMaxTotal());
        config.setMaxIdle(pool.getMaxIdle());
        config.setMinIdle(pool.getMinIdle());
        config.setMaxWait(pool.getMaxWait());
        config.setMinEvictableIdleTime(pool.getMinEvictableIdleTime());
        config.setSoftMinEvictableIdleTime(pool.getSoftMinEvictableIdleTime());
        config.setLifo(pool.getLifo());
        config.setBlockWhenExhausted(pool.getBlockWhenExhausted());
        genericObjectPool = new GenericObjectPool<>(pooledObjectFactory, config);
    }

    public T get() {
        try {
            T object = genericObjectPool.borrowObject();
            log.debug("Fetch object from object pool.");
            return object;
        } catch (Exception e) {
            log.error("Can not fetch object from pool.", e);
            throw new BorrowObjectFromPoolErrorException("Can not fetch object from pool.");
        }
    }

    public void close(T client) {
        if (ObjectUtils.isNotEmpty(client)) {
            log.debug("Close object in pool.");
            genericObjectPool.returnObject(client);
        }
    }
}
