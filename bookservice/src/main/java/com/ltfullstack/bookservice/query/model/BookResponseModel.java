package com.ltfullstack.bookservice.query.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseModel {
    private String id;
    private String name;
    private String author;
    private Boolean isReady;
}
