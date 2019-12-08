package it.unisa.scanapp.service;

import it.unisa.scanapp.domain.Product;
import it.unisa.scanapp.domain.Transaction;
import it.unisa.scanapp.domain.User;
import it.unisa.scanapp.repository.ProductRepository;
import it.unisa.scanapp.repository.TransactionRepository;
import it.unisa.scanapp.repository.UserRepository;
import it.unisa.scanapp.security.SecurityUtils;
import it.unisa.scanapp.service.dto.TransactionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class TransactionService {

    private final Logger log = LoggerFactory.getLogger(TransactionService.class);

    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Optional<Transaction> registerTransaction(TransactionDTO transactionDTO){

        Transaction result = new Transaction();
        Set<Product> transactionProducts = new HashSet<>();
        result.setDate(LocalDate.now());

        if(!SecurityUtils.getCurrentUserLogin().isPresent()){
            return Optional.empty();
        }

        String userLogin = SecurityUtils.getCurrentUserLogin().get();
        Optional<User> userOptional = userRepository.findOneByLogin(userLogin);

        if(!userOptional.isPresent()){
            return Optional.empty();
        }

        User transactionOwner = userOptional.get();
        result.setUser(transactionOwner);

        transactionDTO.getProductsId().forEach(productId ->{
            transactionProducts.add(productRepository.getOne(productId));
        });

        result.setProducts(transactionProducts);
        result = transactionRepository.save(result);

        return Optional.of(result);
    }

}
