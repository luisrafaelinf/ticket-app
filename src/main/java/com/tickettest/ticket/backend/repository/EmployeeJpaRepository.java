/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket.backend.repository;

import com.tickettest.ticket.backend.model.Employee;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Service;

/**
 *
 * @author luisrafaelinf
 */
@Service
public class EmployeeJpaRepository {
   
    private final EmployeeRepository employeeRepository;

    public EmployeeJpaRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    
    public Long save(Employee employee) {
        
        Employee save = employeeRepository.save(employee);
        
        return save.getId();
        
    }
    
    public Iterable<Employee> saveAll(List<Employee> employees) {
        Iterable<Employee> saveAll = employeeRepository.saveAll(employees);
        return saveAll;
    }
    
    public List<Employee> findAll() {
        
        Iterable<Employee> allEmployees = employeeRepository.findAll();
        return StreamSupport.stream(allEmployees.spliterator(), false)
                .sorted((e1, e2) -> e1.name().compareToIgnoreCase(e2.name()))
                .collect(Collectors.toList());
        
    }
    
    public List<Employee> findAllActive() {
        
        Iterable<Employee> allEmployees = employeeRepository.findEmployeeActive();
        return StreamSupport.stream(allEmployees.spliterator(), false)
                .sorted((e1, e2) -> e1.name().compareToIgnoreCase(e2.name()))
                .collect(Collectors.toList());
        
    }
    
    public Employee findEmployeeLogin(String username, String password) {
        Optional<Employee> employee = employeeRepository.findEmployee(username, password);
        
        return employee.orElse(new Employee());
        
    }
    
}
