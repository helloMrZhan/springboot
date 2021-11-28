package com.zjq.canal.canalsample.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 数据防问层
 *
 * @author
 * @created 2020年05月29日 10:17:00
 * @Modified_By 阿导 2020/5/29 10:17
 */
@Repository
public interface Mapper {
    @Insert("${sql}")
    void doOption(@Param("sql") String sql);
}
