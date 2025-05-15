package com.ltfullstack.borrowingservice.command.saga;

import com.ltfullstack.borrowingservice.command.command.DeleteBorrowingCommand;
import com.ltfullstack.borrowingservice.command.event.BorrowingCreateEvent;
import com.ltfullstack.commonservice.command.command.UpdateBookStatusCommand;
import com.ltfullstack.commonservice.command.event.BookUpdateStatusEvent;
import com.ltfullstack.commonservice.model.BookResponseCommonModel;
import com.ltfullstack.commonservice.model.EmployeeResponseCommonModel;
import com.ltfullstack.commonservice.query.GetBookDetail;
import com.ltfullstack.commonservice.query.GetEmployeeDetail;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
@Slf4j
public class BorrowingSaga {
    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient QueryGateway queryGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    public void handle(BorrowingCreateEvent event){
        log.info("BorrowingCreateEvent in saga for Bookid: {}, EmployeeId: {}",event.getBookId(),event.getEmployeeId());
        try{
            GetBookDetail getBookDetail = new GetBookDetail(event.getBookId());
            BookResponseCommonModel bookResponseCommonModel = queryGateway
                    .query(getBookDetail,
                            ResponseTypes.instanceOf(BookResponseCommonModel.class)).join();
            if(!bookResponseCommonModel.getIsReady()){
                throw new Exception("Borrowed Book");
            }else {
                SagaLifecycle.associateWith("bookId", event.getBookId());
                updateBookStatus(event.getBookId(),event.getEmployeeId(),event.getId(),false);
            }
        }catch (Exception e){
            rollbackBorrowingRecord(event.getId());
            log.error(e.getMessage());
        }
    }

    @SagaEventHandler(associationProperty = "bookId")
    private void handler(BookUpdateStatusEvent event) throws Exception {
        log.info("Update Book Status in Saga for BookId: {}", event.getBookId());
        GetEmployeeDetail query = new GetEmployeeDetail(event.getEmployeeId());
        try{
            EmployeeResponseCommonModel employeeResponseCommonModel = queryGateway
                    .query(query,
                            ResponseTypes.instanceOf(EmployeeResponseCommonModel.class)).join();
            if(employeeResponseCommonModel.isDisciplined()){
                log.info("RollbackBookStatus Record in Saga for Book id: {}", event.getBookId());
                updateBookStatus(event.getBookId(),event.getEmployeeId(), event.getBorrowingId(), true);
                log.info("RollbackBorrowing Record in Saga for Book id: {}", event.getBookId());
                rollbackBorrowingRecord(event.getBorrowingId());
                throw new Exception("Nhan vien bi ki luat");
            }else {
                log.info("Successful borrowing in Saga for BookId: {} and EmployeeId: {}"
                        , event.getBookId(), event.getEmployeeId());
                SagaLifecycle.end();
            }
        }catch (Exception e){
            log.error("Exception in BookUpdateStatusEvent handler: {}", e.getMessage(), e);
            // Only rollback if saga not already ended
            try {
                updateBookStatus(event.getBookId(), event.getEmployeeId(), event.getBorrowingId(), true);
                rollbackBorrowingRecord(event.getBorrowingId());
            } catch (Exception inner) {
                log.error("Rollback failed: {}", inner.getMessage(), inner);
            }
        }
    }

    private void rollbackBorrowingRecord(String id){
        DeleteBorrowingCommand command = new DeleteBorrowingCommand(id);
        commandGateway.sendAndWait(command);
        SagaLifecycle.end();
    }

    private void updateBookStatus(String bookId,String employeeId,String borrowingId, boolean status){
        UpdateBookStatusCommand command = new UpdateBookStatusCommand(bookId,
                status,
                employeeId,
                borrowingId);
        commandGateway.sendAndWait(command);
    }
}
