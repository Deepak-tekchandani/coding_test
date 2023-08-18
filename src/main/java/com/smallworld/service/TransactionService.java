package com.smallworld.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smallworld.data.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
//@Value("classpath:transaction.json")
public class TransactionService {

    @Value("classpath:transactions.json")
    public double getTotalTransactionAmountSentBy(String senderFullName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Load JSON data from the file
        ClassPathResource resource = new ClassPathResource("transactions.json");
        InputStream inputStream = resource.getInputStream();

        // Map JSON data to a List<Transaction> using Jackson
        List<Transaction> transactions = objectMapper.readValue(inputStream, new TypeReference<List<Transaction>>() {});

        // Calculate sum of amounts for the specified client
        double sum = transactions.stream()
                .filter(transaction -> transaction.getSenderFullName().equals(senderFullName))
                .mapToDouble(Transaction::getAmount)
                .sum();

        return sum;
    }
}
