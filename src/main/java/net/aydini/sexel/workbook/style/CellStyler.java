package net.aydini.sexel.workbook.style;

import org.apache.poi.ss.usermodel.CellStyle;

import net.aydini.sexel.workbook.WorkbookHolder;
import net.aydini.sexel.workbook.style.font.FontCreator;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 */
public interface CellStyler {

	public CellStyle getCellStyle(WorkbookHolder workBookHolder, FontCreator fontCreator);
	
	public CellStyle getHeaderCellStyle(WorkbookHolder workBookHolder,FontCreator fontCreator);
}
