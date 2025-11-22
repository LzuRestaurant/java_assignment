package com.pegasus.utils;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class PdfUtil {

    public static void exportStatsPdf(OutputStream os, List<Map<String, Object>> statsData) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, os);
            document.open();

            // 标题
            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Pegasus Hospital Monthly Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" ")); // 空行

            // 表格
            PdfPTable table = new PdfPTable(2); // 2列: 科室, 预约量
            table.addCell("Department");
            table.addCell("Appointment Count");

            for (Map<String, Object> row : statsData) {
                table.addCell(String.valueOf(row.get("deptName")));
                table.addCell(String.valueOf(row.get("count")));
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}