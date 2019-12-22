/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket.backend.repository;

import com.tickettest.ticket.backend.model.TicketEntry;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author luisrafaelinf
 */
@Service
public class TicketEntryJpaRepository {
    
    @Autowired
    private TicketEntryRepository entryRepository;
    
    public Long save(TicketEntry entry) {
        
        TicketEntry save = entryRepository.save(entry);
        
        return save.getId();
    }
    
    
    public List<TicketEntry> findBetweenDate(LocalDate from, LocalDate to) {
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);
        
        return entryRepository.between(from.atStartOfDay(), to.atTime(LocalTime.MAX));
        
    }
    
    
}
