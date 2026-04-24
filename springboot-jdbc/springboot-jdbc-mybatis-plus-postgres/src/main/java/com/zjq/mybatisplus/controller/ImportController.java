package com.zjq.mybatisplus.controller;

import com.zjq.mybatisplus.service.ExcelDataImporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;

@RestController
public class ImportController {

    @Autowired
    private ExcelDataImporter importer;

    @PostMapping("/import")
    public String upload(@RequestParam("file") MultipartFile file) throws Exception {
        Path tempFile = Files.createTempFile("import_", ".xlsx");
        file.transferTo(tempFile);
        importer.importExcel(tempFile.toString());
        Files.deleteIfExists(tempFile);
        return "Import success!";
    }
}