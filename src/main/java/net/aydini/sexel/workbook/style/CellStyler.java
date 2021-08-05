package net.aydini.sexel.workbook.style;

import org.apache.poi.ss.usermodel.CellStyle;

import net.aydini.sexel.workbook.WorkBookHolder;
import net.aydini.sexel.workbook.style.font.FontCreator;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 */
public interface CellStyler {

	public CellStyle getCellStyle(WorkBookHolder workBookHolder, FontCreator fontCreator);
	
	public CellStyle getHeaderCellStyle(WorkBookHolder workBookHolder,FontCreator fontCreator);
}
