package com.smallworld.repo;


import com.smallworld.data.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {

    //Returns the sum of the amounts of all transactions
   // @Query("SELECT SUM(t.amount) FROM Transaction t")
   // Double getTotalTransactionAmount();

    //Returns the sum of the amounts of all transactions sent by the specified client
  //  @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.senderFullName = :senderFullName")
   // Double getTotalTransactionAmountSentBy(@Param("senderFullName") String senderFullName);

    //Returns the highest transaction amount
   // @Query("SELECT MAX(t.amount) FROM Transaction t")
    //Double getMaxTransactionAmount();

    //Counts the number of unique clients that sent or received a transaction
    //@Query("SELECT COUNT(DISTINCT t.senderFullName) + COUNT(DISTINCT t.beneficiaryFullName) FROM Transaction t")
   // Long countUniqueClients();

    /**
     * Returns whether a client (sender or beneficiary) has at least one transaction with a compliance
     * issue that has not been solved
     */
    //boolean existsBySenderFullNameAndComplianceIssueSolvedFalse(String senderFullName);

   // boolean existsByBeneficiaryFullNameAndComplianceIssueSolvedFalse(String beneficiaryFullName);


    //Returns all transactions indexed by beneficiary name
   // @Query("SELECT t.beneficiaryFullName, t FROM Transaction t")
   // List<Object[]> getTransactionsByBeneficiaryName();

    //Returns the identifiers of all open compliance issues
   // @Query("SELECT t.id FROM Transaction t WHERE t.complianceIssueSolved = false")
   // Set<Integer> getUnsolvedIssueIds();

    //Returns a list of all solved issue messages
    //@Query("SELECT t.issueMessage FROM Transaction t WHERE t.complianceIssueSolved = true")
    //List<String> getAllSolvedIssueMessages();

    // Returns the 3 transactions with the highest amount sorted by amount descending
    //@Query("SELECT t FROM Transaction t ORDER BY t.amount DESC LIMIT 3")
   // List<Transaction> findTop3ByOrderByAmountDesc();

    //Returns the senderFullName of the sender with the most total sent amount

    //@Query("SELECT t.senderFullName FROM Transaction t GROUP BY t.senderFullName ORDER BY SUM(t.amount) DESC")
   // List<String> findTopSenderByTotalSentAmount();



//    List<Transaction> findBySenderFullName(String senderFullName);
}
