package com.learn.project.Employeemanagement.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.learn.project.Employeemanagement.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByIdBetween(Long startId, Long endId);
    // public static final Employee employee = new Employee();
    // Employee save(String employee2);

    public List<Employee> findAllByName(String name);

    Page<Employee> findAll(Pageable pageable);
    // List<Employee> findByNameContaining(String keyword);
    // List<Employee> findByDesignationContaining(String keyword);
    // List<Employee> findBySalaryContaining(String keyword);

    // List<Employee> findByNameContainingAndDesignationContainingAndSalaryContaining(String nameKeyword, String designationKeyword, String salaryKeyword); 


    Page<Employee> findByNameContaining(String name, Pageable pageable);

    Page<Employee> findAll(Specification<Employee> specification, Pageable pageable);

    @Query(value = "SELECT  *FROM `employees` WHERE `designation` LIKE %:data% OR `name` LIKE %:data% OR `salary` LIKE %:data% OR `created_date` LIKE %:data%",nativeQuery = true)
    public List<Employee> getDataWithSerachingAndPagination(String data,Pageable pageable);
    

}
    
