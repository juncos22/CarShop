package com.dev.springcar.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.springcar.entities.Brand;
import com.dev.springcar.entities.Car;
import com.dev.springcar.repositories.CarRepository;

@Service
public class CarService {
	
	@Autowired
	private CarRepository repo;
	
	public boolean addCar(Car car) {
		repo.save(car);
		return true;
	}
	
	public List<Car> listCars() {
		List<Car> carList = new ArrayList<Car>();

		for (Car value : repo.findAll()) {
			carList.add(value);
		}
		
		return carList;
	}
	
	public List<Car> listCarsByBrand(Brand brand) {
		return repo.findCarByBrand(brand);
	}
	
	public boolean updateCar(Car car) {
		Car c = repo.findById(car.getId()).orElse(null);
		if (c != null) {
			c.setBrand(car.getBrand());
			c.setModel(car.getModel());
			c.setPrice(car.getPrice());
			c.setStock(car.getStock());
			c.setUser(car.getUser());
			repo.save(c);
			
			return true;
		}
		
		return false;
	}
	
	public boolean deleteCar(int id) {
		Car c =	repo.findById(id).orElse(null);
		// TODO: ARREGLAR METODO
		if (c != null) {
			repo.delete(c);
			return true;
		}
		return false;
	}
	
	public Car findCarById(int id) {
		return repo.findById(id).orElse(null);
	}
}
