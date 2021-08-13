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
public class CellStyleCache {

	 private Map<String, CellStyler> stylerCache = new WeakHashMap<String, CellStyler>();

	    private Map<String, FontCreator> fontCache = new WeakHashMap<String, FontCreator>();

	    private CellStyleCache() {
	    }

	    private static CellStyleCache instance;

	    public static CellStyleCache getInstance() {
	        if (instance == null) {
	            synchronized (CellStyleCache.class) {
	                if (instance == null)
	                    instance = new CellStyleCache();
	            }
	        }
	        return instance;
	    }

	    public CellStyler getStyle(String key) {
	        return stylerCache.get(key);
	    }

	    public void setStyle(String key, CellStyler style) {
	        if(stylerCache.get(key) ==null)
	            stylerCache.put(key, style);
	    }


	    public FontCreator getFont(String key) {
	        return fontCache.get(key);
	    }

	    public void setFont(String key, FontCreator font) {
	        if(fontCache.get(key)==null)
	            fontCache.put(key, font);
	    }
}
