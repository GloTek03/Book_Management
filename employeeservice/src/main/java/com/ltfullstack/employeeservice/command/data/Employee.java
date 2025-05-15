package com.ltfullstack.employeeservice.command.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="employees")
public class Employee {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String kin;
    private boolean isDisciplined;
}
