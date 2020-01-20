package com.dev.springcar.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dev.springcar.entities.Brand;

@Repository
public interface BrandRepository extends CrudRepository<Brand, Integer> {
	
	List<Brand> findBrandByName(String name);
}
