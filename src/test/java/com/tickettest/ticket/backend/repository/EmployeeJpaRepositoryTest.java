package com.tickettest.ticket.backend.repository;

import com.tickettest.ticket.backend.model.Employee;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author luisrafaelinf
 */
public class EmployeeJpaRepositoryTest {

    EmployeeJpaRepository ejr;
    EmployeeRepository mock;

    @Before
    public void setUp() {
        mock = mock(EmployeeRepository.class);
        ejr = new EmployeeJpaRepository(mock);

    }

    @After
    public void tearDown() {

        mock = null;
        ejr = null;

    }

    @Test
    public void testSomeMethod() {
        
        Employee e = new Employee(1l);
        
        when(mock.save(any(Employee.class)))
                .thenReturn(e);
                
        Long save = ejr.save(new Employee());
        
        Assert.assertEquals(e.getId(), save);
        
        
    }

}
