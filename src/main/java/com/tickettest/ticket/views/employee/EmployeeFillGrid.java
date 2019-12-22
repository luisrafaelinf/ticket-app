/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket.views.employee;

import com.tickettest.ticket.backend.model.Employee;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.server.Command;
import java.util.List;

/**
 *
 * @author luisrafaelinf
 */
public final class EmployeeFillGrid implements Command {

    private List<Employee> employees;
    private EmployeeGrid employeeGrid;
    
    public final static EmployeeFillGrid of(List<Employee> employees, EmployeeGrid reportGrid) {
        return new EmployeeFillGrid(employees, reportGrid);
    }
    
    private EmployeeFillGrid(List<Employee> status, EmployeeGrid statusGrid) {
        this.employees = status;
        this.employeeGrid = statusGrid;

    }

    @Override
    public void execute() {
        
        employeeGrid.setItems(this.employees);
        
        final Grid.Column<Employee> ticketColumn = employeeGrid.getColumnByKey(EmployeeGrid.USER_NAME);
        ticketColumn.setFooter(String.format("Total: %d employees", this.employees.size()));

    }

}
