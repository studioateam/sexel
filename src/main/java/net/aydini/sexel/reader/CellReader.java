package net.aydini.sexel.reader;

import java.lang.reflect.Field;

import org.apache.poi.ss.usermodel.Cell;

import net.aydini.mom.util.reflection.ReflectionUtil;
import net.aydini.sexel.configuration.ConfigurationProperty;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 */
public class CellReader extends AbstractReader {

	private final Cell cell;

	private Field field;

	private Object mapedObject;

	public CellReader(Cell cell,ConfigurationProperty configurationProperty) {
		super(configurationProperty);
		this.cell = cell;
	}
	
	public CellReader setField(Field field)
	{
		this.field=field;
		return this;
	}

	
	public CellReader setMapedObject(Object mapedObject)
	{
		this.mapedObject=mapedObject;
		return this;
	}
	public Object doRead() {
		Object cellValue = CellValueExtractor.extractCellValue(field.getType(), cell);
		ReflectionUtil.setFieldValueToObject(field, mapedObject, cellValue);
		return cellValue;
	}

}
