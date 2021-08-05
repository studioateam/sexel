package net.aydini.sexel.workbook.writer;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import net.aydini.mom.util.reflection.ReflectionUtil;
import net.aydini.sexel.annotation.SexelIgnore;
import net.aydini.sexel.configuration.ConfigurationProperty;
import net.aydini.sexel.configuration.ConfigurationProperty.Direction;
import net.aydini.sexel.workbook.WorkBookHolder;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 */
public class SheetWriter {
	
	

	private final Sheet sheet;

	private final WorkBookHolder workBookHolder;

	private List<Object> sheetData;
	
	private ConfigurationProperty configurationProperty;
	
	AtomicInteger rowNnumber = new AtomicInteger(0);

	public SheetWriter(WorkBookHolder workBookHolder, Sheet sheet) {
		this.workBookHolder = workBookHolder;
		this.sheet = sheet;
	}
	
	public SheetWriter setSheetData(List<Object> sheetData)
	{
		this.sheetData = sheetData;
		return this;
	}
	
	
	public SheetWriter setConfigurationProperty(ConfigurationProperty configurationProperty)
	{
		this.configurationProperty = configurationProperty;
		return this;
	}
	

	public void write() {
		configSheet();
		final Set<Field> fields = ReflectionUtil.getClassFields(sheetData.get(0).getClass(),field-> !field.isAnnotationPresent(SexelIgnore.class));
		if(!configurationProperty.isSkipHeader())
			writeHeader(fields, rowNnumber.getAndIncrement());
		sheetData.stream().forEach(item->writeData(fields,item,rowNnumber.getAndIncrement()));
	}

	private void writeHeader(Set<Field> fields, int rowIndex) {
		Row headerRow = sheet.createRow(rowIndex);
		new RowWriter(headerRow, workBookHolder).write(fields,null,true);
	}

	private  void writeData(Set<Field> fields, Object data, int rowIndex) {
		try {
			Row dataRow = sheet.createRow(rowIndex);
			new RowWriter(dataRow, workBookHolder).write(fields, data,false);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void configSheet()
	{
		if(configurationProperty.getDirection()==Direction.RTL)
			sheet.setRightToLeft(true);
	}

}
