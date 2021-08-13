package net.aydini.sexel.workbook.style.font;

import org.apache.poi.ss.usermodel.Font;

import net.aydini.sexel.workbook.WorkbookHolder;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 */
public interface FontCreator {

	public Font createCellFont(WorkbookHolder workHolder);
	
	public Font createHeaderCellFont(WorkbookHolder workHolder);
	
	
}
