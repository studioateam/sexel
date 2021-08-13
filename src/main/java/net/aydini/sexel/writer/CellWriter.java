package net.aydini.sexel.writer;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

import net.aydini.mom.util.reflection.ReflectionUtil;
import net.aydini.sexel.annotation.SexelField;
import net.aydini.sexel.cache.CellStyleCache;
import net.aydini.sexel.cache.MapperCache;
import net.aydini.sexel.configuration.ConfigurationProperty;
import net.aydini.sexel.workbook.WorkbookHolder;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 */
public class CellWriter extends AbstractWriter{


	private CellStyleCache cache = CellStyleCache.getInstance();
	
	private final Cell cell;
	
	private WorkbookHolder workBookHolder;
	
	private Field field;
	
	private Object cellValue;
	
	private boolean isHeader =false;
	
	
	CellWriter( ConfigurationProperty configurationProperty,Cell cell) {
		super(configurationProperty);
		this.cell = cell;
	}


	public CellWriter setWorkBookHolder(WorkbookHolder workBookHolder) {
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
	public CellWriter setIsHeader(boolean isHeader) {
		this.isHeader = isHeader;
		return this;
	}
	
	@SuppressWarnings("unchecked")
	protected void doWrite()
	{
		SexelField sexelField = field.getAnnotation(SexelField.class);
		cache(field,sexelField);
		cell.setCellStyle(getStyle());
		try {
			if(!isHeader)
				cellValue = MapperCache.getInstance().getMaper(sexelField.writeConverter()).map(cellValue);
			setValue(cellValue);
		} catch (Exception e) {
			throw new SecurityException(e.getMessage(),e);
		} 
	}
	
	
	private void setValue(Object value)
	{
		if(value == null)
			return;
		if(value instanceof String)
			cell.setCellValue(value.toString());
		if(value instanceof Date)
			cell.setCellValue((Date)value);
		if(value instanceof Calendar)
			cell.setCellValue((Calendar)value);
		if(value instanceof LocalDate)
			cell.setCellValue((LocalDate)value);
		if(value instanceof LocalDate)
			cell.setCellValue((LocalDate)value);
		if(value instanceof LocalDateTime)
			cell.setCellValue((LocalDateTime)value);
		if(value instanceof Boolean)
			cell.setCellValue((Boolean)value);
		if(StringUtils.isNumeric(value.toString()))
			cell.setCellValue(new BigDecimal(value.toString()).doubleValue());
		cell.setCellValue(value.toString());
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
