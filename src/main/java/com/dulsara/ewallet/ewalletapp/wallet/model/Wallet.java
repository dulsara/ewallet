package com.dulsara.ewallet.ewalletapp.wallet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "wallet")
@Data
public class Wallet {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;
    String name;
    Double balance;

}
