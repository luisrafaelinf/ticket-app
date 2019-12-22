/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket.backend.repository;

import com.tickettest.ticket.backend.model.Ticket;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author luisrafaelinf
 */
@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {
    
    @Modifying
    @Transactional
    @Query("Delete from TicketEmployee e where e.ticketId.id = :id")
    public Integer deleteRelationshipEmployee(@Param("id") Long id);
    
}
