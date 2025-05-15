package com.ltfullstack.commonservice.command.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRollbackStatusEvent {
    private String bookId;
    private Boolean isReady;
    private String employeeId;
    private String borrowingId;
}
