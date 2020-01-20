package com.dev.springcar.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dev.springcar.entities.Brand;
import com.dev.springcar.entities.Car;

@Repository
public interface CarRepository extends CrudRepository<Car, Integer> {
	
	List<Car> findCarByBrand(Brand brand);
}
