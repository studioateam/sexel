package net.aydini.sexel.reader;

import java.lang.reflect.Field;

import org.apache.poi.ss.usermodel.Cell;

import net.aydini.mom.common.service.maper.Maper;
import net.aydini.mom.util.reflection.ReflectionUtil;
import net.aydini.sexel.annotation.SexelField;
import net.aydini.sexel.configuration.ConfigurationProperty;
import net.aydini.sexel.exception.SexelException;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 */
public class CellReader extends AbstractReader<Object> {

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
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	protected Object doRead() {
	        try {
	            Class<? extends Maper> mapper = field.getAnnotation(SexelField.class).converter();
	            Object cellValue = CellValueExtractor.extractCellValue( field.getType(),cell);
	            if(!mapper.equals(Maper.class))
	                cellValue=ReflectionUtil.instantiate(mapper).map(cellValue);
	            ReflectionUtil.setFieldValueToObject(field, mapedObject, cellValue);
	            return cellValue;
	        } catch (Exception e) {
	            throw new SexelException(e.getMessage(),e);
	        }
	    }

}
