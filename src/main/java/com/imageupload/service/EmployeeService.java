package com.imageupload.service;

import com.imageupload.entity.Employee;
import com.imageupload.payload.EmployeeDto;
import com.imageupload.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setName(employeeDto.getName());
        employee.setEmail(employeeDto.getEmail());
        try {
            employee.setResume(employeeDto.getResume().getBytes());
            return employeeRepository.save(employee);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Employee getEmployeeById(long id) {
        return employeeRepository.findById(id).get();
    }

    public List<Employee> getAllEmployees(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize);
        Page<Employee> pageEmp = employeeRepository.findAll(pageRequest);
        return pageEmp.getContent();
    }
}
