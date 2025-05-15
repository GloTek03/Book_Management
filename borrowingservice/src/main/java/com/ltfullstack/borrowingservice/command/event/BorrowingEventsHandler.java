package com.ltfullstack.borrowingservice.command.event;

import com.ltfullstack.borrowingservice.command.data.Borrowing;
import com.ltfullstack.borrowingservice.command.data.BorrowingRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BorrowingEventsHandler {
    @Autowired
    private BorrowingRepository repository;

    @EventHandler
    public void on(BorrowingCreateEvent event){
        Borrowing model = new Borrowing();
        model.setId(event.getId());
        model.setBookId(event.getBookId());
        model.setEmployeeId(event.getEmployeeId());
        model.setBorrowingDate(event.getBorrowingDate());
        model.setReturnDate(event.getReturnDate());
        repository.save(model);
    }

    @EventHandler
    public void on(BorrowingDeleteEvent event){
        Optional<Borrowing> oldEntity = repository.findById(event.getId());
        oldEntity.ifPresent(borrowing -> {repository.delete(borrowing);});
    }
}
