package com.example.employee.controller;

import com.example.employee.entity.Employee;
import com.example.employee.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class EmployeeController {

    //GitHub Repository
    //One
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/public/welcome")
    public String welcome()
    {
        return "welcome to spring boot security";
    }

    @PostMapping("/admin/employees")
    @PreAuthorize("hasRole('ADMIN')")
    public Employee saveEmployee(@RequestBody Employee employee) {
        logger.info("Request to save employee: {}", employee);
        return employeeService.saveEmployee(employee);
    }

    @GetMapping("/user/employees")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Employee> fetchEmployeeList() {
        logger.info("Fetching list of all employees");
        return employeeService.fetchEmployeeList();
    }

    @GetMapping("/admin/employees/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
        logger.info("Fetching employee with ID: {}", id);
        if (id <= 0) {
            logger.error("Invalid employee ID provided: {}", id);
            throw new IllegalArgumentException(id.toString());
        }
        Optional<Employee> employeeOptional = employeeService.getEmployeeById(id);
        if (employeeOptional.isPresent()) {
            logger.info("Employee found: {}", employeeOptional.get());
            return ResponseEntity.ok(employeeOptional.get());
        } else {
            logger.error("Employee not found with ID: {}", id);
            throw new NoSuchElementException(id.toString());

        }
    }

    @PutMapping("/admin/employees/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Employee updateEmployee(@RequestBody Employee employee, @PathVariable("id") Long id) {
        logger.info("Updating employee with ID: {}", id);
        return employeeService.updateEmployee(employee, id);
    }

    @PostMapping("/upload")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> uploadCSVFile(@RequestParam("file") MultipartFile file) throws IOException {
        logger.info("Uploading CSV file: {}", file.getOriginalFilename());
        List<Employee> employees = employeeService.uploadCSVFile(file);
        logger.info("File uploaded successfully: {}. Processed {} employees", file.getOriginalFilename(), employees.size());
        return ResponseEntity.status(HttpStatus.OK).body("Uploaded the file successfully: " + file.getOriginalFilename());
    }

    @DeleteMapping("/admin/employees/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteEmployeeById(@PathVariable("id") Long id) {
        logger.info("Deleting employee with ID: {}", id);
        employeeService.deleteEmployeeById(id);
        logger.info("Employee deleted successfully: {}", id);
        return "Employee deleted successfully";

    }
}


