/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket.backend.repository;

import com.tickettest.ticket.backend.model.Employee;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author luisrafaelinf
 */
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    
    @Query("select e from Employee e where e.userName = :username and e.password = :password")
    public Optional<Employee> findEmployee(@Param("username") String username, @Param("password") String password);
    
    @Query("select e from Employee e where e.status = true")
    public Iterable<Employee> findEmployeeActive();
    
    
}
