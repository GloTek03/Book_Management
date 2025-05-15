package com.ltfullstack.bookservice.command.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookUpdateEvent {
    private String id;
    private String name;
    private String author;
    private Boolean isReady;
}
