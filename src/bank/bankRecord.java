/*
 * Sarah Overlander
 */
package bank;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;

/**
 *
 * @author sarah
 */
public class bankRecord implements Cloneable {

    private String firstName;
    private String lastName;
    private BigDecimal balance;

    //Constructors
    public bankRecord(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = new BigDecimal("0.00");
    }

    public bankRecord(String firstName, String lastName, String balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = new BigDecimal(balance);
    }

    public bankRecord clone() throws CloneNotSupportedException {
        return (bankRecord) super.clone();
    }

    //Getters
    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getBalance() {
        return NumberFormat.getCurrencyInstance(Locale.US).format(this.balance);
    }

    //Setters
    public void modifyFirstName(String newFirstName) {
        this.firstName = newFirstName;
    }

    public void modifyLastName(String newLastName) {
        this.lastName = newLastName;
    }

    public void makeDeposit(String depositAmount) {
        this.balance = this.balance.add(new BigDecimal(depositAmount));
    }

    public void makeWithdrawal(String withdrawalAmount) {
        this.balance = this.balance.subtract(new BigDecimal(withdrawalAmount));
    }
}
