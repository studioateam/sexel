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
import net.aydini.sexel.workbook.WorkbookHolder;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 */
public class SexelWriter extends AbstractWriter {

	private Workbook workbook;

	private Map<String, List<Object>> sheetData;

	private String filePath;

	public SexelWriter() {
		this(new ConfigurationProperty());
	}

	public SexelWriter(ConfigurationProperty ConfigurationProperty) {
		super(ConfigurationProperty);
		sheetData = new HashedMap<>();
	}

	public SexelWriter setFilePath(String filePath) {
		this.filePath = filePath;
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> SexelWriter addSheetData(String sheetName, List<T> data) {
		if (CollectionUtils.isEmpty(data))
			throw new SexelException("empty Data");
		sheetName = StringUtils.isNotEmpty(sheetName) ? sheetName : data.get(0).getClass().getSimpleName();
		if (sheetData.get(sheetName) != null)
			sheetName = sheetName + (sheetData.size() + 1);
		sheetData.put(sheetName, (List<Object>) data);
		return this;
	}

	public <T> SexelWriter addSheetData(List<T> data) {
		return addSheetData(null, data);
	}

	private void validate() {
		if (StringUtils.isAllEmpty(filePath))
			throw new SexelException("invalid file path");
		String fileExtention = FilenameUtils.getExtension(filePath).toLowerCase();
		if (!(fileExtention.equals(Constants.XLSX_FORMAT) || fileExtention.equals(Constants.XLS_FORMAT)))
			throw new SexelException("only .xlsx and .xls is supported");
		if (sheetData.isEmpty())
			throw new SexelException("empty Data");
	}

	protected void doWrite() throws Exception {
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

		sheetData.entrySet().stream()
				.map(item -> new SheetWriter(getConfigurationProperty(), workbook.createSheet(item.getKey()))
						.setWorkBookHolder(new WorkbookHolder(workbook)).setSheetData(item.getValue()))
				.forEach(SheetWriter::write);
	}

}
