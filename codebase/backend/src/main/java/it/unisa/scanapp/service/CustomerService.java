package it.unisa.scanapp.service;

import it.unisa.scanapp.domain.Customer;
import it.unisa.scanapp.domain.User;
import it.unisa.scanapp.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

import static it.unisa.scanapp.security.SecurityUtils.getCurrentUserLogin;

@Service
@Transactional
public class CustomerService {

    private final Logger log = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;
    private final UserService userService;

    public CustomerService(CustomerRepository customerRepository, UserService userService) {
        this.customerRepository = customerRepository;
        this.userService = userService;
    }


    public Optional<Customer> getCustomerFromCurrentUser(){

        Optional<User> currentUser =  userService.getUserWithAuthorities();
        if(!currentUser.isPresent()){
            return Optional.empty();
        }

        Optional<Customer> currentCustomer = customerRepository.findByUser(currentUser.get());
        return currentCustomer;
    }
}
