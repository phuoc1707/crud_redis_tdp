package com.example.crud_redis_tdp.controller;

import com.example.crud_redis_tdp.entity.Employee;
import com.example.crud_redis_tdp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping
public class Controller {
    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping("/employees")
    public Employee saveEmployee(@RequestBody Employee employee){
        employeeRepository.saveEmployee(employee);
        return employee;
    }

//    @GetMapping("/employees")
//    public List<Employee> findAll(){
//        System.out.println("11111111");
//        return employeeRepository.findAll();
//    }
@GetMapping("/employees")
public Set<Employee> findAll(){
    System.out.println("11111111");
    return employeeRepository.findAll();
}
    @GetMapping("/employees/{id}")
    public Employee findById(@PathVariable("id") Integer id){

        return employeeRepository.findById(id);
    }

    @PutMapping("/employee}")
    public void update(@RequestBody Employee employee){

        employeeRepository.update(employee);
    }

    @DeleteMapping("/employees/{id}")
    public void delete(@PathVariable("id") Integer id){
        employeeRepository.delete(id);
    }
}
