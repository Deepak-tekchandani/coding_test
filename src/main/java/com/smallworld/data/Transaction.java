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
    private Long mtn;
    private Double amount;

    private String senderFullName;
    private Integer senderAge;
    private String beneficiaryFullName;

    private Integer beneficiaryAge;

    private Integer issueId;

    private Boolean issueSolved;
    private String issueMessage;



}
