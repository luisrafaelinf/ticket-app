/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket.backend;

import com.tickettest.ticket.backend.model.Employee;
import com.tickettest.ticket.backend.model.Ticket;
import com.tickettest.ticket.backend.model.TicketEmployee;
import com.tickettest.ticket.backend.model.TicketEntry;
import com.tickettest.ticket.backend.model.TicketStatus;
import com.tickettest.ticket.backend.repository.EmployeeRepository;
import com.tickettest.ticket.backend.repository.TicketEntryRepository;
import com.tickettest.ticket.backend.repository.TicketRepository;
import com.tickettest.ticket.backend.repository.TicketStatusRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author luisrafaelinf
 */
@Component
public class CmdLineRunner implements CommandLineRunner {

    @Autowired
    private EmployeeRepository employeeJpaRepository;

    @Autowired
    private TicketStatusRepository ticketStatusJpaRepository;

    @Autowired
    private TicketRepository ticketJpaRepository;
    
    @Autowired
    private TicketEntryRepository entryJpaRepository;

    @Override
    public void run(String... args) throws Exception {

        populateEmployees();
        populateTicketStatus();
        populateTickets();
        populateTicketEntry();

    }

    private void populateEmployees() {
        List<Employee> employees = Arrays.asList(
                new Employee("Admin", "Admin", "Admin", "admin0@bbc.co.uk", true, "admin", LocalDate.now()),
                new Employee("Rowena", "Rowena", "Leeming", "rleeming0@bbc.co.uk", true, "1234", LocalDate.now()),
                new Employee("Alvinia", "Alvinia", "Delong", "adelong1@altervista.org", true, "1234", LocalDate.now()),
                new Employee("Leodora", "Leodora", "Burry", "lburry2@example.com", false, "1234", LocalDate.now()),
                new Employee("Karen", "Karen", "Oaten", "koaten3@ihg.com", true, "1234", LocalDate.now()),
                new Employee("Mariele", "Mariele", "Huke", "mhuke4@washingtonpost.com", true, "1234", LocalDate.now()),
                new Employee("Grata", "Grata", "Widdowes", "gwiddowes5@cargocollective.com", false, "1234", LocalDate.now()),
                new Employee("Donna", "Donna", "Roadknight", "droadknight6@apache.org", true, "1234", LocalDate.now())
        );

        employeeJpaRepository.saveAll(employees);
    }

    private void populateTicketStatus() {
        List<TicketStatus> ticketStatuses = Arrays.asList(
                new TicketStatus("Open"),
                new TicketStatus("Close"),
                new TicketStatus("Reasigned")
        );

        ticketStatusJpaRepository.saveAll(ticketStatuses);

    }

    private void populateTickets() {

        Iterable<Employee> allEmployee = employeeJpaRepository.findEmployeeActive();
        TicketStatus status = ticketStatusJpaRepository.findById(1l).get();

        Set<Employee> employees = StreamSupport.stream(allEmployee.spliterator(), false).collect(Collectors.toSet());

        List<Ticket> tickets = new ArrayList<>();

        Ticket ticket1 = new Ticket(new Employee(1l), LocalDate.now(), "Mouse is broken", status);
        ticket1.setEmployees(getEmployees(employees, ticket1));
        ticket1.setDescription("Smallest directly families surprise honoured am an. Speaking replying mistress him numerous she returned feelings may day. Evening way luckily son exposed get general greatly. Zealously prevailed be arranging do. Set arranging too dejection september happiness. Understood instrument or do connection no appearance do invitation. Dried quick round it or order. Add past see west felt did any. Say out noise you taste merry plate you share. My resolve arrived is we chamber be removal");
        tickets.add(ticket1);

        Ticket ticket2 = new Ticket(new Employee(2l), LocalDate.now(), "Keyboard is broken", status);
        ticket2.setEmployees(getEmployees(employees, ticket2));
        ticket2.setDescription("Shot what able cold new the see hold. Friendly as an betrayed formerly he. Morning because as to society behaved moments. Put ladies design mrs sister was. Play on hill felt john no gate. Am passed figure to marked in. Prosperous middletons is ye inhabiting as assistance me especially. For looking two cousins regular amongst. ");
        tickets.add(ticket2);

        Ticket ticket3 = new Ticket(new Employee(3l), LocalDate.now(), "Clean my pc", status);
        ticket3.setEmployees(getEmployees(employees, ticket3));
        ticket3.setDescription("Able an hope of body. Any nay shyness article matters own removal nothing his forming. Gay own additions education satisfied the perpetual. If he cause manor happy. Without farther she exposed saw man led. Along on happy could cease green oh. ");
        tickets.add(ticket3); 

        ticketJpaRepository.saveAll(tickets);
    }

