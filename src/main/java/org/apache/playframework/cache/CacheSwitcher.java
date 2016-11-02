package org.apache.playframework.cache;

/**
 * 客户端调用服务时，服务端缓存否启用开关；
 * @author alexzhu
 *
 */
public class CacheSwitcher {

    private static final ThreadLocal<Boolean> CACHES = new ThreadLocal<Boolean>();

    public static void set(Boolean cached) {
    	CACHES.set(cached);
    }

    public static void unset() {
    	CACHES.remove();
    }

    public static Boolean get() {
    	Boolean cached = CACHES.get();
    	if(cached!=null){
    		return CACHES.get();
    	}
    	return true;
    }
}