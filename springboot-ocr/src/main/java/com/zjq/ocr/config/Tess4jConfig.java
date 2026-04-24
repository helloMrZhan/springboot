package com.zjq.ocr.config;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class Tess4jConfig {

    @Value("${ocr.tessdata-path:classpath:tessdata}")
    private String tessDataPath;

    @Bean
    public ITesseract tesseract() {
        Tesseract tesseract = new Tesseract();
        String actualPath;

        if (tessDataPath.startsWith("classpath:")) {
            // 获取 classpath 下 tessdata 的实际路径
            String path = tessDataPath.replace("classpath:", "");
            actualPath = getClass().getClassLoader().getResource(path).getPath();
            // 处理路径中的 %20 等编码问题（Windows 常见）
            actualPath = new File(actualPath).getAbsolutePath();
        } else {
            actualPath = tessDataPath;
        }

        tesseract.setDatapath(actualPath);
        return tesseract;
    }
}