package ru.shishov.onlinelibrary.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shishov.onlinelibrary.models.Employee;
import ru.shishov.onlinelibrary.repositories.EmployeeRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class EmployeeService {
    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;
    @Autowired
    public EmployeeService(PasswordEncoder passwordEncoder, EmployeeRepository employeeRepository) {
        this.passwordEncoder = passwordEncoder;
        this.employeeRepository = employeeRepository;
    }

    public Optional<Employee> findByUserName(String username) {
        return employeeRepository.findByUsername(username);
    }
    @Transactional
    public void save(Employee employee) {
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employee.setRole("ROLE_USER");
        employeeRepository.save(employee);
    }
}
