package net.aydini.sexel.writer;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import net.aydini.sexel.workbook.WorkBookHolder;
import net.aydini.mom.util.reflection.ReflectionUtil;

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
		AtomicInteger cellNumber = new AtomicInteger(0);
		for (Field field : fields) {
			Cell cell = row.createCell(cellNumber.getAndIncrement());
			new CellWriter(cell, workBookHolder).write(field, getCellValue(field, rowData, isHeader),isHeader);
		}
	}

	private Optional<Object> getCellValue(Field field, Object rowData, boolean isHeader) {
		if (isHeader)
			return Optional.empty();

		try {
			return Optional.of(ReflectionUtil.getFieldValueFromObject(field, rowData));
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		return Optional.empty();
	}

}
