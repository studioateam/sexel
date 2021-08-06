package net.aydini.sexel.writer;

import java.lang.reflect.Field;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

import net.aydini.mom.util.reflection.ReflectionUtil;
import net.aydini.sexel.annotation.SexelField;
import net.aydini.sexel.cache.CellCache;
import net.aydini.sexel.configuration.ConfigurationProperty;
import net.aydini.sexel.workbook.WorkBookHolder;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 */
public class CellWriter extends AbstractWriter<Object>{


	private CellCache cache = CellCache.getInstance();
	
	private final Cell cell;
	
	private WorkBookHolder workBookHolder;
	
	private Field field;
	
	private Object cellValue;
	
	private boolean isHeader =false;
	
	
	CellWriter( ConfigurationProperty configurationProperty,Cell cell) {
		super(configurationProperty);
		this.cell = cell;
	}


	public CellWriter setWorkBookHolder(WorkBookHolder workBookHolder) {
		this.workBookHolder = workBookHolder;
		return this;
	}
	
	public CellWriter setCellValue(Object cellValue) {
		this.cellValue = cellValue;
		return this;
	}
	
	public CellWriter setField(Field field) {
		this.field = field;
		return this;
	}
	public CellWriter setIsheader(boolean isHeader) {
		this.isHeader = isHeader;
		return this;
	}
	
	protected void doWrite()
	{
		SexelField sexelField = field.getAnnotation(SexelField.class);
		cache(field,sexelField);
		cell.setCellStyle(getStyle());
		try {
			cell.setCellValue(ReflectionUtil.instantiate(sexelField.converter()).map(cellValue));
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	private CellStyle getStyle()
	{
		if(isHeader)
			return cache.getStyle(field.getName()).getHeaderCellStyle(workBookHolder, cache.getFont(field.getName()));
		else
			return cache.getStyle(field.getName()).getCellStyle(workBookHolder, cache.getFont(field.getName()));
			
	}
	
	private void cache(Field field,SexelField sexelField)
	{
		cache.setStyle(field.getName(), ReflectionUtil.instantiate(sexelField.style()));
		cache.setFont(field.getName(), ReflectionUtil.instantiate(sexelField.font()));
	}
	
}
