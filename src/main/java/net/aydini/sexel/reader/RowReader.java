package net.aydini.sexel.reader;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import net.aydini.mom.util.reflection.ReflectionUtil;
import net.aydini.sexel.annotation.RowNumber;
import net.aydini.sexel.annotation.SexelField;
import net.aydini.sexel.configuration.ConfigurationProperty;
import net.aydini.sexel.exception.SexelException;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 */
public class RowReader<T> extends AbstractReader<T> {

	private final Row row;

	private Class<T> outputClass;

	RowReader(Row row, ConfigurationProperty configurationProperty) {
		super(configurationProperty);
		this.row = row;
	}

	public RowReader<T> setOutputClass(Class<T> outputClass) {
		this.outputClass = outputClass;
		return this;
	}

	public T doRead() {
		Set<Field> outputClassFields = ReflectionUtil.getClassFields(outputClass);
		validate(outputClassFields);
		final T object = ReflectionUtil.instantiate(outputClass);
		outputClassFields.stream().filter(item -> item.isAnnotationPresent(SexelField.class)).parallel()
				.forEach(item -> doRead(item, object));
		outputClassFields.stream().filter(item -> item.isAnnotationPresent(RowNumber.class)).parallel()
				.forEach(item -> setRowNumber(item, object));
		return object;
	}

	private void doRead(Field field, Object object) {
		SexelField sexelField = field.getAnnotation(SexelField.class);
		Cell cell = row.getCell(sexelField.columnIndex());
		new CellReader(cell, getConfigurationProperty()).setField(field).setMapedObject(object).read();
	}

	private void setRowNumber(Field field, Object object) {
		try {
			ReflectionUtil.setFieldValueToObject(field, object, row.getRowNum());
		} catch (Exception e) {
			throw new SexelException("field " + field.getName() + " should be numeric type");
		}
	}

	private void validate(Set<Field> outputClassFields) {
		Optional<Field> primitiveField = outputClassFields.parallelStream().filter(item -> item.getType().isPrimitive())
				.findAny();
		if (primitiveField.isPresent())
			throw new SexelException(
					"premitive field is not allowed : " + outputClass.getName() + "." + primitiveField.get().getName());
	}

}
