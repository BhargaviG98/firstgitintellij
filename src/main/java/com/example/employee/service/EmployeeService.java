package com.example.employee.service;

import com.example.employee.Repository.EmployeeRepository;
import com.example.employee.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Profile(value = {"local", "dev", "prod" })
public class EmployeeService{


    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    @Autowired
    private EmployeeRepository employeeRepository;


    public Employee saveEmployee(Employee employee)
    {
        logger.info("Saving employee: {}", employee);
        return employeeRepository.save(employee);
    }


    public void saveEmployeesFromCSV(List<Employee> employees) {
        logger.info("Saving employee: ");
        employeeRepository.saveAll(employees);
    }


    public List<Employee> uploadCSVFile(MultipartFile file) throws IOException {
        logger.info("Uploading CSV file with name: {}", file.getOriginalFilename());
        List<Employee> employees = new BufferedReader(new InputStreamReader(file.getInputStream()))
                .lines()
                .skip(1)
                .map(line -> {
                    String[] data = line.split(",");
                    if (data.length == 3) {
                        Employee employee = new Employee();
                        employee.setId(Long.parseLong(data[0]));
                        employee.setName(data[1]);
                        employee.setMail(data[2]);
                        return employee;
                    } else {
                        logger.error("Invalid data in CSV: {}", line);
                        throw new IllegalArgumentException("Invalid data in CSV: " + line);
                    }
                })
                .collect(Collectors.toList());

        saveEmployeesFromCSV(employees);

        return employees;
    }

    public List<Employee> fetchEmployeeList() {
        logger.debug("Fetching all employees");
        return  employeeRepository.findAll();
    }


    public Optional<Employee> getEmployeeById(Long id)
    {
        logger.debug("get the employees id {}: ",id);
        return employeeRepository.findById(id);
    }

    public Employee updateEmployee(Employee employee,Long id) {
        logger.info("update the employees {} ",employee);
        Employee employee1=employeeRepository.findById(id).get();

        if (Objects.nonNull(employee.getName()) && ! " ".equalsIgnoreCase(employee.getName()))
        {
            employee1.setName(employee.getName());
        }
        if (Objects.nonNull(employee.getMail()) && ! " ".equalsIgnoreCase(employee.getMail()))
        {
            employee1.setMail(employee.getMail());
        }
        return employeeRepository.save(employee1);
    }

    public  void deleteEmployeeById(Long id) {
        logger.info("deleted employee {} ",id);
        employeeRepository.deleteById(id);
    }

}
