/**
 * 
 */
package com.saptarsi.assignement.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author saptarsichaurashy
 *
 */
public interface KeyValueRepository {

	
    /**
     * Puts Element into the Cache Identified by Cache with the given key
     *
     * @param cacheMapEntry in which value has to be added
     * @param key      key to be used
     * @param value    value to be associated
     */
    public void put(CacheEntry cacheEntry, String key, Map<String,Object> value);
    
    /**
     * Puts Element into the Cache Identified by Cache with the given key
     *
     * @param cacheMapEntry in which value has to be added
     * @param key      key to be used
     * @param value    value to be associated
     * @param ttlSec	value in second - record expire time
     */
    public void put(CacheEntry cacheEntry, String key, Map<String,Object> value, int ttlSec);


    /**
     * Reads from they cache entry specified by key
     *
     * @param cacheMapEntry
     * @param key
     * @return Value stored in cache for key, null if not found
     */
    public Object get(CacheEntry cacheEntry, String key, String ...bins);

    /**
     * Removes the Value from Cache , entry specified by key
     *
     * @param cacheMapEntry
     * @param key
     */
    public void remove(CacheEntry cacheEntry, String key);

    public void remove(CacheEntry cacheMapEntry, List<String> keys);

    /**
     * Remove all the Values stored in Cache
     *
     * @param cacheMapEntry
     */
    public void clear(CacheEntry cacheEntry);

    /**
     * Remove all the caches
     */
    public void clearAll(Set<CacheEntry> cacheEntries);
    
    default boolean isKeyPresent(CacheEntry cacheEntry, String key){
    	return false;
    }
}
