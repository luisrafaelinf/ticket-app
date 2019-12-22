/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket.backend.exception;

/**
 *
 * @author luisrafaelinf
 */
public class TicketStatusNotFoundException extends RuntimeException {

    public TicketStatusNotFoundException(String message) {
        super(message);
    }
    
}
