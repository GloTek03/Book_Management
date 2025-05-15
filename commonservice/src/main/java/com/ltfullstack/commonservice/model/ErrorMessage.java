package com.ltfullstack.commonservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class ErrorMessage {
    private String code;
    private String message;
    private HttpStatus status;
    public ErrorMessage(String code, String message, HttpStatus status){
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
