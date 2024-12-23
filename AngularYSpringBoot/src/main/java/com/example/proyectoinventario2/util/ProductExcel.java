package com.example.proyectoinventario2.util;

import com.example.proyectoinventario2.model.Category;
import com.example.proyectoinventario2.model.Product;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

import static org.apache.poi.ss.util.CellUtil.createCell;

public class ProductExcel {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Product> productList;

    public ProductExcel(List<Product> products) {
        productList = products;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Resultado");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();

        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "ID", style);
        createCell(row, 1, "Nombre", style);
        createCell(row, 2, "Precio", style);
        createCell(row, 3, "Cuantos", style);
        createCell(row, 4, "Categoria", style);
    }

    private void createCell(Row row, int column, Object value, CellStyle style) {
        sheet.autoSizeColumn(column);
        Cell cell = row.createCell(column);

        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }

        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Product result : productList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, String.valueOf(result.getId()), style);
            createCell(row, columnCount++, result.getName(), style);
            createCell(row, columnCount++, String.valueOf(result.getPrice()), style);
            createCell(row, columnCount++, String.valueOf(result.getAccount()), style);
            createCell(row, columnCount++, result.getCategory().getName(), style);
        }
    }

    public void exportExcel(HttpServletResponse response) throws IOException {
        writeHeaderLine(); // Write the header
        writeDataLines(); // Write the data

        try (ServletOutputStream servletOutput = response.getOutputStream()) {
            workbook.write(servletOutput);
            servletOutput.flush();
        } finally {
            workbook.close();
        }
    }
}
