package com.example.employee.service;

import com.example.employee.entity.Employee;

import java.util.List;

public interface Empserint {
    
    Employee saveEmployee(Employee employee);

    Employee saveEmployeesFromCSV(Employee employee);

    List<Employee> fetchEmployeeList();

    Employee getEmployeeById(Long id);

    Employee updateEmployee(Employee employee,Long id);

    void deleteEmployeeById(Long id);

    void welcome();
}
