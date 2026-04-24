package com.zjq.ocr.service;

import com.zjq.ocr.util.ImagePreprocessor;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.TesseractException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class OcrService {

    private static final Logger log = LoggerFactory.getLogger(OcrService.class);

    private final ITesseract tesseract;

    public OcrService(ITesseract tesseract) {
        this.tesseract = tesseract;
    }

    public String recognize(MultipartFile file, String language) throws IOException, TesseractException {
        Path tempFile = Files.createTempFile("ocr_", "_" + file.getOriginalFilename());
        try {
            Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);

            tesseract.setLanguage(language);
            String result = tesseract.doOCR(tempFile.toFile());
            log.info("OCR completed for file: {}, lang: {}", file.getOriginalFilename(), language);
            return result != null ? result.trim() : "";
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }


    // 修改 OcrService.java
    public String recognizeBetter(MultipartFile file, String language) throws IOException, TesseractException {
        // 1. 读取原始字节
        byte[] originalBytes = file.getBytes();

        // 2. 预处理图像（关键！）
        // 方案 A：使用轻量预处理（推荐先试这个）
        byte[] processedBytes = ImagePreprocessor.preprocessForIdCard(originalBytes);

        // 方案 B：如果还是不好，退回简单灰度化
        // byte[] processedBytes = ImagePreprocessor.simpleGrayscale(originalBytes);

        // 3. 写入临时文件
        Path tempFile = Files.createTempFile("ocr_processed_", ".png");
        Files.write(tempFile, processedBytes);

        try {
            tesseract.setLanguage(language);
            // 设置更适合证件的模式
            // ✅ 正确设置 PSM 模式（Tess4J 4.x）
            tesseract.setPageSegMode(6); // = PSM_SINGLE_BLOCK
            // 【重要】设置变量提升中文识别（Tess4J 4.x 支持）
            tesseract.setTessVariable("preserve_interword_spaces", "1"); // 保留单词间距
            tesseract.setTessVariable("tessedit_char_whitelist", "");   // 不限制字符集（身份证含汉字+数字+X）

            String result = tesseract.doOCR(tempFile.toFile());
            return result != null ? result.trim() : "";
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }
}