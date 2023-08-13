package com.smallworld;

import com.smallworld.data.Transaction;
import com.smallworld.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class TransactionDataFetcher {

    @Autowired
    TransactionRepo transactionRepo;
    /**
     * Returns the sum of the amounts of all transactions
     */

    @PostMapping("create")
    public Transaction create(Transaction transaction) {
        System.out.println("================");
        return transactionRepo.save(transaction);

    }


    @GetMapping("/totalAmount")
    public Double getTotalTransactionAmount() {
        Double amount =  transactionRepo.getTotalTransactionAmount();
        if(amount >= 0 ) {
            return amount;
        }
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the sum of the amounts of all transactions sent by the specified client
     */
    @GetMapping("/bySenderName")
    public double getTotalTransactionAmountSentBy(String senderFullName) {
        Double amount = transactionRepo.getTotalTransactionAmountSentBy(senderFullName);
        if (amount >0){
            return amount;
        }
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the highest transaction amount
     */
    @GetMapping("/maxTransactionAmount")
    public double getMaxTransactionAmount() {
        Double amount =  transactionRepo.getMaxTransactionAmount();
        if(amount > 0){
            return amount;
        }
        throw new UnsupportedOperationException();
    }

    /**
     * Counts the number of unique clients that sent or received a transaction
     */
    @GetMapping("/uniqueClient")
    public long countUniqueClients() {
        Long count = transactionRepo.countUniqueClients();
        if (count > 0){
            return count;
        }

        throw new UnsupportedOperationException();
    }

    /**
     * Returns whether a client (sender or beneficiary) has at least one transaction with a compliance
     * issue that has not been solved
     */
    @GetMapping("/openIssue")
    public boolean hasOpenComplianceIssues(String clientFullName) {

        return transactionRepo.existsBySenderFullNameAndComplianceIssueSolvedFalse(clientFullName) ||
                transactionRepo.existsByBeneficiaryFullNameAndComplianceIssueSolvedFalse(clientFullName);
//        throw new UnsupportedOperationException();
    }

    /**
     * Returns all transactions indexed by beneficiary name
     */
    @GetMapping("/byBeneficiaryName")
    public Map<String, List<Transaction>> getTransactionsByBeneficiaryName() {
        List<Object[]> results = transactionRepo.getTransactionsByBeneficiaryName();
        Map<String, List<Transaction>> transactionsByBeneficiary = new HashMap<>();

        for (Object[] result : results) {
            String beneficiaryName = (String) result[0];
            Transaction transaction = (Transaction) result[1];

            transactionsByBeneficiary
                    .computeIfAbsent(beneficiaryName, k -> new ArrayList<>())
                    .add(transaction);
        }
        return transactionsByBeneficiary;

//        throw new UnsupportedOperationException();
    }

    /**
     * Returns the identifiers of all open compliance issues
     */
    @GetMapping("/unsolvedIssueIds")
    public Set<Integer> getUnsolvedIssueIds() {
        return transactionRepo.getUnsolvedIssueIds();
//        throw new UnsupportedOperationException();
    }

    /**
     * Returns a list of all solved issue messages
     */
    @GetMapping("/issueMessage")
    public List<String> getAllSolvedIssueMessages() {
        return  transactionRepo.getAllSolvedIssueMessages();
//        throw new UnsupportedOperationException();
    }

    /**
     * Returns the 3 transactions with the highest amount sorted by amount descending
     */
    @GetMapping("/top3Transactions")
    public List<Transaction> getTop3TransactionsByAmount() {
        return transactionRepo.findTop3ByOrderByAmountDesc();
//        throw new UnsupportedOperationException();
    }

    /**
     * Returns the senderFullName of the sender with the most total sent amount
     */
    @GetMapping("/topSender")
    public List<String> getTopSender() {
        return transactionRepo.findTopSenderByTotalSentAmount();

//        throw new UnsupportedOperationException();
    }

}
