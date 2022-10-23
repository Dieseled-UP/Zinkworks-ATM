package com.bourne.zinkworksatm.controllers;

import com.bourne.zinkworksatm.exceptions.BadPinException;
import com.bourne.zinkworksatm.exceptions.BalanceException;
import com.bourne.zinkworksatm.exceptions.InvalidInputException;
import com.bourne.zinkworksatm.exceptions.NotesException;
import com.bourne.zinkworksatm.models.ApiResponse;
import com.bourne.zinkworksatm.services.AtmService;
import com.bourne.zinkworksatm.services.impl.AtmServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AtmService atmService;

    public AccountController(AtmServiceImpl atmService) {
        this.atmService = atmService;
    }

    @GetMapping
    @RequestMapping("balance/{accountNumber}/{pinNumber}")
    public ResponseEntity<ApiResponse<Serializable>> getAccountBalance(@PathVariable Long accountNumber, @PathVariable int pinNumber) {

        try {
            BigDecimal balance = atmService.checkBalance(accountNumber, pinNumber);
            return ResponseEntity.ok(
                   new ApiResponse<>(
                           balance,
                           true
                   )
            );
        } catch (BadPinException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new ApiResponse<>(
                            e.getMessage(),
                            false
                    )
            );
        }
    }

    @GetMapping
    @RequestMapping("withdrawal/{accountNumber}/{pinNumber}/{amount}")
    public ResponseEntity<ApiResponse<Serializable>> makeWithdrawal(@PathVariable Long accountNumber, @PathVariable int pinNumber, @PathVariable int amount) {
        try {
            HashMap<String, Object> transactionDetails = atmService.makeWithdrawal(accountNumber, pinNumber, amount);
            return ResponseEntity.ok(
                    new ApiResponse<>(
                            transactionDetails,
                            true
                    )
            );
        } catch (BadPinException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new ApiResponse<>(
                            e.getMessage(),
                            false
                    )
            );
        } catch (InvalidInputException | NotesException | BalanceException e) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(
                            e.getMessage(),
                            false
                    )
            );
        }
    }
}
