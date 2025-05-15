package com.ltfullstack.commonservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeResponseCommonModel {
    private String id;
    private String firstName;
    private String lastName;
    private String kin;
    private boolean isDisciplined;
}
