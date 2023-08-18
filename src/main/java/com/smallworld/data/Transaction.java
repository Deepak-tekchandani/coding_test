package com.smallworld.data;

import lombok.*;


@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

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
