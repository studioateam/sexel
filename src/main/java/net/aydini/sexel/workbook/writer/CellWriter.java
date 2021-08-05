package net.aydini.sexel.workbook.writer;

import java.lang.reflect.Field;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

import net.aydini.mom.util.reflection.ReflectionUtil;
import net.aydini.sexel.annotation.SexelField;
import net.aydini.sexel.cache.CellCache;
import net.aydini.sexel.workbook.WorkBookHolder;
import net.aydini.sexel.workbook.style.DefaultCellStyler;
import net.aydini.sexel.workbook.style.font.DefaultFontCreator;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 */
public class CellWriter {


	private CellCache cache = CellCache.getInstance();
	
	private final Cell cell;
	
	private final WorkBookHolder workBookHolder;
	
	
	public CellWriter(Cell cell, WorkBookHolder workBookHolder) {
		super();
		this.cell = cell;
		this.workBookHolder = workBookHolder;
	}



	
	public void write(Field field,Optional<Object> optionalCellValue,boolean isHeader)
	{
		try
		{
			if (field.isAnnotationPresent(SexelField.class))
				writesexelField(field,optionalCellValue,isHeader);
			else
				writeNonesexelField(field,optionalCellValue,isHeader);
		}catch(Exception e)
		{
			//todo
		}
	}
	
	private void writesexelField(Field field,Optional<Object> optionalCellValue,boolean isHeader) throws Exception
	{
		
		SexelField sexelField = field.getAnnotation(SexelField.class);
		cache(field,sexelField);
		Object cellValue = optionalCellValue.orElse(null);
		if(isHeader)
			cellValue =sexelField.headerTitle();
		cell.setCellStyle(
				cache.getStyle(field.getName()).getCellStyle(workBookHolder, cache.getFont(field.getName())));
		cell.setCellValue(ReflectionUtil.instantiate(sexelField.converter())
				.map(ReflectionUtil.getFieldValueFromObject(field, cellValue)));
	}
	
	private void writeNonesexelField(Field field,Optional<Object> optionalCellValue,boolean isHeader)
	{
		Object cellValue = optionalCellValue.orElse(null);
		if(isHeader)
			cellValue =field.getName();
		cell.setCellStyle(getDefaultStyle(isHeader));
		if (cellValue == null)
			cell.setBlank();
		else
			cell.setCellValue(cellValue.toString());
		
	}
	
	
	private void cache(Field field,SexelField sexelField)
	{
		cache.setStyle(field.getName(), ReflectionUtil.instantiate(sexelField.style()));
		cache.setFont(field.getName(), ReflectionUtil.instantiate(sexelField.font()));
	}
	
	
	private CellStyle getDefaultStyle(boolean isHeader) {
		if (isHeader)
			return new DefaultCellStyler().getHeaderCellStyle(workBookHolder, new DefaultFontCreator());
		return new DefaultCellStyler().getCellStyle(workBookHolder, new DefaultFontCreator());
	}
}
