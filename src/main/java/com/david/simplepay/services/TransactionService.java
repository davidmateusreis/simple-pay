package com.david.simplepay.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.david.simplepay.entities.Transaction;
import com.david.simplepay.entities.User;
import com.david.simplepay.dtos.TransactionDTO;
import com.david.simplepay.repositories.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;

    public Transaction createTransaction(TransactionDTO transactionDTO) throws Exception {

        User sender = userService.findUserById(transactionDTO.sender());
        User receiver = userService.findUserById(transactionDTO.receiver());

        userService.validateTransaction(sender, transactionDTO.value());

        boolean isAuhorized = authorizeTransaction(sender, transactionDTO.value());

        if (!isAuhorized) {
            throw new Exception("Unauthorized transaction!");
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transactionDTO.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setCreatedAt(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transactionDTO.value()));
        receiver.setBalance(receiver.getBalance().add(transactionDTO.value()));

        transactionRepository.save(newTransaction);
        userService.saveUser(sender);
        userService.saveUser(receiver);

        notificationService.sendNotification(sender, "Transaction successful!");
        notificationService.sendNotification(receiver, "Transaction successful!");

        return newTransaction;
    }

    public boolean authorizeTransaction(User sender, BigDecimal value) {

        ResponseEntity<Map<String, Object>> authorizationResponse = restTemplate
                .exchange("https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6", HttpMethod.GET, null,
                        new ParameterizedTypeReference<Map<String, Object>>() {
                        });

        if (authorizationResponse.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> body = authorizationResponse.getBody();
            if (body != null && body.containsKey("message")) {
                String message = (String) body.get("message");
                return "Autorizado".equalsIgnoreCase(message);
            }
        }

        return false;
    }
}
