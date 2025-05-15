package com.ltfullstack.employeeservice.query.projection;

import com.ltfullstack.commonservice.model.EmployeeResponseCommonModel;
import com.ltfullstack.commonservice.query.GetEmployeeDetail;
import com.ltfullstack.employeeservice.command.data.Employee;
import com.ltfullstack.employeeservice.command.data.EmployeeRepository;
import com.ltfullstack.employeeservice.query.model.EmployeeResponseModel;
import com.ltfullstack.employeeservice.query.queries.GetAllEmployeeQuery;
import com.ltfullstack.employeeservice.query.queries.GetDetailEmployeeQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeProjection {
    @Autowired
    private EmployeeRepository employeeRepository;

    @QueryHandler
    public List<EmployeeResponseModel> handle(GetAllEmployeeQuery query){
        List<Employee> listEmployee = employeeRepository.findAllByIsDisciplined(query.isDiscipline());
        return listEmployee.stream().map(employee -> {
            EmployeeResponseModel model = new EmployeeResponseModel();
            BeanUtils.copyProperties(employee, model);
            return model;
        }).toList();
    }

    @QueryHandler
    public EmployeeResponseCommonModel handle(GetEmployeeDetail query) throws Exception {
        Employee employee = employeeRepository.findById(query.getId()).orElseThrow(() -> new Exception("Employee not found"));
        EmployeeResponseCommonModel model = new EmployeeResponseCommonModel();
        BeanUtils.copyProperties(employee, model);
        return model;
    }
}
