package com.zjq.jpa.mapper;

import com.zjq.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * <p>用户</p>
 *
 * @Author zjq
 * @Date 2021/9/9
 */
@Repository
public interface UserMapper extends JpaRepository<User,Integer> {

}
