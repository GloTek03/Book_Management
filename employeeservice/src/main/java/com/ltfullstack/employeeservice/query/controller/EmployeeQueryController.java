package com.ltfullstack.employeeservice.query.controller;

import com.fasterxml.jackson.databind.deser.SettableAnyProperty;
import com.ltfullstack.commonservice.model.EmployeeResponseCommonModel;
import com.ltfullstack.commonservice.query.GetEmployeeDetail;
import com.ltfullstack.employeeservice.command.data.Employee;
import com.ltfullstack.employeeservice.query.model.EmployeeResponseModel;
import com.ltfullstack.employeeservice.query.queries.GetAllEmployeeQuery;
import com.ltfullstack.employeeservice.query.queries.GetDetailEmployeeQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@Slf4j
@Tag(name = "Employee Query")
public class EmployeeQueryController {
    @Autowired
    private QueryGateway queryGateway;

    @Autowired
    private Environment env;

    @Operation(
            summary = "Get List Employee",
            description = "Get endpoint for employee with filter",
            responses = {
                    @ApiResponse(
                            description = "success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid token",
                            responseCode = "401"
                    )
            }
    )
    @GetMapping
    public List<EmployeeResponseModel> getAllEmployee(@RequestParam(required = false, defaultValue = "false") Boolean isDisciplined){
        String port = env.getProperty("local.server.port");
        log.info("Running service get Employee:" + port);
       return queryGateway.query(new GetAllEmployeeQuery(isDisciplined), ResponseTypes.multipleInstancesOf(EmployeeResponseModel.class)).join();
    }

    @GetMapping("/{employeeId}")
    public EmployeeResponseCommonModel getDetailEmployee(@PathVariable String employeeId){
        GetEmployeeDetail query = new GetEmployeeDetail(employeeId);
        return queryGateway.query(query, ResponseTypes.instanceOf(EmployeeResponseCommonModel.class)).join();
    }
}
