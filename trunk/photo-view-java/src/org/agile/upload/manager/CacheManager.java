package org.agile.upload.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;

public class CacheManager {
	private static final Logger logger = Logger.getLogger(CacheManager.class.getName());

	private static Cache cache;
	{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			CacheFactory factory = javax.cache.CacheManager.getInstance().getCacheFactory();
			cache = factory.createCache(map);
		} catch (CacheException e) {
			logger.warning("Fail to generate cache instance!");
		}
	}

	public Object get(String key) {
		return cache.get(key);
	}

	@SuppressWarnings("unchecked")
	public void put(String key, Object value) {
		cache.put(key, value);
	}
	
	public void clearAll(){
		cache.clear();
	}
}
