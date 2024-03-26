package com.learn.project.Employeemanagement.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 5000000)
    @Lob
    private byte[] empImage;
    // @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate createdDate;

    private String name;
    private String designation;
    private String salary;

    @PrePersist
    protected void onCreate() {
        if (createdDate == null) {
            createdDate = LocalDate.now();
        }
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate localDate) {
        // DateTimeFormatter manu = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        this.createdDate = localDate;
    }

    public Employee(String name, String designation, String salary, byte[] empImage, LocalDate createdDate) {
        this.name = name;
        this.designation = designation;
        this.salary = salary;
        this.empImage = empImage;
        this.createdDate = createdDate;
    }

    public Employee() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public byte[] getEmpImage() {
        return empImage;
    }

    public void setEmpImage(byte[] bs) {
        this.empImage = bs;
    }
}
