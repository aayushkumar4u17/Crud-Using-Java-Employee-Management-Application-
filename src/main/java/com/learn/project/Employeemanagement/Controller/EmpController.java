package com.learn.project.Employeemanagement.Controller;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.learn.project.Employeemanagement.Response.GlobalResponse;
import com.learn.project.Employeemanagement.model.Employee;
import com.learn.project.Employeemanagement.repo.EmployeeRepository;
import com.learn.project.Employeemanagement.service.EmployeeService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class EmpController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRepository empRepo;

    public ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        // Add any additional configuration if needed
        return objectMapper;
    }

    @PostMapping("/employees")
    public ResponseEntity<GlobalResponse> createEmployee(
            @RequestParam(value = "file", required = true) MultipartFile file, // @RequestBody
            @RequestParam(value = "data", required = true) String data) throws IOException {

        ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder().build();
        Employee employee = objectMapper.readValue(data, Employee.class);

        // Set the createdDate to the current date
        employee.setCreatedDate(LocalDate.now());

        // If the 'file' parameter is not required, you can check for null before
        // setting the image
        if (file != null) {
            employee.setEmpImage(file.getBytes());
        }

        Employee savedEmployee = this.employeeService.saveEmployee(employee);

        if (savedEmployee != null) {
            return new ResponseEntity<>(new GlobalResponse("Data inserted successfully"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new GlobalResponse("Data is not inserted successfully"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    // @PostMapping("/employees")
    // public ResponseEntity<GlobalResponse> createEmployee(
    // @RequestParam(value = "file", required = true) MultipartFile file,
    // @RequestParam(value = "data", required = true) String data) throws
    // IOException {
    // ObjectMapper objectMapper = new ObjectMapper();
    // Employee employee = objectMapper.readValue(data, Employee.class);

    // JsonNode jsonNode = objectMapper.readTree(data);
    // if (jsonNode.has("createdDate")) {
    // LocalDate createdDate =
    // LocalDate.parse(jsonNode.get("createdDate").asText());
    // employee.setCreatedDate(createdDate);
    // }
    // employee.setEmpImage(file.getBytes());
    // Employee saveEmployee = this.employeeService.saveEmployee(employee);
    // if (saveEmployee != null) {
    // return new ResponseEntity<GlobalResponse>(new GlobalResponse("data inserted
    // successfully"), HttpStatus.OK);
    // } else {
    // return new ResponseEntity<GlobalResponse>(new GlobalResponse("data is not
    // inserted successfully"),
    // HttpStatus.BAD_REQUEST);

    // }
    // }

    // @PostMapping("/employees")
    // public ResponseEntity<GlobalResponse> createEmployee(
    // @RequestParam(value = "file", required = true) MultipartFile file,
    // @RequestParam(value = "data", required = true) String data) throws
    // IOException {
    // ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder().build();
    // Employee employee = objectMapper.readValue(data, Employee.class);

    // // Parse and set the createdDate from the JSON string
    // JsonNode jsonNode = objectMapper.readTree(data);
    // if (jsonNode.has("createdDate")) {
    // LocalDate createdDate =
    // LocalDate.parse(jsonNode.get("createdDate").asText());
    // employee.setCreatedDate(createdDate);
    // }

    // employee.setEmpImage(file.getBytes());
    // Employee saveEmployee = this.employeeService.saveEmployee(employee);

    // if (saveEmployee != null) {
    // return new ResponseEntity<>(new GlobalResponse("Data inserted successfully"),
    // HttpStatus.OK);
    // } else {
    // return new ResponseEntity<>(new GlobalResponse("Data is not inserted
    // successfully"),
    // HttpStatus.BAD_REQUEST);
    // }
    // }

    // @GetMapping("/employees/{id}/image")
    // public byte[] getEmployeeImage(@PathVariable Long id) throws IOException {
    // Employee employee = employeeService.getEmployeeById(id);
    // System.out.println(employee.getEmpImage());
    // ByteArrayInputStream bis = new ByteArrayInputStream(employee.getEmpImage());
    // BufferedImage image = ImageIO.read(bis);
    // bis.close();

    // // Convert BufferedImage back to byte array
    // ByteArrayOutputStream bos = new ByteArrayOutputStream();
    // ImageIO.write(image,"jpg", bos);
    // byte[] imageBytes = bos.toByteArray();
    // bos.close();

    // return imageBytes;

    // }

    // @GetMapping("/employees/search")
    // public ResponseEntity<GlobalResponse> searchEmployees(@RequestParam(required
    // = false) String name,
    // @RequestParam(required = false) String designation, @RequestParam(required =
    // false) String salary) {
    // List<Employee> searchedEmployees = employeeService.searchEmployees(name,
    // designation, salary);

    // if (searchedEmployees.isEmpty()) {
    // return new ResponseEntity<GlobalResponse>(
    // new GlobalResponse("No employees found with the specified criteria."),
    // HttpStatus.NOT_FOUND);
    // } else {
    // return new ResponseEntity<GlobalResponse>(
    // new GlobalResponse("Employees found with the specified criteria."),
    // HttpStatus.OK);
    // }
    // }
    // @GetMapping("/employees/search")
    // public ResponseEntity<Page<Employee>> searchEmployeesByName(
    // @RequestParam(required = false) String name,
    // @RequestParam(defaultValue = "0") int page,
    // @RequestParam(defaultValue = "2") int size) {

    // Pageable pageable = PageRequest.of(page, size);
    // Page<Employee> searchedEmployees =
    // employeeService.searchEmployeesByName(name, pageable);

    // return ResponseEntity.ok(searchedEmployees);
    // }

    @GetMapping("/employees/search")
    public ResponseEntity<Page<Employee>> getDataWithSearchingAndPagination(
            @RequestParam String data,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);
        List<Employee> employees = empRepo.getDataWithSerachingAndPagination(data, pageable);

        return ResponseEntity.ok(new PageImpl<>(employees, pageable, employees.size()));
    }
    @GetMapping("/employees/")
    public ResponseEntity<Page<Employee>> getAllEmployees(@RequestParam(defaultValue = "0", value = "page") int page,
            @RequestParam(defaultValue = "5", value = "size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Employee> employees = employeeService.getAllEmployees(pageable);
        System.out.println(employees.getContent());
        for (Employee employee : employees.getContent()) {
            System.out.println(employee.getEmpImage());
        }
        return ResponseEntity.ok(employees);
    }

    // @GetMapping("/employees/{id}/image")
    // public ResponseEntity<byte[]> getImage(
    // @PathVariable("id") Long employeeId) {
    // byte[] imageData = imageService.getImageData(employeeId);

    // if (imageData != null) {
    // HttpHeaders headers = new HttpHeaders();
    // headers.setContentType(MediaType.ALL);
    // return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    // } else {
    // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    // }
    // }

    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long empid) {

        Optional<Employee> emp = employeeService.findById(empid);
        if (emp.isPresent()) {
            return new ResponseEntity<Employee>(emp.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Employee>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/employees/count")
    public ResponseEntity<Long> getCount() {
        long count = employeeService.countRecords();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/employees/{startId}/{endId}")
    public ResponseEntity<List<Employee>> getEmployeesInRange(@PathVariable Long startId, @PathVariable Long endId) {
        List<Employee> employeesInRange = employeeService.getEmployeesInRange(startId, endId);
        return new ResponseEntity<>(employeesInRange, HttpStatus.OK);
    }

    // @PutMapping("/employees/{employeeId}")
    // public ResponseEntity<GlobalResponse> updateEmployee(
    // @PathVariable Long employeeId,
    // @RequestPart(value = "file", required = false) MultipartFile file,
    // @RequestPart(value = "data", required = true) String data) throws IOException
    // {

    // ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder().build();
    // Employee existingEmployee = employeeService.getEmployeeById(employeeId);
    // // System.out.println("data -- >> "+data);

    // if (existingEmployee == null) {
    // return new ResponseEntity<>(new GlobalResponse("Employee not found"),
    // HttpStatus.NOT_FOUND);
    // }

    // // Update employee data based on the provided JSON string
    // Employee updatedData = objectMapper.readValue(data, Employee.class);
    // existingEmployee.setEmpImage(updatedData.getEmpImage());
    // existingEmployee.setName(updatedData.getName());
    // existingEmployee.setDesignation(updatedData.getDesignation());
    // existingEmployee.setSalary(updatedData.getSalary());

    // // Update other fields as needed

    // // Update the createdDate if provided in the JSON string
    // JsonNode jsonNode = objectMapper.readTree(data);
    // // Update the createdDate if provided in the ISO 8601 format in the JSON
    // string
    // // Update the createdDate if provided in the ISO 8601 format in the JSON
    // string
    // if (jsonNode.has("createdDate")) {
    // String dateString = jsonNode.get("createdDate").asText();
    // Instant instant = Instant.parse(dateString);

    // // Convert Instant to LocalDate
    // LocalDate createdDate =
    // instant.atZone(ZoneId.of("Asia/Kolkata")).toLocalDate();

    // existingEmployee.setCreatedDate(createdDate);
    // }

    // // Update the image if a new one is provided
    // if (!file.isEmpty()) {
    // existingEmployee.setEmpImage(file.getBytes());
    // }

    // // Save the updated employee
    // Employee updatedEmployeeResult =
    // employeeService.saveEmployee(existingEmployee);

    // if (updatedEmployeeResult != null) {
    // return new ResponseEntity<>(new GlobalResponse("Employee updated
    // successfully"), HttpStatus.OK);
    // } else {
    // return new ResponseEntity<>(new GlobalResponse("Failed to update employee"),
    // HttpStatus.BAD_REQUEST);
    // }
    // }

    @PutMapping("/employees/{employeeId}")
    public ResponseEntity<GlobalResponse> updateEmployee(
            @PathVariable Long employeeId,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart(value = "data", required = true) String data) throws IOException {

        ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder().build();

        Employee existingEmployee = employeeService.getEmployeeById(employeeId);
        if (existingEmployee == null) {
            return new ResponseEntity<>(new GlobalResponse("Employee not found"), HttpStatus.NOT_FOUND);
        }

        // Update employee data based on the provided JSON string
        Employee updatedData = objectMapper.readValue(data, Employee.class);
        existingEmployee.setName(updatedData.getName());
        existingEmployee.setDesignation(updatedData.getDesignation());
        existingEmployee.setSalary(updatedData.getSalary());

        // Update createdDate if provided
        JsonNode jsonNode = objectMapper.readTree(data);
        if (jsonNode.has("createdDate")) {
            String dateString = jsonNode.get("createdDate").asText();
            Instant instant = Instant.parse(dateString);
            LocalDate createdDate = instant.atZone(ZoneId.of("Asia/Kolkata")).toLocalDate();
            existingEmployee.setCreatedDate(createdDate);
        }

        // Update image only if a new one is provided (using a ternary operator)
        existingEmployee.setEmpImage(file.isEmpty() ? existingEmployee.getEmpImage() : file.getBytes());

        // Save the updated employee
        Employee updatedEmployeeResult = employeeService.saveEmployee(existingEmployee);

        if (updatedEmployeeResult != null) {
            return new ResponseEntity<>(new GlobalResponse("Employee updated successfully"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new GlobalResponse("Failed to update employee"), HttpStatus.BAD_REQUEST);
        }
    }

    // @PutMapping("/employees/{id}")
    // public ResponseEntity<GlobalResponse> updateEmployee(
    // @PathVariable("id") Long id,
    // @RequestParam(value = "file", required = false) MultipartFile file,
    // @RequestParam(value = "data", required = true) String data) throws
    // IOException {
    // Employee existingEmployee = employeeService.getEmployeeById(id);

    // if (existingEmployee == null) {
    // return new ResponseEntity<GlobalResponse>(new GlobalResponse("Employee not
    // found"), HttpStatus.NOT_FOUND);
    // }

    // ObjectMapper objectMapper = new ObjectMapper();
    // Employee updatedEmployee = objectMapper.readValue(data, Employee.class);

    // if (file != null) {
    // updatedEmployee.setEmpImage(file.getBytes());
    // } else {
    // // If no new image is provided, keep the existing one
    // updatedEmployee.setEmpImage(existingEmployee.getEmpImage());
    // }

    // updatedEmployee.setId(existingEmployee.getId());

    // Employee savedEmployee = employeeService.saveEmployee(updatedEmployee);

    // if (savedEmployee != null) {
    // return new ResponseEntity<GlobalResponse>(new GlobalResponse("Data updated
    // successfully"), HttpStatus.OK);
    // } else {
    // return new ResponseEntity<GlobalResponse>(new GlobalResponse("Data is not
    // updated successfully"),
    // HttpStatus.BAD_REQUEST);
    // }
    // }
    // @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/employees/{id}")
    public GlobalResponse deleteEmployeeByEmpId(@PathVariable("id") long id) {

        System.out.println("emp id == " + id);
        employeeService.deleteEmployeeById(id);
        return new GlobalResponse("Employee Deleted Successfully");
    }

    // @DeleteMapping("employees")
    // public GlobalResponse deleteAllEmployee() {
    // employeeService.deleteAllEmployees();
    // return new GlobalResponse("Employee Deleted Successfully");
    // }

}
