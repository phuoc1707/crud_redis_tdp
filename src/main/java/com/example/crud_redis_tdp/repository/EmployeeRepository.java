package com.example.crud_redis_tdp.repository;

import com.example.crud_redis_tdp.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepository {

    private HashOperations hashOperations;
    private ListOperations listOperations;
    @Autowired
    private RedisTemplate redisTemplate;

    public EmployeeRepository(RedisTemplate redisTemplate) {

        this.hashOperations = redisTemplate.opsForHash();
        this.listOperations=redisTemplate.opsForList();
        this.redisTemplate = redisTemplate;

    }
    public void saveEmployee(Employee employee){
        hashOperations.put("EMPLOYEE", employee.getId(), employee);
       listOperations.leftPush("EMPLOYEE",employee);
    }

    public List<Employee> findAll(){

//        return hashOperations.values("EMPLOYEE");
        Long lastIndex = listOperations.size("EMPLOYEE") - 1;
        return listOperations.range("EMPLOYEE", 0, lastIndex);
    }

    public Employee findById(Integer id){
//        return (Employee) hashOperations.get("EMPLOYEE", id);

        List<Employee> employees = findAll();
        for (Employee employee : employees) {
            if (employee.getId() == id)
                return employee;
        }

        return new Employee();

    }

    public void update(Employee employee){
        saveEmployee(employee);
    }
    public void delete(Integer id){
//        hashOperations.delete("EMPLOYEE", id);

        listOperations.rightPopAndLeftPush("EMPLOYEE", id);
    }

}
