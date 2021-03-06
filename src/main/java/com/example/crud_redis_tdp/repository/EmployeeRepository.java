package com.example.crud_redis_tdp.repository;

import com.example.crud_redis_tdp.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class EmployeeRepository {

    private HashOperations hashOperations;
    private ListOperations listOperations;
    private SetOperations setOperations;
    @Autowired
    private RedisTemplate redisTemplate;

    public EmployeeRepository(RedisTemplate redisTemplate) {

        this.hashOperations = redisTemplate.opsForHash();
        this.listOperations=redisTemplate.opsForList();
        this.setOperations= redisTemplate.opsForSet();
        this.redisTemplate = redisTemplate;

    }
    public void saveEmployee(Employee employee){
//        hashOperations.put("EMPLOYEE", employee.getId(), employee);
//        listOperations.leftPush("EMPLOYEE_LIST",employee);
        setOperations.add("EMPLOYEE_SET",employee);
    }


    public Set<Employee> findAll(){
/*
        hash
        return hashOperations.values("EMPLOYEE");
        list
        Long lastIndex = listOperations.size("EMPLOYEE_LIST") - 1;
        return listOperations.range("EMPLOYEE_LIST", 0, lastIndex);
*/

        return setOperations.members("EMPLOYEE_SET");

    }

    public Employee findById(Integer id){
//        hash
//        return (Employee) hashOperations.get("EMPLOYEE", id);

//        list && set

        Set<Employee> employees = findAll();
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

//        listOperations.rightPopAndLeftPush("EMPLOYEE_LIST", id);
        setOperations.remove("EMPLOYEE_SET", findById(id));
    }

}
