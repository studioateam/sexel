package net.aydini.sexel.cache;

import java.util.Map;
import java.util.WeakHashMap;

import net.aydini.sexel.workbook.style.CellStyler;
import net.aydini.sexel.workbook.style.font.FontCreator;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 */
public class CellCache {

	private Map<String, CellStyler> stylerCache = new WeakHashMap<String, CellStyler>();

	private Map<String, FontCreator> fontCache = new WeakHashMap<String, FontCreator>();

	private CellCache() {
	}

	private static CellCache instance;

	public static CellCache getInstance() {
		if (instance == null) {
			synchronized (CellCache.class) {
				if (instance == null)
					instance = new CellCache();
			}
		}
		return instance;
	}

	public CellStyler getStyle(String key) {
		return stylerCache.get(key);
	}

	public void setStyle(String key, CellStyler style) {
		stylerCache.put(key, style);
	}
	
	
	public FontCreator getFont(String key) {
		return fontCache.get(key);
	}

	public void setFont(String key, FontCreator font) {
		fontCache.put(key, font);
	}
}
