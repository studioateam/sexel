package net.aydini.sexel.reader;

import java.lang.reflect.Field;

import org.apache.poi.ss.usermodel.Cell;

import net.aydini.mom.util.reflection.ReflectionUtil;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 */
public class CellReader {

	private final Cell cell;

	private final Field field;

	private final Object mapedObject;

	public CellReader(Cell cell, Field field, Object mapedObject) {
		super();
		this.cell = cell;
		this.field = field;
		this.mapedObject = mapedObject;
	}

	public void read() {
		ReflectionUtil.setFieldValueToObject(field, mapedObject, CellValueExtractor.extract(field.getType(), cell));
	}

}
