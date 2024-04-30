package com.imageupload.service;

import com.imageupload.entity.Employee;
import com.imageupload.payload.DeleteResponse;
import com.imageupload.payload.EmployeeDto;
import com.imageupload.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee saveEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setName(employeeDto.getName());
        employee.setEmail(employeeDto.getEmail());
        employee.setCity(employeeDto.getCity());
        String filename = employeeDto.getResume().getOriginalFilename();
        String fileExtension = getFileExtension(filename);
        employee.setFileName(filename);
        log.info("file name {}",filename);
        employee.setExtension(fileExtension);
        try {
            employee.setResume(employeeDto.getResume().getBytes());
            return employeeRepository.save(employee);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Employee getEmployeeById(long id) {

        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            return optionalEmployee.get();
        }
        throw new IllegalArgumentException("Employee not found with id " + id);
    }

    public List<Employee> getAllEmployees(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        Page<Employee> pageEmp = employeeRepository.findAll(pageRequest);
        return pageEmp.getContent();
    }

    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        return filename.substring(dotIndex + 1);
//        if (StringUtils.hasText(filename)) {
//            int dotIndex = filename.lastIndexOf('.');
//            if (dotIndex >= 0 && dotIndex < filename.length() - 1) {
//                return filename.substring(dotIndex + 1);
//            }
//        }
//        return null;
    }

    public DeleteResponse deleteEmployeeById(long id) {
        employeeRepository.deleteById(id);
        DeleteResponse response = new DeleteResponse();
        response.setMessage("Deleted Successfully");
        return response;
    }

    public Employee updateEmployeeById(EmployeeDto employeeDto, long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setName(employeeDto.getName());
            employee.setEmail(employeeDto.getEmail());
            employee.setCity(employeeDto.getCity());
            try {
                employee.setResume(employeeDto.getResume().getBytes());
                employee.setFileName(employeeDto.getResume().getOriginalFilename());
                String fileExtension = getFileExtension(employeeDto.getResume().getOriginalFilename());
                employee.setExtension(fileExtension);
                return employeeRepository.save(employee);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        throw new IllegalArgumentException("Employee not found with id " + id);
    }
}
