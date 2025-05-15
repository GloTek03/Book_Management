package com.ltfullstack.employeeservice.command.command;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@Getter
@Setter
public class CreateEmployeeCommand {
    @TargetAggregateIdentifier
    private String id;
    private String firstName;
    private String lastName;
    private String kin;
    private Boolean isDisciplined;

    public CreateEmployeeCommand(String id, String firstName, String lastName, String kin, Boolean isDisciplined) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.kin = kin;
        this.isDisciplined = isDisciplined;
    }
}
