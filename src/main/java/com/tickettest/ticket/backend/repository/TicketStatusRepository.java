/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket.backend.repository;

import com.tickettest.ticket.backend.model.Employee;
import com.tickettest.ticket.backend.model.TicketStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author luisrafaelinf
 */
@Repository
public interface TicketStatusRepository extends CrudRepository<TicketStatus, Long> {
    
}
