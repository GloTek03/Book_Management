package com.ltfullstack.borrowingservice.command.controller;

import com.ltfullstack.borrowingservice.command.command.CreateBorrowingCommand;
import com.ltfullstack.borrowingservice.command.model.BorrowingCreatedModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/borrowing")
public class BorrowingCommandController {
    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public String createBorrowing(@RequestBody BorrowingCreatedModel createdModel){
        CreateBorrowingCommand command = new CreateBorrowingCommand(UUID.randomUUID().toString()
                , createdModel.getBookId()
                , createdModel.getEmployeeId()
                , new Date());
        return commandGateway.sendAndWait(command);
    }
}
