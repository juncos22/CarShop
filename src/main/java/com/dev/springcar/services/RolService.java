package com.dev.springcar.services;

import com.dev.springcar.entities.Rol;
import com.dev.springcar.repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolService {
    @Autowired
    private RolRepository repository;

    public Rol getRol(long id) {
        return repository.findById(id).orElse(null);
    }
}
