package ru.shishov.onlinelibrary.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shishov.onlinelibrary.models.Employee;
import ru.shishov.onlinelibrary.services.EmployeeService;

@Component
public class EmployeeValidator implements Validator {
    private final EmployeeService employeeService;
    @Autowired
    public EmployeeValidator(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Employee.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Employee employee = (Employee) target;
        if(employeeService.findByUserName(employee.getUsername()).isPresent())
            errors.rejectValue("username", "", "Person with this name already exists");

    }
}
