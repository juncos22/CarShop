package com.dev.springcar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.springcar.entities.User;
import com.dev.springcar.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;
	
	public boolean addUser(User user) {
		if (repo.save(user) != null) {
			return true;
		}
		
		return false;
	}
	
	public User findUser(String name, String password) {
		return repo.findUserByNameAndPassword(name, password).orElse(null);
	}

	public User findUserById(int id) {
		return repo.findById(id).orElse(null);
	}

	public boolean updateUser(User user) {
		User u = repo.findById(user.getId()).orElse(null);
		
		if (u != null) {
			u.setDni(user.getDni());
			u.setName(user.getName());
			u.setPhone(user.getPhone());
			u.setAddress(user.getAddress());
			u.setDob(user.getDob());
			u.setPassword(user.getPassword());
			repo.save(u);
			return true;
		}
		return false;
	}
	
	public boolean deleteUser(int id) {
		User user = repo.findById(id).orElse(null);
		if (user != null) {
			repo.delete(user);
			return true;
		}
		return false;
	}
}
