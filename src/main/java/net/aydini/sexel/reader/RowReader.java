package net.aydini.sexel.reader;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import net.aydini.mom.util.reflection.ReflectionUtil;
import net.aydini.sexel.annotation.SexelField;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 */
public class RowReader {

	private final Row row;

	private final Class<?> outputClass;

	public RowReader(Row row, Class<?> outputClass) {
		super();
		this.row = row;
		this.outputClass = outputClass;
	}

	public Object read() {
		Set<Field> outputClassFields = ReflectionUtil.getClassFields(outputClass);
		validate(outputClassFields);
		final Object object = ReflectionUtil.instantiate(outputClass);
		outputClassFields.stream().filter(item->item.isAnnotationPresent(SexelField.class)).forEach(item->read(item,object));
		return object;
	}
	private void read(Field field,Object object)
	{
		SexelField sexelField = field.getAnnotation(SexelField.class);
		Cell cell = row.getCell(sexelField.columnIndex());
		new CellReader(cell, field, object).read();
	}

	private void validate(Set<Field> outputClassFields) {
		Optional<Field> primitiveField = outputClassFields.parallelStream().filter(item -> item.getType().isPrimitive())
				.findAny();
		if (primitiveField.isPresent())
			throw new RuntimeException(
					"premitive field is not allowed : " + outputClass.getName() + "." + primitiveField.get().getName());
	}

}