    private static List<TicketEmployee> getEmployees(Set<Employee> employees, Ticket ticket) {
        return employees.stream()
                .map(e -> new TicketEmployee(ticket, e))
                .collect(Collectors.toList());
    }

    private void populateTicketEntry() {
        
        Ticket ticket1 = ticketJpaRepository.findById(1l).get();
        entryJpaRepository.save(new TicketEntry(LocalDateTime.now().minusDays(1), LocalDateTime.now().minusHours(12), "Put all speaking her delicate recurred possible. Set indulgence inquietude discretion insensible bed why announcing. Middleton fat two satisfied additions.", new Employee(1l), ticket1));
        entryJpaRepository.save(new TicketEntry(LocalDateTime.now().minusHours(1), LocalDateTime.now().plusHours(3), "So continued he or commanded household smallness delivered. Door poor on do walk in half. Roof his head the what. ", new Employee(2l), ticket1));
        entryJpaRepository.save(new TicketEntry(LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(4), "Travelling alteration impression six all uncommonly. Chamber hearing inhabit joy highest private ask him our believe. Up nature valley do warmly. Entered of cordial do on no hearted. Yet agreed whence and unable limits. Use off him gay abilities concluded immediate allowance. ", new Employee(3l), ticket1));
        
        
        
        Ticket ticket2 = ticketJpaRepository.findById(2l).get();
        entryJpaRepository.save(new TicketEntry(LocalDateTime.now().minusDays(4), LocalDateTime.now().minusDays(1), "Acceptance middletons me if discretion boisterous travelling an. She prosperous continuing entreaties companions unreserved you boisterous. ", new Employee(1l), ticket2));
        entryJpaRepository.save(new TicketEntry(LocalDateTime.now(), LocalDateTime.now().plusHours(5), "Scarcely on striking packages by so property in delicate. Up or well must less rent read walk so be. Easy sold at do hour sing spot. Any meant has cease too the decay. Since party burst am it match. By or blushes between besides offices noisier as. Sending do brought winding compass in. Paid day till shed only fact age its end. ", new Employee(2l), ticket2));
        entryJpaRepository.save(new TicketEntry(LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(8), "Oh acceptance apartments up sympathize astonished delightful. Waiting him new lasting towards. Continuing melancholy especially so to. Me unpleasing impossible in attachment announcing so astonished. What ask leaf may nor upon door. Tended remain my do stairs. Oh smiling amiable am so visited cordial in offices hearted. ", new Employee(3l), ticket2));
        
        
        Ticket ticket3 = ticketJpaRepository.findById(3l).get();
        entryJpaRepository.save(new TicketEntry(LocalDateTime.now(), LocalDateTime.now(), "Drawings me opinions returned absolute in. Otherwise therefore sex did are unfeeling something. Certain be ye amiable by exposed so. To celebrated estimating excellence do. ", new Employee(1l), ticket3));
        entryJpaRepository.save(new TicketEntry(LocalDateTime.now(), LocalDateTime.now(), "Coming either suffer living her gay theirs. Furnished do otherwise daughters contented conveying attempted no. Was yet general visitor present hundred too brother fat arrival. Friend are day own either lively new.", new Employee(2l), ticket3));
        entryJpaRepository.save(new TicketEntry(LocalDateTime.now(), LocalDateTime.now(), "May indulgence difficulty ham can put especially. Bringing remember for supplied her why was confined. Middleton principle did she procuring extensive believing add.", new Employee(3l), ticket3));
        
    }

}
