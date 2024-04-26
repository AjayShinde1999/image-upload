package com.imageupload.controller;

import com.imageupload.entity.Employee;
import com.imageupload.payload.EmployeeDto;
import com.imageupload.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Employee> saveEmployeeDetails(EmployeeDto employeeDto) {
        Employee employee = employeeService.saveEmployee(employeeDto);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees(@RequestParam(defaultValue = "1",required = false) int pageNumber,
                                                          @RequestParam(defaultValue = "10",required = false) int pageSize) {
        List<Employee> allEmployees = employeeService.getAllEmployees(pageNumber,pageSize);
        return ResponseEntity.ok(allEmployees);
    }
}
