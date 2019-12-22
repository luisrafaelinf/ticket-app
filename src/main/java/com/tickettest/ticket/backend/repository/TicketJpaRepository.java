/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket.backend.repository;

import com.tickettest.ticket.backend.exception.TicketNotFoundException;
import com.tickettest.ticket.backend.model.Ticket;
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
public class TicketJpaRepository {
    
    @Autowired
    private TicketRepository ticketRepository;
    
    public Long save(Ticket ticket) {
        
        Ticket save = ticketRepository.save(ticket);
        
        return save.getId();
    }
    
    public Iterable<Ticket> saveAll(List<Ticket> tickets) {
        
        Iterable<Ticket> saveAll = ticketRepository.saveAll(tickets);
        
        return saveAll;
    }
    
    public Ticket findById(Long id) {
    
        final Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> {
                    return new TicketNotFoundException(String.format("Ticket id<%d> not found on data base", id));
                });
        
        return ticket;
    }
    
    public List<Ticket> findAll() {
        
        Iterable<Ticket> findAll = ticketRepository.findAll();
        
        return StreamSupport.stream(findAll.spliterator(), false)
                .sorted((t1, t2) -> t1.getId() > t1.getId() ? 1 : 0)
                .collect(Collectors.toList());
        
    }
    
    public Integer deleteRelationWithEmployee(Long ticketid) {
    
        Integer deleted = ticketRepository.deleteRelationshipEmployee(ticketid);
        
        return deleted;
        
    }
    
    public void delete(Long ticketid) {
    
        ticketRepository.deleteById(ticketid);
        
        
    }
    
}
