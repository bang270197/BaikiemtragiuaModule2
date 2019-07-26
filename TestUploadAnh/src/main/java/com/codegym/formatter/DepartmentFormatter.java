package com.codegym.formatter;

import com.codegym.model.Department;
import com.codegym.service.DepartmentService;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class DepartmentFormatter implements Formatter<Department> {
    private DepartmentService departmentService;

    @Override
    public Department parse(String text, Locale locale) throws ParseException {
        return null;
    }

    @Override
    public String print(Department object, Locale locale) {
        return null;
    }
}
