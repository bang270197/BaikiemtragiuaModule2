package com.codegym.service;

import com.codegym.model.Department;
import com.codegym.repository.DepartmentRepository;

public interface DepartmentService {
    Iterable<Department> findAll();

    Department findById(Long id);

    void save(Department department);

    void remove(Long id);

    Department findByName(String name);
}
