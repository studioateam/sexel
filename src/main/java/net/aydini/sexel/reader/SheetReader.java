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
public class SheetReader<T> extends AbstractReader<List<T>>{

	
	private final Sheet sheet;
	
	private  Class<T> outputClass;

	public SheetReader(Sheet sheet,ConfigurationProperty configurationProperty) {
		super(configurationProperty);
		this.sheet = sheet;
	}
	
	public SheetReader<T> setOutputClass(Class<T> outputClass)
	{
		this.outputClass = outputClass;
		return this;
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> doRead()
	{
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		if(getConfigurationProperty().getStartRow() >rowCount)
			throw new RuntimeException("wrong configuration with start row");
		List<T> list = new ArrayList<>();
		for (int i = getConfigurationProperty().getStartRow(); i < rowCount + 1; i++) {
			Row row = sheet.getRow(i);
			list.add((T) new RowReader(row, getConfigurationProperty()).setOutputClass(outputClass).read());
		}
		return list;
	}
	
	
	
}
