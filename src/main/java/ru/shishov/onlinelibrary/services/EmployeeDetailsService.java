package ru.shishov.onlinelibrary.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.shishov.onlinelibrary.models.Employee;
import ru.shishov.onlinelibrary.repositories.EmployeeRepository;
import ru.shishov.onlinelibrary.security.EmployeeDetails;

import java.util.Optional;

@Service
public class EmployeeDetailsService implements UserDetailsService {
    private final EmployeeRepository employeeRepository;
    @Autowired
    public EmployeeDetailsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee> employee = employeeRepository.findByUsername(username);

        if(employee.isPresent())
            return new EmployeeDetails(employee.get());
        throw new UsernameNotFoundException("Employee with not found!");

    }
}
