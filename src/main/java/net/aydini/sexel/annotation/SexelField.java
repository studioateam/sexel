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
	
	int columnIndex();
	
	String headerTitle() default "";
	
	@SuppressWarnings("rawtypes")
	Class<? extends Maper> converter() default VoidMaper.class;
	
	Class<? extends CellStyler> style() default DefaultCellStyler.class;
	
	Class<? extends FontCreator> font() default DefaultFontCreator.class;
	
}
