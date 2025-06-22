package com.zjq.email;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDInlineImage;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HtmlToPdf {
    public static void main(String[] args) {
        try {
            // 从文件中读取HTML内容
            String htmlFilePath = "D:\\app\\html\\222.html";
            String htmlContent = new String(Files.readAllBytes(Paths.get(htmlFilePath)));

            // 创建输出流和PDF生成器
            OutputStream os = new FileOutputStream("D:\\app\\pdf\\file.pdf");
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(htmlContent, new File(htmlFilePath).toURI().toString());
            builder.toStream(os);
            builder.run();
            os.close();


//            try (PDDocument document = PDDocument.load(new File("D:\\app\\pdf\\file.pdf"))) {
//                // 创建一个新的 PDF 文档用于保存裁剪后的页面
//                PDDocument outputDocument = new PDDocument();
//
//                PDFRenderer renderer = new PDFRenderer(document);
//
//                for (int pageIndex = 0; pageIndex < document.getNumberOfPages(); pageIndex++) {
//                    PDPage page = (PDPage) document.getPage(pageIndex);
//                    BufferedImage image = renderer.renderImage(pageIndex); // 使用 renderImage 方法
//
//                    // 计算图像的非空白区域
//                    int minX = image.getWidth();
//                    int maxX = 0;
//                    int minY = image.getHeight();
//                    int maxY = 0;
//                    boolean foundNonWhitePixel = false;
//                    for (int x = 0; x < image.getWidth(); x++) {
//                        for (int y = 0; y < image.getHeight(); y++) {
//                            if (image.getRGB(x, y) != 0xffffffff) { // 检测到非白色像素
//                                foundNonWhitePixel = true;
//                                minX = Math.min(minX, x);
//                                maxX = Math.max(maxX, x);
//                                minY = Math.min(minY, y);
//                                maxY = Math.max(maxY, y);
//                            }
//                        }
//                    }
//
//                    // 如果没有找到非白色像素，则使用原始页面尺寸
//                    if (!foundNonWhitePixel) {
//                        minX = 0;
//                        minY = 0;
//                        maxX = image.getWidth();
//                        maxY = image.getHeight();
//                    }
//
//                    // 为裁剪框增加一定的边距
//                    int margin = 10; // 增加 10 个像素作为边距
//                    minX = Math.max(0, minX - margin);
//                    minY = Math.max(0, minY - margin);
//                    maxX = Math.min(image.getWidth(), maxX + margin);
//                    maxY = Math.min(image.getHeight(), maxY + margin);
//
//                    // 更新页面的裁剪框
//                    PDRectangle cropBox = new PDRectangle(minX, minY, maxX - minX, maxY - minY);
//                    PDPage croppedPage = new PDPage(cropBox);
//                    outputDocument.addPage(croppedPage);
//
//                    // 复制页面内容到新页面
//                    PDPageContentStream contentStream = new PDPageContentStream(outputDocument, croppedPage);
//                    contentStream.addRect(minX, minY, maxX - minX, maxY - minY);
//                    contentStream.clip();
//                    contentStream.appendRawCommands("q\n");
//                    contentStream.drawImage(image, 0, 0); // 使用 BufferedImage 直接绘制
//                    contentStream.appendRawCommands("Q\n");
//                    contentStream.close();
//                }
//
//                // 保存裁剪后的 PDF
//                outputDocument.save("path/to/output.pdf");
//                outputDocument.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}