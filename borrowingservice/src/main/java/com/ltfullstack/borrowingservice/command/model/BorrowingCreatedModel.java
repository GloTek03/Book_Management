package com.ltfullstack.borrowingservice.command.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BorrowingCreatedModel {
    private String bookId;
    private String employeeId;
}
