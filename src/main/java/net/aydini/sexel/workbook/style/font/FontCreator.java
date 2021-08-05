package net.aydini.sexel.workbook.style.font;

import org.apache.poi.ss.usermodel.Font;

import net.aydini.sexel.workbook.WorkBookHolder;

public interface FontCreator {

	public Font createCellFont(WorkBookHolder workHolder);
	
	public Font createHeaderCellFont(WorkBookHolder workHolder);
	
	
}
