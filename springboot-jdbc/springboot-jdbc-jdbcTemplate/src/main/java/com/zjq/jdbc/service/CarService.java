package com.zjq.jdbc.service;

import com.zjq.jdbc.dao.CarDao;
import com.zjq.jdbc.entity.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zjq
 * @date 2020/3/6 22:11
 * <p>title:</p>
 * <p>company:zjq</p>
 * <p>description:</p>
 */
@Service
public class CarService {

    @Autowired
    CarDao carDao;

    public int addCar(Car car) {
        return carDao.insertCar(car);
    }

    public int delCar(Integer id) {
        return carDao.deleteCarById(id);
    }

    public int update(Car car) {
        return carDao.updateCar(car);
    }

    public Car getCar(Integer id) {
        return carDao.getCarById(id);
    }

    public List<Car> getAllCars() {
        return carDao.getAllCars();
    }


}
