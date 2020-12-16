package com.pengjunlee.ratelimit;

/**
 * @author pengjunlee
 * @create 2020-12-15 16:30
 */
public enum Token {
    SUCCESS,
    FAILED;

    public boolean isSuccess() {
        return this.equals(SUCCESS);
    }

    public boolean isFailed() {
        return this.equals(FAILED);
    }
}
