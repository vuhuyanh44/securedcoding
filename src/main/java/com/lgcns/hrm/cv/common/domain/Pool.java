package com.lgcns.hrm.cv.common.domain;

import com.google.common.base.MoreObjects;
import org.apache.commons.pool2.impl.BaseObjectPoolConfig;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.time.Duration;

public class Pool {

    /**
     * The maximum number of objects in the pool
     */
    private Integer maxTotal = GenericObjectPoolConfig.DEFAULT_MAX_TOTAL;

    /**
     * Maximum number of free objects
     */
    private Integer maxIdle = GenericObjectPoolConfig.DEFAULT_MAX_IDLE;

    /**
     * Maximum number of free objects
     */
    private Integer minIdle = GenericObjectPoolConfig.DEFAULT_MIN_IDLE;

    /**
     * The way the object pool stores pooled objects, true is placed at the front of the idle queue, false is placed at the end of the idle queue
     */
    private Boolean lifo = true;

    /**
     * When the connection pool resources are exhausted, the maximum blocking time of the caller, and an exception is thrown when it times out.
     */
    private Duration maxWait = BaseObjectPoolConfig.DEFAULT_MAX_WAIT;

    /**
     * When the object pool is full, whether to block acquisition (false means an exception will be thrown directly if the object cannot be borrowed), the default is true
     */
    private Boolean blockWhenExhausted = BaseObjectPoolConfig.DEFAULT_BLOCK_WHEN_EXHAUSTED;

    /**
     * Minimum idle time. After reaching this value, idle connections may be removed. The default is 30 minutes.
     */
    private Duration minEvictableIdleTime = BaseObjectPoolConfig.DEFAULT_MIN_EVICTABLE_IDLE_DURATION;

    private Duration softMinEvictableIdleTime = BaseObjectPoolConfig.DEFAULT_SOFT_MIN_EVICTABLE_IDLE_DURATION;

    public Integer getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

    public Boolean getLifo() {
        return lifo;
    }

    public void setLifo(Boolean lifo) {
        this.lifo = lifo;
    }

    public Duration getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(Duration maxWait) {
        this.maxWait = maxWait;
    }

    public Boolean getBlockWhenExhausted() {
        return blockWhenExhausted;
    }

    public void setBlockWhenExhausted(Boolean blockWhenExhausted) {
        this.blockWhenExhausted = blockWhenExhausted;
    }

    public Duration getMinEvictableIdleTime() {
        return minEvictableIdleTime;
    }

    public void setMinEvictableIdleTime(Duration minEvictableIdleTime) {
        this.minEvictableIdleTime = minEvictableIdleTime;
    }

    public Duration getSoftMinEvictableIdleTime() {
        return softMinEvictableIdleTime;
    }

    public void setSoftMinEvictableIdleTime(Duration softMinEvictableIdleTime) {
        this.softMinEvictableIdleTime = softMinEvictableIdleTime;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("maxTotal", maxTotal)
                .add("maxIdle", maxIdle)
                .add("minIdle", minIdle)
                .add("lifo", lifo)
                .add("maxWait", maxWait)
                .add("blockWhenExhausted", blockWhenExhausted)
                .add("minEvictableIdleTime", minEvictableIdleTime)
                .add("softMinEvictableIdleTime", softMinEvictableIdleTime)
                .toString();
    }
}
