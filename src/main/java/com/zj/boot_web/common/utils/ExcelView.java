package com.zj.boot_web.common.utils;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.zj.boot_web.common.base.PageData;

/**
 * 导出excel TypesName(类名)：ObjectExcelView Description(描述)： 导出excel
 * 
 * @author DZK
 * @date 2017年11月22日 下午14:20:31
 */
public class ExcelView extends AbstractXlsView {

	/*@SuppressWarnings("deprecation")
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();
		String filename = Tools.date2Str(date, "yyyyMMddHHmmss");
		HSSFSheet sheet;
		HSSFCell cell;
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ filename + ".xls");
		sheet = workbook.createSheet("sheet1");

		@SuppressWarnings("unchecked")
		List<String> titles = (List<String>) model.get("titles");
		int len = titles.size();
		HSSFCellStyle headerStyle = workbook.createCellStyle(); // 标题样式
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont headerFont = workbook.createFont(); // 标题字体
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerFont.setFontHeightInPoints((short) 11);
		headerStyle.setFont(headerFont);
		short width = 20, height = 25 * 20;
		sheet.setDefaultColumnWidth(width);
		for (int i = 0; i < len; i++) { // 设置标题
			String title = titles.get(i);
			cell = getCell(sheet, 0, i);
			cell.setCellStyle(headerStyle);
			setText(cell, title);
		}
		sheet.getRow(0).setHeight(height);

		HSSFCellStyle contentStyle = workbook.createCellStyle(); // 内容样式
		contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		@SuppressWarnings("unchecked")
		List<PageData> varList = (List<PageData>) model.get("varList");
		int varCount = varList.size();
		for (int i = 0; i < varCount; i++) {
			PageData vpd = varList.get(i);
			for (int j = 0; j < len; j++) {
				String varstr = vpd.getString("var" + (j + 1)) != null ? vpd
						.getString("var" + (j + 1)) : "";
				cell = getCell(sheet, i + 1, j);
				cell.setCellStyle(contentStyle);
				setText(cell, varstr);
			}

		}

	}*/

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String fileName = model.get("fileName") +  ".xls";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/ms-excel");// 文件下载
		response.setHeader("Content-Disposition", "attachment; filename="
				+ new String(fileName.getBytes(), "iso8859-1"));
		OutputStream outputStream = response.getOutputStream();

		HSSFWorkbook book = (HSSFWorkbook) workbook;
		HSSFSheet sheet = book.createSheet();

		HSSFRow row = sheet.createRow(0);
		//String[] headers = (String[]) model.get("titles");
		List<String> titles = (List<String>) model.get("titles");
		int size = titles.size();
		for (int i = 0; i < size; i++) {
			row.createCell(i).setCellValue(titles.get(i));
		}

		List<PageData> varList = (List<PageData>) model.get("varList");
		for (int i = 0; i < varList.size(); i++) {
			PageData pd = varList.get(i);
			row = sheet.createRow(i + 1);
			for (int k = 0; k < size; k++) {
				String str = pd.getString("var" + (k + 1)) != null ? pd
						.getString("var" + (k + 1)) : "";
				row.createCell(k).setCellValue(str);
				// row.createCell(1).setCellValue(stu.getName());
				// row.createCell(2).setCellValue(stu.getAge());
			}

		}

		book.write(outputStream);
		outputStream.flush();
		outputStream.close();
	}
}
