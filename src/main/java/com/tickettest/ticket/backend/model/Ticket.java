/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket.backend.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

/**
 *
 * @author luisrafaelinf
 */

@Entity
public class Ticket implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String subject;
    @Size(max = 10000)
    private String description;
    @ManyToOne
    private Employee user;
    @ManyToOne
    private TicketStatus ticketStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticketId", fetch = FetchType.LAZY)
    private List<TicketEmployee> employees;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticketId", fetch = FetchType.LAZY)
    private List<TicketEntry> entrys;
    

    public Ticket() {
    }

    public Ticket(Employee user, LocalDate date, String subject, TicketStatus ticketStatus) {
        this.user = user;
        this.date = date;
        this.subject = subject;
        this.ticketStatus = ticketStatus;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Employee getUser() {
        return user;
    }

    public void setUser(Employee user) {
        this.user = user;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public List<TicketEmployee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<TicketEmployee> employees) {
        this.employees = employees;
    }

    public List<TicketEntry> getEntrys() {
        return entrys;
    }

    public void setEntrys(List<TicketEntry> entrys) {
        this.entrys = entrys;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ticket other = (Ticket) obj;
        return Objects.equals(this.id, other.id);
    }
    
}
