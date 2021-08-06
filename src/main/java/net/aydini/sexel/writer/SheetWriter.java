package net.aydini.sexel.writer;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import net.aydini.mom.util.reflection.ReflectionUtil;
import net.aydini.sexel.configuration.ConfigurationProperty;
import net.aydini.sexel.configuration.ConfigurationProperty.Direction;
import net.aydini.sexel.exception.SexelException;
import net.aydini.sexel.workbook.WorkBookHolder;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 */
public class SheetWriter extends AbstractWriter<List<Object>> {

	private final Sheet sheet;

	private WorkBookHolder workBookHolder;

	private List<Object> sheetData;

	AtomicInteger rowNnumber;

	SheetWriter(ConfigurationProperty configurationProperty, Sheet sheet) {
		super(configurationProperty);
		this.sheet = sheet;
	}

	public SheetWriter setSheetData(List<Object> sheetData) {
		this.sheetData = sheetData;
		return this;
	}

	public SheetWriter setWorkBookHolder(WorkBookHolder workBookHolder) {
		this.workBookHolder = workBookHolder;
		return this;
	}

	protected void doWrite() throws Exception {
		configSheet();
		final Set<Field> fields = ReflectionUtil.getClassFields(sheetData.get(0).getClass());
		if (!getConfigurationProperty().isSkipHeader())
			writeHeader(fields, rowNnumber.getAndIncrement());
		sheetData.stream().forEach(item -> writeData(fields, item, rowNnumber.getAndIncrement()));
	}

	private void writeHeader(Set<Field> fields, int rowIndex) {
		Row headerRow = sheet.createRow(rowIndex);
		new RowWriter(getConfigurationProperty(), headerRow).setWorkBookHolder(workBookHolder).setFields(fields).setIsheader(true).write();
	}

	private void writeData(Set<Field> fields, Object data, int rowIndex) {
		try {
			Row dataRow = sheet.createRow(rowIndex);
			new RowWriter(getConfigurationProperty(), dataRow).setWorkBookHolder(workBookHolder).setFields(fields).setRowData(data).write();
		} catch (Exception e) {
			throw new SexelException(e.getMessage(), e);
		}
	}

	private void configSheet() {
		rowNnumber = new AtomicInteger(getConfigurationProperty().getStartRow());
		if (getConfigurationProperty().getDirection() == Direction.RTL)
			sheet.setRightToLeft(true);
		else
			sheet.setRightToLeft(false);
	}

}
