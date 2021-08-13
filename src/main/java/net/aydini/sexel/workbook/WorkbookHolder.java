package net.aydini.sexel.workbook;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 */
public class WorkbookHolder {

	private final Workbook workBook;
	
	
	public WorkbookHolder(Workbook workBook)
	{
		this.workBook = workBook;
	}
	
	public CellStyle createCellStyle()
	{
		return workBook.createCellStyle();
	}
	
	
	public Font createFont()
	{
		return workBook.createFont();
	}
	
}
