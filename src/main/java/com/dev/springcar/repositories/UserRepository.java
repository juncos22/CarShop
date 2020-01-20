package com.dev.springcar.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dev.springcar.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	
	Optional<User> findUserByNameAndPassword(String name, String password);
}
