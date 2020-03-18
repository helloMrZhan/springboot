package com.zjq.jdbc.dao;

import com.zjq.jdbc.entity.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zjq
 * @date 2020/3/6 22:16
 * <p>title:</p>
 * <p>company:zjhcsoft</p>
 * <p>description:</p>
 */
/**
 * 创建 CarDao,注入 JabcTemplate.由于已经添加了 spring-jdbc相关的依赖, JdbcTemplate会被
 * 自动注册到 Spring容器中,因此这里可以直接注入 JdbcTemplate使用.
 在 JdbcTemplate中,增删改三种类型的操作主要使用 update和 batchUpdate方法来完成. query和
 query ForObject方法主要用来完成查询功能.另外,还有 execute方法可以用来执行任意的SQL、call方法用来调用存储过程等.
 在执行查询操作时,需要有一个 RowMapper将查询出来的列和实体类中的属性一一对应起来.如果列名和
 属性名都是相同的,那么可以直接使用 Bean Property RowMapper;如果列名和属性名不同,就需要开发者
 自己实现 RowMapper接口,将列和实体类属性一一对应起来
 */
@Repository
public class CarDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public int insertCar(Car car) {
        return jdbcTemplate.update("INSERT  into car (name,color) VALUES" +
                " (?,?)", car.getName(), car.getColor());
    }

    public int deleteCarById(Integer id) {
        return jdbcTemplate.update("delete car where id = ?", id);
    }

    public int updateCar(Car car) {
        return jdbcTemplate.update("UPDATE car set name = ?,color=? where id = ?",
                car.getName(), car.getColor(), car.getId());
    }

    public Car getCarById(int id) {
        Car car = jdbcTemplate.queryForObject("select * from car where id = ?",
                new BeanPropertyRowMapper<>(Car.class), id);
        return car;
    }

    public List<Car> getAllCars(){
        List<Car> carList = jdbcTemplate.query("select * from car",
                new BeanPropertyRowMapper<>(Car.class));
        return carList;
    }

}
