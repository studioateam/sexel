package net.aydini.sexel.cache;

import java.util.Map;
import java.util.WeakHashMap;

import net.aydini.mom.common.service.maper.Maper;
import net.aydini.mom.util.reflection.ReflectionUtil;

public class MapperCache {

	private final Map<String, Maper> mapCache = new WeakHashMap<>();

	private static MapperCache instance;

	private MapperCache() {
	}

	public static MapperCache getInstance() {
		if (instance == null) {
			synchronized (MapperCache.class) {
				if (instance == null)
					instance = new MapperCache();
			}
		}
		return instance;
	}

	private synchronized void putMapper(Class<? extends Maper> maperClass) {
		if (mapCache.get(maperClass.getName()) == null)
			mapCache.put(maperClass.getName(), ReflectionUtil.instantiate(maperClass));
	}

	public Maper getMaper(Class<? extends Maper> maperClass) {
		Maper maper = mapCache.get(maperClass.getName());
		if (maper != null)
			return maper;
		putMapper(maperClass);
		return getMaper(maperClass);
	}

}
