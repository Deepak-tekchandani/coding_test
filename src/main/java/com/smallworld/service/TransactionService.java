package com.smallworld.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smallworld.data.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionService {


    public double getTotalTransactionAmount() throws IOException {

        // Map JSON data to a List<Transaction> using Jackson
        List<Transaction> transactions = readJsonFile();

        // Calculate sum of amounts for the specified client
        double sum = transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();

        return sum;
    }

    public double getTotalTransactionAmountSentBy(String senderFullName) throws IOException {
        List<Transaction> transactions = readJsonFile();

        // Calculate sum of amounts for the specified client
        double sum = transactions.stream()
                .filter(transaction -> transaction.getSenderFullName().equals(senderFullName))
                .mapToDouble(Transaction::getAmount)
                .sum();

        return sum;
    }

    public double getMaxTransactionAmount() throws IOException {

        List<Transaction> transactions = readJsonFile();

        // Calculate sum of amounts for the specified client
        double highestAmount = transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .max()
                .orElse(0.0);

        return highestAmount;
    }

    public long countUniqueClients() throws IOException {
        List<Transaction> transactions = readJsonFile();

        Set<String> uniqueClients = new HashSet<>();

        // Collect unique clients from sender and receiver fields
        for (Transaction transaction : transactions) {
            uniqueClients.add(transaction.getSenderFullName());
            uniqueClients.add(transaction.getBeneficiaryFullName());
        }

        return uniqueClients.size();
    }

    public boolean hasUnsolvedComplianceIssue(String clientName) throws IOException {
        List<Transaction> transactions = readJsonFile();

        // Check if the client has any transactions with unsolved compliance issues
        boolean hasUnsolvedIssue = transactions.stream()
                .anyMatch(transaction ->
                        (transaction.getSenderFullName().equals(clientName) || transaction.getBeneficiaryFullName().equals(clientName))
                                && transaction.getIssueSolved());

        return hasUnsolvedIssue;
    }
    public Map<String, List<Transaction>> getTransactionsByBeneficiaryName() throws IOException {
        List<Transaction> transactions = readJsonFile();

        // Index transactions by beneficiary name
        Map<String, List<Transaction>> transactionsByBeneficiary = new HashMap<>();
        for (Transaction transaction : transactions) {
            String beneficiary = transaction.getBeneficiaryFullName();
            transactionsByBeneficiary.computeIfAbsent(beneficiary, k -> new ArrayList<>()).add(transaction);
        }

        return transactionsByBeneficiary;
    }

    public Set<Long> getOpenComplianceIssueIds() throws IOException {
        List<Transaction> transactions = readJsonFile();

        // Retrieve identifiers of all open compliance issues
        return transactions.stream()
                .filter(transaction -> !transaction.getIssueSolved())
                .map(Transaction::getMtn)
                .collect(Collectors.toSet());
    }

    public List<String> getAllSolvedIssueMessages() throws IOException {
        List<Transaction> transactions = readJsonFile();

        // Retrieve messages of all solved issues
        return transactions.stream()
                .filter(Transaction::getIssueSolved)
                .map(Transaction::getIssueMessage)
                .collect(Collectors.toList());
    }

    public List<Transaction> getHighestAmountTransactions() throws IOException {
        List<Transaction> transactions = readJsonFile();

        // Retrieve the 3 transactions with the highest amount
        return transactions.stream()
                .sorted(Comparator.comparingDouble(Transaction::getAmount).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    public String getSenderWithMostTotalSentAmount() throws IOException {
        // Load JSON data from the file
        List<Transaction> transactions = readJsonFile();

        // Calculate the sender with the most total sent amount
        Map<String, Double> senderTotalAmounts = new HashMap<>();
        for (Transaction transaction : transactions) {
            senderTotalAmounts.merge(
                    transaction.getSenderFullName(),
                    transaction.getAmount(),
                    Double::sum
            );
        }

        return senderTotalAmounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    @Value("classpath:transactions.json")
    private List<Transaction> readJsonFile() throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();

        // Load JSON data from the file
        ClassPathResource resource = new ClassPathResource("transactions.json");
        InputStream inputStream = resource.getInputStream();

        // Map JSON data to a List<Transaction> using Jackson
        return objectMapper.readValue(inputStream, new TypeReference<List<Transaction>>() {});

    }
}
