package com.ltfullstack.bookservice.command.event;

import com.ltfullstack.bookservice.command.data.Book;
import com.ltfullstack.bookservice.command.data.BookRepository;
import com.ltfullstack.commonservice.command.event.BookUpdateStatusEvent;
import io.grpc.netty.shaded.io.netty.handler.codec.MessageAggregationException;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
@Slf4j
public class BookEventHandler {
    @Autowired
    private BookRepository bookRepository;
    @EventHandler
    public void on(BookCreateEvent event){
        Book book = new Book();
        BeanUtils.copyProperties(event, book);
        try {
            bookRepository.save(book);
        }catch (MessageAggregationException e){
            log.error(e.toString());
        }

    }

    @EventHandler
    public void on(BookUpdateEvent event){
        Optional<Book> oldBook = bookRepository.findById(event.getId());
        oldBook.ifPresent(book -> {
            book.setName(event.getName());
            book.setAuthor(event.getAuthor());
            book.setIsReady(event.getIsReady());
            bookRepository.save(book);
        });
    }

    @EventHandler
    public void on(BookDeleteEvent event){
        Optional<Book> oldBook = bookRepository.findById(event.getId());
        oldBook.ifPresent(book -> bookRepository.delete(book));
    }

    @EventHandler
    public void on(BookUpdateStatusEvent event){
        Optional<Book> oldBook = bookRepository.findById(event.getBookId());
        oldBook.ifPresent(book -> {
            book.setIsReady(event.getIsReady());
            bookRepository.save(book);
        });
    }
}
