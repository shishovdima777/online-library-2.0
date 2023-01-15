package ru.shishov.onlinelibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.shishov.onlinelibrary.models.Employee;
import ru.shishov.onlinelibrary.services.EmployeeService;
import ru.shishov.onlinelibrary.util.EmployeeValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final EmployeeValidator employeeValidator;
    private final EmployeeService employeeService;
    @Autowired
    public AuthController(EmployeeValidator employeeValidator, EmployeeService employeeService) {
        this.employeeValidator = employeeValidator;
        this.employeeService = employeeService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "auth/login";
    }
    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("employee")Employee employee) {
        return "auth/registration";
    }
    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("employee")@Valid Employee employee,
                                      BindingResult bindingResult) {
        System.out.println(employee);

        employeeValidator.validate(employee, bindingResult);

        if(bindingResult.hasErrors())
            return "auth/registration";

        employeeService.save(employee);

        return "redirect:/auth/login";

    }
}
