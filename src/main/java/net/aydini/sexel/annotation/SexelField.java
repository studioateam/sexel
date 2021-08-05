package net.aydini.sexel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.poi.ss.usermodel.CellType;

import net.aydini.sexel.workbook.style.CellStyler;
import net.aydini.sexel.workbook.style.DefaultCellStyler;
import net.aydini.sexel.workbook.style.font.DefaultFontCreator;
import net.aydini.sexel.workbook.style.font.FontCreator;
import net.aydini.mom.common.service.maper.Maper;
import net.aydini.mom.util.mapper.ToStringMapper;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SexelField {
	
	int columnIndex();
	
	String headerTitle();
	
	Class<? extends Maper<Object, String>> converter() default ToStringMapper.class;
	
	Class<? extends CellStyler> style() default DefaultCellStyler.class;
	
	Class<? extends FontCreator> font() default DefaultFontCreator.class;
	
	CellType  cellType() default CellType.STRING;
	
}
