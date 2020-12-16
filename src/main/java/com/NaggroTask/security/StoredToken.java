package com.NaggroTask.security;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class StoredToken {
	
	private static ConcurrentHashMap<String, String> tokenValut;

	public StoredToken() {
		tokenValut = new ConcurrentHashMap<String, String>();
	}

	public void putToken(String key, String value) {
		tokenValut.put(key, value);
	}

	public void removeTokenByKey(String key) {
		tokenValut.remove(key);
	}

	public String getTokenByUserName(String key) {
		return tokenValut.get(key);
	}

}