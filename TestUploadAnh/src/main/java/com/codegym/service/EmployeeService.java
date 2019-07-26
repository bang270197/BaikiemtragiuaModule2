package com.codegym.service;

import com.codegym.model.Department;
import com.codegym.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
Iterable<Employee> findAllByDepartment(Department department);

    Employee findById(Long id);

    void save(Employee employee);

    void remove(Long id);

    Page<Employee> findAll(Pageable pageable);
}
