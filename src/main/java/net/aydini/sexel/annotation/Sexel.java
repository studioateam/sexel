package net.aydini.sexel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Sexel {

	
	/**
	 * file name
	 */
	String fileName();
	
	/**
	 *  sheet name
	 */
	String sheetName();
	
	
	/**
	 * 
	 * sheet prefix
	 */
	String sheetPrefix() default "";
	
	/**
	 * 
	 * if true new sheet with sheetPrefix will be created when reaches to #maxRowLimit 
	 */
	boolean limitRowData() default false;
	
	
	/**
	 * 
	 * @return maximum rows per sheet
	 */
	long maxRowLimit() default Long.MAX_VALUE;
	
}
