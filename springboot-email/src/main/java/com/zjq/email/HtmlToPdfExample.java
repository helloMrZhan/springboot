package com.zjq.email;

//import com.itextpdf.html2pdf.ConverterProperties;
////import com.itextpdf.html2pdf.HtmlConverter;
//import com.itextpdf.html2pdf.HtmlConverter;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.PageSize;
//import com.itextpdf.text.pdf.PdfWriter;
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.layout.properties.UnitValue;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.LineSeparator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HtmlToPdfExample {
    public static void main(String[] args) throws IOException {
        String srcHtmlFile = "D:\\app\\html\\demo1.html";
        String destPdfFile = "D:\\app\\pdf\\file.pdf";

        File source = new File(srcHtmlFile);
        File destination = new File(destPdfFile);

//        try (OutputStream os = new FileOutputStream(new File(destPdfFile))) {
//            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(os));
//            com.itextpdf.layout.Document doc = new com.itextpdf.layout.Document(pdfDoc, new UnitValue(UnitValue.CM, 0), new UnitValue(UnitValue.CM, 0),
//                    new UnitValue(UnitValue.CM, 0), new UnitValue(UnitValue.CM, 0));
//
//            HtmlConverter.convertToPdf(srcHtmlFile, doc);
//            doc.close();
//            System.out.println("PDF created without margins.");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }



        try (FileInputStream is = new FileInputStream(source)) {

            String html = new String(Files.readAllBytes(Paths.get(srcHtmlFile)), StandardCharsets.UTF_8);
            ConverterProperties properties = new ConverterProperties();
            PdfWriter writer = new PdfWriter(destination);
            PdfDocument pdf = new PdfDocument(writer);
            pdf.setDefaultPageSize(new PageSize(540, 763.5f));
            Document document = HtmlConverter.convertToDocument(
                    new ByteArrayInputStream(html.getBytes()), pdf, properties);
//            document.setMargins(0,0,0,0);
//            EndPosition endPosition = new EndPosition();
//            LineSeparator separator = new LineSeparator(endPosition);
//            document.add(separator);
            document.getRenderer().close();
//            PdfPage page = pdf.getPage(1);
//            float y = endPosition.getY() - 36;
//            page.setMediaBox(new Rectangle(0, y, 595, 14400 - y));
            document.close();
//            ConverterProperties properties = new ConverterProperties();
//            properties.setPdfAConformance(PdfAConformance.NONE); // 如果不需要符合 PDF/A 标准
//            properties.setPageHeight(PageSize.A4.getHeight());
//            properties.setPageWidth(PageSize.A4.getWidth());
//            HtmlConverter.convertToPdf(is, new FileOutputStream(destination), properties);
//            HtmlConverter.convertToPdf(is, new FileOutputStream(destination), new ConverterProperties());
//            Document document = new Document(PageSize.A4, 0, 0, 0, 0); // 设置边距
//            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(destination));
//            document.open();
//            // 使用HtmlConverter将HTML转换为PDF
////            HtmlConverter.convertToPdf(is, document);

//            System.out.println(content);
//
//            HtmlConverter.convertToPdf(content, pdfWriter, new ConverterProperties());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}