package com.shishuo.cms.service;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Service;

@Service
public class KeyGeneratorService implements KeyGenerator {

	@Override
	public Object generate(Object target, Method method, Object... params) {
		String key = method.getName() + "_" + StringUtils.join(params, "_");
		return key;
	}

	public static void main(String[] args) {

	}
}
