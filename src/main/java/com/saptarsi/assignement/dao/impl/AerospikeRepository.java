/**
 * 
 */
package com.saptarsi.assignement.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.AerospikeException;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.WritePolicy;
import com.saptarsi.assignement.dao.CacheEntry;
import com.saptarsi.assignement.dao.KeyValueRepository;

/**
 * @author saptarsichaurashy
 *
 */
@Repository
public class AerospikeRepository implements KeyValueRepository {
	
	@Autowired
	private AerospikeClient client;

	@Autowired
	@Qualifier("writePolicy")
	private WritePolicy writePolicy;
	
	@Autowired
	@Qualifier("readPolicy")
	private Policy policy;
	
	@Value("${aerospike.namespace}")
	private String namespace;
	
	protected void finalize() throws Throwable {
		if (this.client != null){
			this.client.close();
		}
	};
	
	/* (non-Javadoc)
	 * @see com.saptarsi.assignement.dao.KeyValueRepository#put(com.saptarsi.assignement.dao.CacheEntry, java.lang.String, java.util.Map)
	 */
	@Override
	public void put(CacheEntry cacheEntry, String key, Map<String, Object> value) {
		
		Key keyBin = new Key(namespace, cacheEntry.name(), key);
		Bin bin[] = new Bin[value.size()];
		int i = 0;
		for(String k : value.keySet()){
			bin[i++] = new Bin(k,value.get(k));
		};
		try{
			client.put(writePolicy, keyBin, bin);
		}catch(AerospikeException ex){
			throw new AerospikeException(ex.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.saptarsi.assignement.dao.KeyValueRepository#put(com.saptarsi.assignement.dao.CacheEntry, java.lang.String, java.util.Map, int)
	 */
	@Override
	public void put(CacheEntry cacheEntry, String key, Map<String, Object> value, int ttlSec) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.saptarsi.assignement.dao.KeyValueRepository#get(com.saptarsi.assignement.dao.CacheEntry, java.lang.String, java.lang.String[])
	 */
	@Override
	public Object get(CacheEntry cacheEntry, String key, String... bins) {
		
		Key keyBin = new Key(namespace, cacheEntry.name(), key);
		try{
			Record record;
			if(bins.length == 0)
				record = client.get(policy, keyBin);
			else
				record = client.get(policy, keyBin, bins);
			return record;
		}catch(AerospikeException ex){
			throw new AerospikeException(ex.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.saptarsi.assignement.dao.KeyValueRepository#remove(com.saptarsi.assignement.dao.CacheEntry, java.lang.String)
	 */
	@Override
	public void remove(CacheEntry cacheEntry, String key) {
		
		Key keyBin = new Key(namespace, cacheEntry.name(), key);
		try{
			client.delete(writePolicy, keyBin);
		}catch(AerospikeException ex){
			throw new AerospikeException(ex.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.saptarsi.assignement.dao.KeyValueRepository#remove(com.saptarsi.assignement.dao.CacheEntry, java.util.List)
	 */
	@Override
	public void remove(CacheEntry cacheMapEntry, List<String> keys) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.saptarsi.assignement.dao.KeyValueRepository#clear(com.saptarsi.assignement.dao.CacheEntry)
	 */
	@Override
	public void clear(CacheEntry cacheEntry) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.saptarsi.assignement.dao.KeyValueRepository#clearAll(java.util.Set)
	 */
	@Override
	public void clearAll(Set<CacheEntry> cacheEntries) {
		// TODO Auto-generated method stub

	}

}
