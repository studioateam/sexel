package net.aydini.sexel.writer;

import java.lang.reflect.Field;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import net.aydini.mom.util.reflection.ReflectionUtil;
import net.aydini.sexel.annotation.SexelField;
import net.aydini.sexel.workbook.WorkBookHolder;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 */
public class RowWriter {

	private final Row row;

	private final WorkBookHolder workBookHolder;

	RowWriter(Row row, WorkBookHolder workBookHolder) {
		super();
		this.row = row;
		this.workBookHolder = workBookHolder;
	}

	public void write(Set<Field> fields, Object rowData,boolean isHeader)  {
		for (Field field : fields) {
			Cell cell = row.createCell(field.getAnnotation(SexelField.class).columnIndex());
			new CellWriter(cell, workBookHolder).write(field, getCellValue(field, rowData, isHeader),isHeader);
		}
	}

	private Object getCellValue(Field field, Object rowData, boolean isHeader) {
		if (isHeader)
			return null;
		try {
			return ReflectionUtil.getFieldValueFromObject(field, rowData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
