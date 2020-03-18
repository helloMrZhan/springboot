package com.zjq.jdbc.controller;

import com.zjq.jdbc.entity.Car;
import com.zjq.jdbc.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zjq
 * @date 2020/3/6 22:54
 * <p>title:</p>
 * <p>company:zjhcsoft</p>
 * <p>description:</p>
 */
@RestController
@RequestMapping(value = "/car")
public class CarController {

    @Autowired
    private CarService carService;
    
    @RequestMapping(value = "/add")
    public int addCar(Car car) {
        car.setName("法拉利");
        car.setColor("金色");
        return carService.addCar(car);
    }

    @RequestMapping(value = "/del")
    public int delCar(Integer id) {
        return carService.delCar(id);
    }

    @RequestMapping(value = "/update")
    public int update(Car car) {
        car.setId(1);
        car.setName("宾利");
        car.setColor("黑色");
        return carService.update(car);
    }

    @RequestMapping(value = "/get")
    public Car getCar(Integer id) {
        return carService.getCar(id);
    }

    @RequestMapping(value = "/all")
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }
}
