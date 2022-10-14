package com.bourne.zinkworksatm.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.HashMap;

@Entity
public class Atm {

    @Id
    @GeneratedValue
    private Long id;
    private BigDecimal remainingCashTotal;
    private int numOfFifties;
    private int numOfTwenties;
    private int numOfTens;
    private int numOfFives;

    public Atm() {
        remainingCashTotal = BigDecimal.valueOf(1500);
        setNumOfFifties(10);
        setNumOfTwenties(30);
        setNumOfTens(30);
        setNumOfFives(20);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getRemainingCashTotal() {
        return remainingCashTotal;
    }

    public void setRemainingCashTotal(BigDecimal remainingCashTotal) {
        this.remainingCashTotal = remainingCashTotal;
    }

    public int getNumOfFifties() {
        return numOfFifties;
    }

    public void setNumOfFifties(int numOfFifties) {
        this.numOfFifties = numOfFifties;
    }

    public int getNumOfTwenties() {
        return numOfTwenties;
    }

    public void setNumOfTwenties(int numOfTwenties) {
        this.numOfTwenties = numOfTwenties;
    }

    public int getNumOfTens() {
        return numOfTens;
    }

    public void setNumOfTens(int numOfTens) {
        this.numOfTens = numOfTens;
    }

    public int getNumOfFives() {
        return numOfFives;
    }

    public void setNumOfFives(int numOfFives) {
        this.numOfFives = numOfFives;
    }
}
