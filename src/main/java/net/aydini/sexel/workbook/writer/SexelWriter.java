package net.aydini.sexel.workbook.writer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import net.aydini.sexel.constant.Constants;
import net.aydini.sexel.workbook.WorkBookHolder;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 */
public class SexelWriter {

	private Workbook workbook;

	@SuppressWarnings("rawtypes")
	private Map<String, List> sheetData;

	private String filePath;

	public SexelWriter() {
		sheetData = new HashedMap<>();
	}

	public SexelWriter setFilePath(String filePath) {
		this.filePath = filePath;
		validateFilePath();
		return this;
	}

	@SuppressWarnings("rawtypes")
	public SexelWriter addSheetData(String sheetName, List data) {
		validateData(data);
		if (StringUtils.isEmpty(sheetName))
			addSheetData(data);
		else
			sheetData.put(sheetName, data);
		return this;
	}

	@SuppressWarnings("rawtypes")
	public SexelWriter addSheetData(List data) {
		validateData(data);
		String sheetName = data.get(0).getClass().getSimpleName();
		if (sheetData.isEmpty())
			sheetData.put(sheetName, data);
		else
			sheetData.put(sheetName + (sheetData.size() + 1), data);
		return this;
	}

	@SuppressWarnings("rawtypes")
	private void validateData(List data) {
		if (CollectionUtils.isEmpty(data))
			throw new RuntimeException("empty Data");
	}

	private void validateFilePath() {
		if (StringUtils.isAllEmpty(filePath))
			throw new RuntimeException("invalid file path");
		if (!FilenameUtils.getExtension(filePath).toLowerCase().equals(Constants.ACCEPTABLE_EXCEL_TYPE))
			throw new RuntimeException("only .xlsx is supported");
	}

	private void validate() {
		validateFilePath();
		if (sheetData.isEmpty())
			throw new RuntimeException("empty Data");
	}

	public void write() throws Exception {
		validate();
		initWorkbook();
		setDataToSheet();
		workbook.write(new FileOutputStream(new File(filePath)));
		workbook.close();

	}

	private void initWorkbook() {
		try {
			this.workbook = new HSSFWorkbook();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private void setDataToSheet() {
		WorkBookHolder workBookHolder = new WorkBookHolder(workbook);
		sheetData.entrySet().stream()
				.map(item -> new SheetWriter(item.getValue(), workBookHolder, workbook.createSheet(item.getKey())))
				.forEach(SheetWriter::write);
	}

}
