package net.aydini.sexel.reader;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 */
public class CellValueExtractor {

	public static Object extract(Class<?> clazz, Cell cell) {

		if (clazz.equals(Boolean.class))
			return cell.getBooleanCellValue();
		if (clazz.equals(LocalDate.class))
			return cell.getLocalDateTimeCellValue();
		if (clazz.equals(Date.class))
			return cell.getDateCellValue();
		if (clazz.equals(Double.class))
			return cell.getNumericCellValue();
		if (clazz.equals(Integer.class))
			return getInteger(cell);
		
		
		if (clazz.equals(BigDecimal.class))
			return new BigDecimal(cell.getNumericCellValue());
		if (clazz.equals(Long.class))
			return new Double(cell.getNumericCellValue()).longValue();
		if (clazz.equals(Float.class))
			return new Double(cell.getNumericCellValue()).floatValue();
		return cell.getStringCellValue();
	}

	private static Integer getInteger(Cell cell) {
		if (cell.getCellType() == CellType.NUMERIC)
			return new Double(cell.getNumericCellValue()).intValue();
		String value = cell.getStringCellValue();
		return StringUtils.isNotEmpty(value) ? Integer.parseInt(value) : null;
	}
}
