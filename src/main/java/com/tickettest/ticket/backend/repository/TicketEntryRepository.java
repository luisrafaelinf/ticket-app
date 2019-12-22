/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket.backend.repository;

import com.tickettest.ticket.backend.model.TicketEntry;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author luisrafaelinf
 */
@Repository
public interface TicketEntryRepository extends CrudRepository<TicketEntry, Long> {
    
    @Query("select t from TicketEntry t where t.fromDate >= :from and t.toDate <= :to")
    public List<TicketEntry> between(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);
    
}
