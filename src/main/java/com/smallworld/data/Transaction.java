package com.smallworld.data;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String senderFullName;
    private String beneficiaryFullName;
    private Double amount;
    private Boolean complianceIssueSolved;
    private String issueMessage;
}
