package net.aydini.sexel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.aydini.mom.common.service.maper.Maper;
import net.aydini.sexel.converter.VoidMaper;
import net.aydini.sexel.workbook.style.CellStyler;
import net.aydini.sexel.workbook.style.DefaultCellStyler;
import net.aydini.sexel.workbook.style.font.DefaultFontCreator;
import net.aydini.sexel.workbook.style.font.FontCreator;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SexelField {
	
	
	/**
	 *  index of column to be read/write. starting from 0.
	 */
	int columnIndex();
	
	
	/**
	 * title of header cell.
	 */
	String headerTitle() default "";
	
	
	/**
	 *  converts annotated field value using this maper class and then sets final value to field.
	 * @see ObjectToStringMaper, ObjectToLongMaper, ObjectToIntegerMaper
	 */
	@SuppressWarnings("rawtypes")
	Class<? extends Maper> readConverter() default VoidMaper.class;
	
	
	/**
	 * 
	 * converts annotated field value using this maper class and then sets final value to cell.
	 * @see ObjectToStringMaper, ObjectToLongMaper, ObjectToIntegerMaper
	 */
	@SuppressWarnings("rawtypes")
	Class<? extends Maper> writeConverter() default VoidMaper.class;
	
	/**
	 * 
	 * this will be used for setting style (borders,background color etc) to both header and data cells
	 */
	Class<? extends CellStyler> style() default DefaultCellStyler.class;
	
	
	/**
	 * 
	 * this will be used for setting font (font,size,color etc) to both header and data cells
	 */
	Class<? extends FontCreator> font() default DefaultFontCreator.class;
	
}
