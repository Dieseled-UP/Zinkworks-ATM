package com.bourne.zinkworksatm.services;

import com.bourne.zinkworksatm.exceptions.BadPinException;
import com.bourne.zinkworksatm.exceptions.BalanceException;
import com.bourne.zinkworksatm.exceptions.InvalidInputException;
import com.bourne.zinkworksatm.exceptions.NotesException;

import java.math.BigDecimal;
import java.util.HashMap;

public interface AtmService {

    BigDecimal checkBalance(Long accountNumber, int pinNumber) throws BadPinException;

    HashMap<String, Object> makeWithdrawal(Long accountNumber, int pinNumber, int amount) throws BadPinException, InvalidInputException, NotesException, BalanceException;
}
