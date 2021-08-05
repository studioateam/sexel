package net.aydini.sexel.workbook.style.font;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;

import net.aydini.sexel.constant.Constants;
import net.aydini.sexel.workbook.WorkBookHolder;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 */
public class DefaultFontCreator implements FontCreator {

	@Override
	public Font createCellFont(WorkBookHolder workHolder) {
		Font font = init(workHolder);
		font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
		return font;
	}

	@Override
	public Font createHeaderCellFont(WorkBookHolder workHolder) {
		Font font = init(workHolder);
		font.setBold(true);
		font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
		return font;
	}

	private Font init(WorkBookHolder workHolder) {
		Font font = workHolder.createFont();
		font.setFontName(Constants.DEFAULT_FONT_NAME);
		return font;
	}

}
