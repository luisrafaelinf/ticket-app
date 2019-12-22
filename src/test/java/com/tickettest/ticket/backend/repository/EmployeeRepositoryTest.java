package com.tickettest.ticket.backend.repository;

import com.tickettest.ticket.backend.model.Employee;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author luisrafaelinf
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void whenFindByName_thenReturnEmployee() {
        // given
        Employee user = new Employee();
        user.setUserName("admin");
        user.setPassword("admin");
        entityManager.persist(user);
        entityManager.flush();

        // when
        Optional<Employee> found = employeeRepository.findEmployee("admin", "admin");
        Optional<Employee> notFound = employeeRepository.findEmployee("admin", "rest");

        // then
        Assert.assertTrue(found.isPresent());
        Assert.assertFalse(notFound.isPresent());
    }
}
