package net.aydini.sexel.reader;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import net.aydini.sexel.configuration.ConfigurationProperty;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 */
public class SheetReader {

	
	private final ConfigurationProperty configurationProperty;
	
	private final Sheet sheet;
	
	private final  Class<?> outputClass;

	public SheetReader(ConfigurationProperty configurationProperty, Sheet sheet,Class<?> outputClass) {
		super();
		this.configurationProperty = configurationProperty;
		this.sheet = sheet;
		this.outputClass=outputClass;
	}
	
	public List<Object> read()
	{
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		if(configurationProperty.getStartRow() >rowCount)
			throw new RuntimeException("wrong configuration with start row");
		List<Object> list = new ArrayList<>();
		for (int i = configurationProperty.getStartRow(); i < rowCount + 1; i++) {
			Row row = sheet.getRow(i);
			list.add(new RowReader(row, outputClass).read());
		}
		return list;
	}
	
	
}
