package net.aydini.sexel.reader;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import net.aydini.sexel.exception.SexelException;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 */
public class CellValueExtractor {

	public static Object extractCellValue(Class<?> clazz, Cell cell) {
		try {
			return extract(clazz, cell);
		} catch (Exception e) {
			
			throw new SexelException(e.getMessage(),e);
		}
	}

	private static Object extract(Class<?> clazz, Cell cell) {

		if (clazz.equals(Boolean.class))
			return cell.getBooleanCellValue();
		if (clazz.equals(LocalDate.class))
			return cell.getLocalDateTimeCellValue();
		if (clazz.equals(Date.class))
			return cell.getDateCellValue();
		if (clazz.equals(Double.class))
			return getDouble(cell);
		if (clazz.equals(Integer.class))
			return getInteger(cell);
		if (clazz.equals(BigDecimal.class))
			return getBigDecimal(cell);
		if (clazz.equals(Long.class))
			getLong(cell);
		if (clazz.equals(Float.class))
			return getFloat(cell);
		return cell.getStringCellValue();
	}

	private static Double getDouble(Cell cell) {
		if (cell.getCellType() == CellType.NUMERIC)
			return new Double(cell.getNumericCellValue());
		String value = cell.getStringCellValue();
		return StringUtils.isNotEmpty(value) ? Double.parseDouble(value) : null;
	}

	private static Float getFloat(Cell cell) {
		if (cell.getCellType() == CellType.NUMERIC)
			return new Double(cell.getNumericCellValue()).floatValue();
		String value = cell.getStringCellValue();
		return StringUtils.isNotEmpty(value) ? Float.parseFloat(value) : null;
	}

	private static Long getLong(Cell cell) {
		if (cell.getCellType() == CellType.NUMERIC)
			return new Double(cell.getNumericCellValue()).longValue();
		String value = cell.getStringCellValue();
		return StringUtils.isNotEmpty(value) ? Long.parseLong(value) : null;
	}

	private static BigDecimal getBigDecimal(Cell cell) {
		if (cell.getCellType() == CellType.NUMERIC)
			return new BigDecimal(cell.getNumericCellValue());
		String value = cell.getStringCellValue();
		return StringUtils.isNotEmpty(value) ? new BigDecimal(value) : null;
	}

	private static Integer getInteger(Cell cell) {
		if (cell.getCellType() == CellType.NUMERIC)
			return new Double(cell.getNumericCellValue()).intValue();
		String value = cell.getStringCellValue();
		return StringUtils.isNotEmpty(value) ? Integer.parseInt(value) : null;
	}
}
