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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import net.aydini.sexel.configuration.ConfigurationProperty;
import net.aydini.sexel.constant.Constants;
import net.aydini.sexel.exception.SexelException;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 */
public class SexelReader<T> extends AbstractReader<List<T>> {

	private String filePath;

	private Class<T> outputClass;

	private final Set<String> sheetsNames;

	private Workbook workbook;

	public SexelReader() {
		this(new ConfigurationProperty().setStartRow(1));
	}

	public SexelReader(ConfigurationProperty configurationProperty) {
		super(configurationProperty);
		sheetsNames = new HashSet<>();
	}

	public SexelReader<T> setFilePath(String filePath) {
		this.filePath = filePath;
		return this;
	}

	public SexelReader<T> setSheetName(String sheetName) {
		if (StringUtils.isAllEmpty(sheetName))
			throw new RuntimeException("invalid sheetName");
		sheetsNames.add(sheetName);
		return this;
	}

	public SexelReader<T> setOutputClass(Class<T> outputClass) {
		if (outputClass != null)
			this.outputClass = outputClass;
		return this;
	}

	private void validate() {
		if (StringUtils.isAllEmpty(filePath))
			throw new SexelException("invalid file path");
		String fileExtention = FilenameUtils.getExtension(filePath).toLowerCase();
		if (!(fileExtention.equals(Constants.XLSX_FORMAT) || fileExtention.equals(Constants.XLS_FORMAT)))
			throw new SexelException("only .xlsx and .xls is supported");
	}

	public List<T> doRead() {
		validate();
		initWorkBook();
		List<T> list = new ArrayList<>();
		if (sheetsNames.isEmpty())
			return doReadAllSheets();

		sheetsNames.forEach(item -> list.addAll(doRead(item)));

		return list;
	}

	private List<T> doReadAllSheets() {

		List<T> list = new ArrayList<>();
		int numberOfSheets = workbook.getNumberOfSheets();
		for (int i = 0; i < numberOfSheets; i++) {
			Sheet sheet = workbook.getSheetAt(i);
			list.addAll(doRead(sheet.getSheetName()));

		}
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<T> doRead(String sheetName) {

		Sheet sheet = workbook.getSheet(sheetName);
		return (List<T>) new SheetReader(sheet, getConfigurationProperty()).setOutputClass(outputClass).read();
	}

	private void initWorkBook() {
		try {
			if (FilenameUtils.getExtension(filePath).toLowerCase().equals(Constants.XLS_FORMAT))
				workbook = new HSSFWorkbook(new FileInputStream(new File(filePath)));
			else
				workbook = new XSSFWorkbook(new FileInputStream(new File(filePath)));
		} catch (IOException e) {
			throw new SexelException(e.getMessage(), e);
		}
	}

}
