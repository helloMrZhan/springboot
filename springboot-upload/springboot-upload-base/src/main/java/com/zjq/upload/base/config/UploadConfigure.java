package com.zjq.upload.base.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zjq
 * @date 2021/9/29 21:50
 * <p>title:上传配置</p>
 * <p>description:</p>
 */
@Data
@Component
@ConfigurationProperties(prefix ="upload")
public class UploadConfigure {

    // 获取存放位置
    private Map<String, String> localtion;

    // 单个文件大小
    private String maxFileSize;

    // 单次上传总文件大小
    private String maxRequestSize;

    public String getBasePath() {
        String location = "";
        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("win")) {
            location = this.getLocaltion().get("windows");
        } else {
            location = this.getLocaltion().get("linux");
        }
        return location;
    }
}
