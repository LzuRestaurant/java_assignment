package com.pegasus.utils;

import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.Color;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PdfUtil {

    // 静态持有中文字体基准，方便生成不同大小的字体
    private static BaseFont bfChinese;

    static {
        try {
            // 尝试加载中文字体 (STSong-Light 是 OpenPDF/iTextAsian 的标准中文字体)
            try {
                bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            } catch (Exception e) {
                System.err.println("中文支持库未找到，回退到 Helvetica");
                bfChinese = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void exportStatsPdf(OutputStream os, List<Map<String, Object>> statsData) {
        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, os);
            document.open();

            // 定义各种大小的字体
            Font titleFont = new Font(bfChinese, 18, Font.BOLD);
            Font timeFont = new Font(Font.HELVETICA, 10, Font.ITALIC); // 时间用英文显示
            Font bodyFont = new Font(bfChinese, 11, Font.NORMAL);

            // 1. 标题
            Paragraph title = new Paragraph("飞马医院 - 月度预约统计报表", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20f);
            document.add(title);

            // 2. 生成时间
            String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            Paragraph info = new Paragraph("生成时间: " + dateStr, timeFont);
            info.setAlignment(Element.ALIGN_RIGHT);
            info.setSpacingAfter(10f);
            document.add(info);

            // 3. 创建表格
            PdfPTable table = new PdfPTable(new float[]{3, 1});
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // --- 表头 ---
            addHeaderCell(table, "科室", new Font(bfChinese, 12, Font.BOLD, Color.WHITE));
            addHeaderCell(table, "数量", new Font(bfChinese, 12, Font.BOLD, Color.WHITE));

            // --- 内容 ---
            long totalCount = 0;
            for (Map<String, Object> row : statsData) {
                String deptName = String.valueOf(row.get("deptName"));
                long count = Long.parseLong(String.valueOf(row.get("count")));
                totalCount += count;

                addBodyCell(table, deptName, Element.ALIGN_LEFT, bodyFont);
                addBodyCell(table, String.valueOf(count), Element.ALIGN_CENTER, bodyFont);
            }

            // --- 底部统计 ---
            addFooterCell(table, "总计", new Font(bfChinese, 12, Font.BOLD));
            addFooterCell(table, String.valueOf(totalCount), new Font(bfChinese, 12, Font.BOLD));

            document.add(table);

            // 4. 页脚
            Paragraph footer = new Paragraph("报告由飞马医院管理系统生成。", new Font(Font.HELVETICA, 10, Font.ITALIC, Color.GRAY));
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(30f);
            document.add(footer);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 添加表头
    private static void addHeaderCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(new Color(66, 139, 202));
        cell.setPadding(8f);
        cell.setBorderColor(Color.GRAY);
        table.addCell(cell);
    }

    // 添加内容
    private static void addBodyCell(PdfPTable table, String text, int align, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(align);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(6f);
        cell.setBorderColor(Color.LIGHT_GRAY);
        table.addCell(cell);
    }

    // 添加底部
    private static void addFooterCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(new Color(240, 240, 240));
        cell.setPadding(6f);
        table.addCell(cell);
    }
}