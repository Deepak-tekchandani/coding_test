package com.smallworld;

import com.smallworld.data.Transaction;
import com.smallworld.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

@RestController
public class TransactionDataFetcher {

    @Autowired
    TransactionService transactionService;
    /**
     * Returns the sum of the amounts of all transactions
     */



    @GetMapping("/totalAmount")
    public Double getTotalTransactionAmount() throws IOException {
        return   transactionService.getTotalTransactionAmount();

    }

    /**
     * Returns the sum of the amounts of all transactions sent by the specified client
     */
    @GetMapping("/bySenderName")
    public double getTotalTransactionAmountSentBy(String senderFullName)throws IOException {
       return transactionService.getTotalTransactionAmountSentBy(senderFullName);
//
    }

    /**
     * Returns the highest transaction amount
     */
    @GetMapping("/maxTransactionAmount")
    public double getMaxTransactionAmount()throws IOException {
        return   transactionService.getMaxTransactionAmount();
    }

    /**
     * Counts the number of unique clients that sent or received a transaction
     */
    @GetMapping("/uniqueClient")
    public long countUniqueClients() throws IOException{
        return transactionService.countUniqueClients();
    }

    /**
     * Returns whether a client (sender or beneficiary) has at least one transaction with a compliance
     * issue that has not been solved
     */
    @GetMapping("/openIssue")
    public boolean hasOpenComplianceIssues(String clientFullName)throws IOException {
        return transactionService.hasUnsolvedComplianceIssue(clientFullName);
    }

    /**
     * Returns all transactions indexed by beneficiary name
     */
    @GetMapping("/byBeneficiaryName")
    public Map<String, List<Transaction>> getTransactionsByBeneficiaryName() throws IOException {
       return transactionService.getTransactionsByBeneficiaryName();

    }

    /**
     * Returns the identifiers of all open compliance issues
     */
    @GetMapping("/unsolvedIssueIds")
    public Set<Long> getUnsolvedIssueIds()throws IOException {
        return transactionService.getOpenComplianceIssueIds();
    }

    /**
     * Returns a list of all solved issue messages
     */
    @GetMapping("/issueMessage")
    public List<String> getAllSolvedIssueMessages() throws IOException {
        List<String> solvedIssued = new ArrayList<>();
        List<String> trasactionList =  transactionService.getAllSolvedIssueMessages();
        for(String str : trasactionList){
            if(str != null){
                solvedIssued.add(str);
            }
        }
        return solvedIssued;
    }

    /**
     * Returns the 3 transactions with the highest amount sorted by amount descending
     */
    @GetMapping("/top3Transactions")
    public List<Transaction> getTop3TransactionsByAmount()throws IOException {
        return transactionService.getHighestAmountTransactions();
    }

    /**
     * Returns the senderFullName of the sender with the most total sent amount
     */
    @GetMapping("/topSender")
    public String getTopSender()throws IOException {
        return transactionService.getSenderWithMostTotalSentAmount();

    }

}
