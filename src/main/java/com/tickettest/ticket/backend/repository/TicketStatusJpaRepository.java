/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket.backend.repository;

import com.tickettest.ticket.backend.exception.TicketStatusNotFoundException;
import com.tickettest.ticket.backend.model.TicketStatus;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author luisrafaelinf
 */
@Service
public class TicketStatusJpaRepository {
    
    @Autowired
    private TicketStatusRepository ticketStatusRepository;
    
    public Long save(TicketStatus ticketStatus) {
        
        TicketStatus save = ticketStatusRepository.save(ticketStatus);
        
        return save.getId();
        
    }
    
    public Iterable<TicketStatus> saveAll(List<TicketStatus> ticketStatuses) {
        Iterable<TicketStatus> saveAll = ticketStatusRepository.saveAll(ticketStatuses);
        return saveAll;
    }
    
    public TicketStatus findById(Long id) {
        
        final TicketStatus ticketStatus = ticketStatusRepository.findById(id)
                .orElseThrow(() -> {
                    return new TicketStatusNotFoundException(String.format("Ticket status id<%d> not found on data base", id));
                });
        
        return ticketStatus;
    }
    
    public List<TicketStatus> findAll() {
        
        Iterable<TicketStatus> status = ticketStatusRepository.findAll();
        return StreamSupport.stream(status.spliterator(), false)
                .sorted((e1, e2) -> e1.getDescription().compareToIgnoreCase(e2.getDescription()))
                .collect(Collectors.toList());
        
    }
    
}
