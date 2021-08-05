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
public class SheetReader extends AbstractReader<List<Object>>{

	
	private final Sheet sheet;
	
	private  Class<?> outputClass;

	public SheetReader(Sheet sheet,ConfigurationProperty configurationProperty) {
		super(configurationProperty);
		this.sheet = sheet;
	}
	
	public SheetReader setOutputClass(Class<?> outputClass)
	{
		this.outputClass = outputClass;
		return this;
	}

	
	public List<Object> doRead()
	{
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		if(getConfigurationProperty().getStartRow() >rowCount)
			throw new RuntimeException("wrong configuration with start row");
		List<Object> list = new ArrayList<>();
		for (int i = getConfigurationProperty().getStartRow(); i < rowCount + 1; i++) {
			Row row = sheet.getRow(i);
			list.add(new RowReader(row, getConfigurationProperty()).setOutputClass(outputClass).read());
		}
		return list;
	}
	
	
}
