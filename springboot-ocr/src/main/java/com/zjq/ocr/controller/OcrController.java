package com.zjq.ocr.controller;

import com.zjq.ocr.service.OcrService;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/ocr")
public class OcrController {

    @Autowired
    private OcrService ocrService;

    @PostMapping("/recognize")
    public ResponseEntity<String> recognize(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "lang", defaultValue = "chi_sim") String lang) {

        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("文件不能为空");
        }

        try {
            String text = ocrService.recognize(file, lang);
//            String text = ocrService.recognizeBetter(file, lang);
            return ResponseEntity.ok(text);
        } catch (TesseractException e) {
            return ResponseEntity.status(500).body("OCR识别失败: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("系统错误: " + e.getMessage());
        }
    }
}