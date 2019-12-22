/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket.customcomponent;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.timepicker.TimePicker;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 *
 * @author luisrafaelinf
 */
public class CustomDateTimePicker extends CustomField<LocalDateTime> {

    private final DatePicker datePicker = new DatePicker();
    private final TimePicker timePicker = new TimePicker();

    public CustomDateTimePicker(String text) {
        setLabel(text);
        add(datePicker, timePicker);
        
        datePicker.setWidthFull();
        timePicker.setWidthFull();
        
        timePicker.setStep(Duration.ofMinutes(15));
        
        setPresentationValue(LocalDateTime.now());
    }

    @Override
    protected LocalDateTime generateModelValue() {
        final LocalDate date = datePicker.getValue();
        final LocalTime time = timePicker.getValue();
        return Objects.nonNull(date)
                && Objects.nonNull(time)
                ? LocalDateTime.of(date, time)
                : null;
    }
    
    public LocalDateTime getValue() {
        return generateModelValue();
    }

    @Override
    protected void setPresentationValue(LocalDateTime newPresentationValue) {

        datePicker.setValue(Objects.nonNull(newPresentationValue) ? newPresentationValue.toLocalDate() : null);
        timePicker.setValue(Objects.nonNull(newPresentationValue) ? newPresentationValue.toLocalTime() : null);

    }
    
    public void setValue(LocalDateTime newPresentationValue) {
        setPresentationValue(newPresentationValue);
    }

}
