package com.lgcns.hrm.cv.service;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;

@Slf4j
@Service
public class ExportExcelService {
	private static final String PATH = "src/main/resources/templates/";

	public void exportExcel(String templateName, Map<String, Object> beans, HttpServletResponse response) {
		try (InputStream templateInputStream = getClass().getResourceAsStream("/templates/" + templateName)) {
			Workbook workbook = new XLSTransformer().transformXLS(templateInputStream, beans);
			templateInputStream.close();

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + templateName);
			ServletOutputStream out = response.getOutputStream();
			workbook.write(out);
			out.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
