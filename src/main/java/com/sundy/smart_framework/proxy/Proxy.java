package com.sundy.smart_framework.proxy;

public interface Proxy {

	/**
	 * 执行链式代理
	 * @param proxyChain
	 * @return
	 * @throws Throwable
	 */
	Object doProxy(ProxyChain proxyChain)throws Throwable;
	
}
