package com.ltfullstack.employeeservice.command.event;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeCreatedEvent {
    private String id;
    private String firstName;
    private String lastName;
    private String kin;
    private Boolean isDisciplined;
}
