package com.learn.project.Employeemanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.learn.project.Employeemanagement.model.Employee;
import com.learn.project.Employeemanagement.repo.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // public List<Employee> searchEmployees(String nameKeyword, String designationKeyword, String salaryKeyword) {
    //     return employeeRepository.findByNameContainingAndDesignationContainingAndSalaryContaining(nameKeyword, designationKeyword, salaryKeyword);
    // }
    // public List<Employee> searchEmployees(String name, String designation, String salary) {
    //     if (name != null) {
    //         return employeeRepository.findByNameContaining(name);
    //     } else if (designation != null) {
    //         return employeeRepository.findByDesignationContaining(designation);
    //     } else if (salary != null) {
    //         return employeeRepository.findBySalaryContaining(salary);
    //     } else {
    //         return employeeRepository.findAll();
    //     }
    // }
    public Page<Employee> searchEmployeesByName(String name, Pageable pageable) {
        return employeeRepository.findByNameContaining(name, pageable);
    }
    
    

    // // public Employee addNewProduct(Employee employee) {
    // //     return employeeRepository.save(employee);
    // // }
    
    // public Page<Employee> getAllEmployees(Pageable pageable) {
    //     return employeeRepository.findAll(pageable);
    
    // }

    // public String saveImage(Long employeeId, MultipartFile imageFile) throws IOException {
    //     byte[] imageBytes = imageFile.getBytes();
    //     imageService.saveImage(employeeId, imageBytes);
    //     return "Image saved successfully for employee with ID: " + employeeId;
    // }

    // public byte[] getImageData(Long employeeId) {
    //     return imageService.getImageData(employeeId);
    // }

    // public String deleteImage(Long employeeId) {
    //     imageService.deleteImage(employeeId);
    //     return "Image deleted successfully for employee with ID: " + employeeId;
    // }
    public Page<Employee> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll(); 
    }

    public Employee getEmployeeById(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee.orElse(null);
    }

    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }

    public long countRecords() {
        return employeeRepository.count();
    }

    public List<Employee> getEmployeesInRange(Long startId, Long endId) {
        return employeeRepository.findByIdBetween(startId, endId);
    }

    public ResponseEntity<String> deleteEmployeeById(long empid) {
        if (employeeRepository.existsById(empid)) {
            employeeRepository.deleteById(empid);
            return new ResponseEntity<>("Employee Deleted Successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Employee with Id " + empid + " not found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> deleteAllEmployees() {
        long employeeCount = employeeRepository.count();
        if (employeeCount > 0) {
            employeeRepository.deleteAll();
            return new ResponseEntity<>("All Employees Deleted Successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No Employees to delete", HttpStatus.NOT_FOUND);
        }
    }
}
