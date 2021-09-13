package com.zjq.tkmybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
//这个注解注意不要用错误了，不然请求访问会报错
// java.lang.NoSuchMethodException: tk.mybatis.mapper.provider.base.BaseSelectProvider
@MapperScan(basePackages = "com.zjq.tkmybatis.mapper")
public class MybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisApplication.class, args);
	}

}
