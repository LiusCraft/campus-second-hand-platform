package com.liuscraft.tradingplatform.entity;

import java.util.Objects;

/**
 * @author LiusCraft
 * @DateTime 2023/4/30 21:50
 */
public class CacheObject <T>{
    private T obj;
    private long expireTime = 0L;

    public CacheObject(T obj, long expireTime) {
        this.obj = obj;
        this.expireTime = expireTime;
    }

    public CacheObject(T obj) {
        this.obj = obj;
    }

    public CacheObject() {
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public T getObj() {
        return obj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CacheObject<?> that = (CacheObject<?>) o;
        return getObj().equals(that.getObj());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getObj());
    }

    public boolean isExpire() {
        if (getExpireTime()<1) {
            return false;
        }
        return getExpireTime() <= System.currentTimeMillis();
    }
}
