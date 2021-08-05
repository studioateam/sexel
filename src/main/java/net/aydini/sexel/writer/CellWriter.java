package net.aydini.sexel.writer;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;

import net.aydini.mom.util.reflection.ReflectionUtil;
import net.aydini.sexel.annotation.SexelField;
import net.aydini.sexel.cache.CellCache;
import net.aydini.sexel.workbook.WorkBookHolder;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 */
public class CellWriter {


	private CellCache cache = CellCache.getInstance();
	
	private final Cell cell;
	
	private final WorkBookHolder workBookHolder;
	
	
	CellWriter(Cell cell, WorkBookHolder workBookHolder) {
		super();
		this.cell = cell;
		this.workBookHolder = workBookHolder;
	}



	
	public void write(Field field,Object cellValue,boolean isHeader)
	{
		SexelField sexelField = field.getAnnotation(SexelField.class);
		cache(field,sexelField);
		if(isHeader)
			cellValue =StringUtils.isNotEmpty(sexelField.headerTitle()) ? sexelField.headerTitle() : field.getName();
		cell.setCellStyle(
				cache.getStyle(field.getName()).getCellStyle(workBookHolder, cache.getFont(field.getName())));
		try {
			cell.setCellValue(ReflectionUtil.instantiate(sexelField.converter()).map(cellValue));
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	private void cache(Field field,SexelField sexelField)
	{
		cache.setStyle(field.getName(), ReflectionUtil.instantiate(sexelField.style()));
		cache.setFont(field.getName(), ReflectionUtil.instantiate(sexelField.font()));
	}
	
}
