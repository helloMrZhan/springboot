package com.zjq.upload.base.dao;

import com.zjq.upload.base.entity.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zjq
 * @date 2021/9/29 21:50
 * <p>title:文件dao</p>
 * <p>description:</p>
 */
@Repository
public interface FileInfoMapper extends JpaRepository<FileInfo, Long> {

    FileInfo findByFileName(String fileName);

    FileInfo findByFileNameAndValid(String fileName , Boolean valid);

    List<FileInfo> findByValid(Boolean valid);

    List<FileInfo> findByResourceId(String resourceId);
}
