package com.ltfullstack.employeeservice.command.event;

import com.ltfullstack.employeeservice.command.data.Employee;
import com.ltfullstack.employeeservice.command.data.EmployeeRepository;
import jakarta.ws.rs.NotFoundException;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.print.Book;
import java.util.Optional;

@Component
public class EmployeeEventHandler {
    @Autowired
    private EmployeeRepository repository;

    @EventHandler
    public void on(EmployeeCreatedEvent event){
        Employee employee = new Employee();
        BeanUtils.copyProperties(event,employee);
        repository.save(employee);
    }

    @EventHandler
    public void on(EmployeeUpdateEvent event){
        Employee oldEmp = repository.findById(event.getId()).orElseThrow(() -> new NotFoundException("Employee Not Found"));
        oldEmp.setFirstName(event.getFirstName());
        oldEmp.setLastName(event.getLastName());
        oldEmp.setKin(event.getKin());
        oldEmp.setDisciplined(event.getIsDisciplined());
        repository.save(oldEmp);
    }

    @EventHandler
    public void on(EmployeeDeleteEvent event){
        Employee oldEmp = repository.findById(event.getEmployeeId()).orElseThrow(() -> new NotFoundException("Employee Not Found"));
        repository.delete(oldEmp);
    }
}
