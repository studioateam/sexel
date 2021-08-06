package net.aydini.sexel.writer;

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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import net.aydini.sexel.configuration.ConfigurationProperty;
import net.aydini.sexel.constant.Constants;
import net.aydini.sexel.exception.SexelException;
import net.aydini.sexel.workbook.WorkBookHolder;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 */
public class SexelWriter {

	private Workbook workbook;

	private ConfigurationProperty ConfigurationProperty = new ConfigurationProperty();

	private Map<String, List<Object>> sheetData;

	private String filePath;

	public SexelWriter() {
		sheetData = new HashedMap<>();
	}

	public SexelWriter setFilePath(String filePath) {
		this.filePath = filePath;
		validateFilePath();
		return this;
	}

	public SexelWriter setConfigurationProperty(ConfigurationProperty configurationProperty) {
		if (configurationProperty != null)
			this.ConfigurationProperty = configurationProperty;
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> SexelWriter addSheetData(String sheetName, List<T> data) {
		validateData(data);
		if (StringUtils.isEmpty(sheetName))
			addSheetData(data);
		else
			sheetData.put(sheetName, (List<Object>)data);
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> SexelWriter addSheetData(List<T> data) {
		validateData(data);
		String sheetName = data.get(0).getClass().getSimpleName();
		if (sheetData.isEmpty())
			sheetData.put(sheetName, (List<Object>) data);
		else
			sheetData.put(sheetName + (sheetData.size() + 1), (List<Object>) data);
		return this;
	}

	private <T> void validateData(List<T> data) {
		if (CollectionUtils.isEmpty(data))
			throw new SexelException("empty Data");
	}

	private void validateFilePath() {
		if (StringUtils.isAllEmpty(filePath))
			throw new SexelException("invalid file path");
		String fileExtention = FilenameUtils.getExtension(filePath).toLowerCase();
		if (!(fileExtention.equals(Constants.XLSX_FORMAT) || fileExtention.equals(Constants.XLS_FORMAT)))
			throw new SexelException("only .xlsx and .xls is supported");
	}

	private void validate() {
		validateFilePath();
		if (sheetData.isEmpty())
			throw new SexelException("empty Data");
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
			if (FilenameUtils.getExtension(filePath).toLowerCase().equals(Constants.XLS_FORMAT))
				workbook = new HSSFWorkbook();
			else
				workbook = new XSSFWorkbook();
		} catch (Exception e) {
			throw new SexelException(e.getMessage(), e);
		}
	}

	private void setDataToSheet() {
		WorkBookHolder workBookHolder = new WorkBookHolder(workbook);
		sheetData.entrySet().stream()
				.map(item -> new SheetWriter(workBookHolder, workbook.createSheet(item.getKey()))
						.setConfigurationProperty(ConfigurationProperty).setSheetData(item.getValue()))
				.forEach(SheetWriter::write);
	}

}
