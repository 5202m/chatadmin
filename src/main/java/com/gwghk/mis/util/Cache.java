package com.gwghk.mis.util;

/**
 * cache实体
 * @author Ben.wang
 * @time: 2014-3-6
 */
public class Cache {
	private String key;
    private Object value;
    private long timeOut;
    private boolean expired;
    public Cache() {
            super();
    }
            
    public Cache(String key, String value, long timeOut, boolean expired) {
            this.key = key;
            this.value = value;
            this.timeOut = timeOut;
            this.expired = expired;
    }

    public String getKey() {
            return key;
    }

    public long getTimeOut() {
            return timeOut;
    }

    public Object getValue() {
            return value;
    }

    public void setKey(String string) {
            key = string;
    }

    public void setTimeOut(long l) {
            timeOut = l;
    }

    public void setValue(Object object) {
            value = object;
    }

    public boolean isExpired() {
            return expired;
    }

    public void setExpired(boolean b) {
            expired = b;
    }
}
