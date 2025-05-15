package com.ltfullstack.bookservice.query.controller;

import com.ltfullstack.bookservice.query.model.BookResponseModel;
import com.ltfullstack.bookservice.query.queries.GetAllBookQuery;
import com.ltfullstack.commonservice.model.BookResponseCommonModel;
import com.ltfullstack.commonservice.query.GetBookDetail;
import com.ltfullstack.commonservice.service.KafkaService;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookQueryController {
    @Autowired
    private QueryGateway queryGateway;

    @Autowired
    private KafkaService kafkaService;

    @GetMapping
    public List<BookResponseModel> getAllBooks(){
        GetAllBookQuery query = new GetAllBookQuery();
        return queryGateway.query(query, ResponseTypes.multipleInstancesOf(BookResponseModel.class)).join();
    }

    @GetMapping("/{bookId}")
    public BookResponseCommonModel getBook(@PathVariable String bookId){
        GetBookDetail query = new GetBookDetail(bookId);
        return queryGateway.query(query,ResponseTypes.instanceOf(BookResponseCommonModel.class)).join();
    }

    @PostMapping("/sendMessage")
    public void sendMessage(@RequestBody String message){
        kafkaService.sendMessage("emailTemplate", message);
    }
}
