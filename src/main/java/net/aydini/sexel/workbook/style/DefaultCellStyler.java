package net.aydini.sexel.workbook.style;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import net.aydini.sexel.workbook.WorkBookHolder;
import net.aydini.sexel.workbook.style.font.FontCreator;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 */
public class DefaultCellStyler implements CellStyler {

	@Override
	public CellStyle getCellStyle(WorkBookHolder workBookHolder, FontCreator fontCreator) {
		CellStyle cellStyle = init(workBookHolder);
		cellStyle.setFont(fontCreator.createCellFont(workBookHolder));
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		return cellStyle;

	}

	public CellStyle getHeaderCellStyle(WorkBookHolder workBookHolder, FontCreator fontCreator) {
		CellStyle cellStyle = init(workBookHolder);
		cellStyle.setFont(fontCreator.createHeaderCellFont(workBookHolder));
		cellStyle.setBorderBottom(BorderStyle.MEDIUM);
		cellStyle.setBorderLeft(BorderStyle.MEDIUM);
		cellStyle.setBorderRight(BorderStyle.MEDIUM);
		cellStyle.setBorderTop(BorderStyle.MEDIUM);
		return cellStyle;
	}

	private CellStyle init(WorkBookHolder workBookHolder) {
		if (workBookHolder == null)
			throw new RuntimeException("");
		CellStyle cellStyle = workBookHolder.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		return cellStyle;
	}

}
