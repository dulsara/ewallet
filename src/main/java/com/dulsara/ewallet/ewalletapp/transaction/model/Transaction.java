package com.dulsara.ewallet.ewalletapp.transaction.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;
import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction")
public class Transaction {

    @Id 
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;
    Double amount;
    String type;
    String transferType;
    Long walletId;
    Long referenceId;
    String description;
    Instant transactionDate;

}
