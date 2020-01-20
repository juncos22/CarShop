package com.dev.springcar.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.springcar.entities.Brand;
import com.dev.springcar.repositories.BrandRepository;

@Service
public class BrandService {
	@Autowired
	private BrandRepository repo;
	
	public boolean addBrand(Brand brand) {
		repo.save(brand);
		return true;
	}
	
	public List<Brand> listBrands() {
		List<Brand> brandList = new ArrayList<Brand>();
		Brand brand;
		for (Brand value : repo.findAll()) {
			brand = value;
			brandList.add(brand);
		}
		
		return brandList;
	}
	
	public List<Brand> findByName(String name) {
		return repo.findBrandByName(name);
	}
	
	public Brand getBrand(int id) {
		return repo.findById(id).orElse(null);
	}
	
	public boolean deleteBrand(int id) {
		Brand brand = repo.findById(id).orElse(null);
		if (brand != null) {
			repo.delete(brand);
			return true;
		}
		return false;
	}
	
	public boolean updateBrand(Brand brand) {
		Brand b = repo.findById(brand.getId()).orElse(null);
		
		if (b != null) {
			b.setName(brand.getName());
			return true;
		}
		return false;
	}
}
