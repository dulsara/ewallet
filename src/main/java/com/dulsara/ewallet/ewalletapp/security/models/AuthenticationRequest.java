package com.dulsara.ewallet.ewalletapp.security.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthenticationRequest implements Serializable {


    private String username;
    private String password;
}
