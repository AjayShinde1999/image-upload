package com.imageupload.controller;

import com.imageupload.entity.Employee;
import com.imageupload.payload.DeleteResponse;
import com.imageupload.payload.EmployeeDto;
import com.imageupload.service.EmployeeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public ResponseEntity<List<Employee>> getAllEmployees(@RequestParam(defaultValue = "1", required = false) int pageNumber,
                                                          @RequestParam(defaultValue = "10", required = false) int pageSize) {
        List<Employee> allEmployees = employeeService.getAllEmployees(pageNumber, pageSize);
        return ResponseEntity.ok(allEmployees);
    }

    @GetMapping("/{id}/file")
    public ResponseEntity<byte[]> fileDownload(@PathVariable long id) {
        Employee employee = employeeService.getEmployeeById(id);

        byte[] fileBytes = employee.getResume();
        String fileExtension = employee.getExtension();
        String fileName = employee.getFileName();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName + "." + fileExtension);
        return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deleteEmployee(@PathVariable long id) {
        return ResponseEntity.ok(employeeService.deleteEmployeeById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(EmployeeDto employeeDto, @PathVariable long id){
        return ResponseEntity.ok(employeeService.updateEmployeeById(employeeDto,id));
    }
}
