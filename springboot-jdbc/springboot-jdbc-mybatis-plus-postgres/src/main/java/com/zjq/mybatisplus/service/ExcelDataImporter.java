package com.zjq.mybatisplus.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
public class ExcelDataImporter {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Map<String, String> fieldMapping = new HashMap<>();

    // 配置常量
    private static final String TENANT = "your_tenant_id";
    private static final Long APP_ID = 2013801619569352705L;
    private static final String CREATOR = "system_importer";

    // 时间格式（支持多种）
    private static final List<DateTimeFormatter> TIME_FORMATTERS = Arrays.asList(
        DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"),
        DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"),
        DateTimeFormatter.ofPattern("yyyy/MM/dd")
    );

    @PostConstruct
    public void loadFieldMapping() throws Exception {
        Properties props = new Properties();
        Resource resource = new ClassPathResource("field-mapping.properties");
        try (InputStreamReader is = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            props.load(is);
            for (String key : props.stringPropertyNames()) {
                fieldMapping.put(key.trim(), props.getProperty(key).trim());
            }
        }
        System.out.println("Loaded " + fieldMapping.size() + " field mappings.");
    }

    @Transactional
    public void importExcel(String excelFilePath) throws Exception {
        try (Workbook workbook = WorkbookFactory.create(new java.io.File(excelFilePath))) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) throw new IllegalArgumentException("Empty sheet");

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) throw new IllegalArgumentException("No header row");

            List<String> headers = new ArrayList<>();
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                 Cell cell = headerRow.getCell(i);
                String header = cell == null ? "" : cell.getStringCellValue().trim();
                headers.add(header);
            }

            Set<String> unmatchedHeaders = new HashSet<>(headers);

            // 批量插入准备
            List<Map<String, Object>> batchData = new ArrayList<>();

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) continue;

                Map<String, Object> record = new HashMap<>();
                record.put("tenant", TENANT);
                record.put("app_id", APP_ID);
                record.put("creator", CREATOR);
                record.put("create_time", new Timestamp(System.currentTimeMillis()));
                record.put("update_time", new Timestamp(System.currentTimeMillis()));

                for (int colIndex = 0; colIndex < headers.size(); colIndex++) {
                    String header = headers.get(colIndex);
                    String targetField = fieldMapping.get(header);
                    if (targetField == null || targetField.startsWith("id_placeholder")) {
                        continue;
                    }

                    unmatchedHeaders.remove(header); // 标记为已匹配

                    Cell cell = row.getCell(colIndex);
                    Object value = getCellValue(cell);
                    if (value != null) {
                        // 尝试解析时间
                        if (value instanceof String) {
                            String strVal = (String) value;
                            if (!strVal.isEmpty()) {
                                Object parsedTime = tryParseDateTime(strVal);
                                if (parsedTime != null) {
                                    value = parsedTime;
                                }
                            }
                        }
                        record.put(targetField, value);
                    }
                }

                batchData.add(record);
            }

            // 执行批量插入
            insertBatch(batchData);

            // 日志：未匹配的列
            if (!unmatchedHeaders.isEmpty()) {
                System.err.println("⚠️ 以下 Excel 列未找到映射，请检查 field-mapping.properties:");
                unmatchedHeaders.forEach(System.err::println);
            }

            System.out.println("✅ 成功导入 " + batchData.size() + " 条记录");
        }
    }

    private Object getCellValue(Cell cell) {
        if (cell == null) return null;
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return new Timestamp(cell.getDateCellValue().getTime());
                } else {
                    double val = cell.getNumericCellValue();
                    // 避免 48.0 变成 "48.0"，保留整数形式
                    if (val == Math.floor(val)) {
                        return String.valueOf((long) val);
                    } else {
                        return String.valueOf(val);
                    }
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return null;
        }
    }

    private Object tryParseDateTime(String input) {
        if (input == null || input.isEmpty()) return null;
        for (DateTimeFormatter formatter : TIME_FORMATTERS) {
            try {
                LocalDateTime ldt = LocalDateTime.parse(input, formatter);
                return Timestamp.valueOf(ldt);
            } catch (DateTimeParseException ignored) {
            }
        }
        return null;
    }

    private void insertBatch(List<Map<String, Object>> batchData) {
        if (batchData.isEmpty()) return;

        // 构建动态 INSERT
        Set<String> allColumns = new LinkedHashSet<>();
        allColumns.add("id");
        allColumns.add("tenant");
        allColumns.add("app_id");
        allColumns.add("creator");
        allColumns.add("editor");
        allColumns.add("create_time");
        allColumns.add("update_time");

        for (Map<String, Object> record : batchData) {
            allColumns.addAll(record.keySet());
        }
        allColumns.remove("id"); // id 用序列

        String columnList = String.join(", ", allColumns);
        String placeholderList = String.join(", ", Collections.nCopies(allColumns.size(), "?"));

        String sql = "INSERT INTO app_page_data_bak (id, " + columnList + ") VALUES (nextval('app_page_data_id_seq'), " + placeholderList + ")";

        List<Object[]> batchArgs = new ArrayList<>();
        for (Map<String, Object> record : batchData) {
            List<Object> args = new ArrayList<>();
            for (String col : allColumns) {
                args.add(record.getOrDefault(col, null));
            }
            batchArgs.add(args.toArray());
        }

        jdbcTemplate.batchUpdate(sql, batchArgs);
    }
}