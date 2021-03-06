package me.j360.lts.failstore;


import me.j360.lts.common.support.KVPair;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Robert HG (254963746@qq.com) on 5/21/15.
 */
public interface FailStore {

    public String getPath();

    public void open() throws FailStoreException;

    public void put(String key, Object value) throws FailStoreException;

    public void delete(String key) throws FailStoreException;

    public void delete(List<String> keys) throws FailStoreException;

    public <T> List<KVPair<String, T>> fetchTop(int size, Type type) throws FailStoreException;

    public void close() throws FailStoreException;

    public void destroy() throws FailStoreException;
}
