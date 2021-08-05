package net.aydini.sexel.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import net.aydini.sexel.configuration.ConfigurationProperty;
import net.aydini.sexel.constant.Constants;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 */
public class SexelReader {

	private ConfigurationProperty configurationProperty = new ConfigurationProperty().setStartRow(1);

	private String filePath;

	private Class<?> outputClass = Object.class;

	private final Set<String> sheetsNames;

	public SexelReader() {
		sheetsNames = new HashSet<>();
	}

	public SexelReader setFilePath(String filePath) {
		this.filePath = filePath;
		validateFilePath();
		return this;
	}

	public SexelReader setSheetName(String sheetName) {
		if (StringUtils.isAllEmpty(sheetName))
			throw new RuntimeException("invalid sheetName");
		sheetsNames.add(sheetName);
		return this;
	}

	public SexelReader setConfigurationProperty(ConfigurationProperty configurationProperty) {
		if (configurationProperty != null)
			this.configurationProperty = configurationProperty;
		return this;
	}

	public SexelReader setOutputClass(Class<?> outputClass) {
		if (outputClass != null)
			this.outputClass = outputClass;
		return this;
	}

	private void validateFilePath() {
		if (StringUtils.isAllEmpty(filePath))
			throw new RuntimeException("invalid file path");
		if (!FilenameUtils.getExtension(filePath).toLowerCase().equals(Constants.ACCEPTABLE_EXCEL_TYPE))
			throw new RuntimeException("only .xlsx is supported");
	}
	
	
	public List<Object> read()
	{
		validateFilePath();
		List<Object> list = new ArrayList<>();
		sheetsNames.forEach(item->list.addAll(read(item)));
		return list;
	}

	private List<Object> read(String sheetName) {
		Workbook workbook = initWorkBook();
		Sheet sheet = workbook.getSheet(sheetName);
		return new SheetReader(configurationProperty, sheet,outputClass).read();
	}

	private Workbook initWorkBook() {
		try {
			return new HSSFWorkbook(new FileInputStream(new File(filePath)));
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
