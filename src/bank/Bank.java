/*
 * Sarah Overlander
 * CSC 3410 Spring 2016
 * Assignment #4
 * 
 */
package bank;

import java.util.*;

/**
 * Purpose of program: To create a console interface for storing names and bank
 * balances of multiple people, provide a way to correct name entries, and to
 * perform account deposits and withdrawals.
 *
 * Algorithms used:
 *
 * Switch statements are used to select which function the user desires, as well
 * as deciding which of the Current Record headers to use.
 *
 * For loops used to iterate over the ledger list for printing to output and
 * sorting in new/updated records.
 *
 * While loop used to keep program running until user exits the program
 * deliberately.
 *
 * While loops used to allow limited retries for non-numeric entries of balance,
 * deposit, or withdrawal amounts.
 *
 * Supplemental classes (data structures) used:
 *
 * bankRecord: an object to store a single name and balance, with methods to
 * allow manipulation of the stored contents.
 *
 * ledger: an extension of the LinkedList that holds only bankRecord objects and
 * allows for list manipulation and access to stored objects.
 *
 * The program is invoked without any arguments and each user input is taken by
 * typing the string and pressing enter.
 *
 */
public class Bank {

    private static ledger bankRecord = new ledger();
    private static Scanner userInput = new Scanner(System.in);

    public static void main(String[] args) {
        boolean stop = false;

        while (!stop) {
            System.out.println(bankRecord.getCurrentRecord());
            displayMenu();
            switch (userInput.nextLine()) {
                case "a":
                    bankRecord.displayRecords();
                    break;
                case "n":
                    bankRecord.newRecord(userInput);
                    break;
                case "q":
                    System.exit(0);
                    break;
                case "r":
                    bankRecord.removeRecord();
                    break;
                case "s":
                    bankRecord.selectRecord(userInput);
                    break;
                case "f":
                    bankRecord.changeFirstName(userInput);
                    break;
                case "l":
                    bankRecord.changeLastName(userInput);
                    break;
                case "d":
                    bankRecord.makeDeposit(userInput);
                    break;
                case "w":
                    bankRecord.makeWithdrawal(userInput);
                    break;
                default:
                    System.out.println("Not a valid command!");
                    break;
            }

        }

    }

    public static void displayMenu() {
        System.out.println("a\tShow all records");
        System.out.println("n\tAdd a new record");
        System.out.println("r\tRemove current record");
        System.out.println("f\tChange the first name in the current record");
        System.out.println("l\tChange the last name in the current record");
        System.out.println("d\tAdd a deposit to the current record");
        System.out.println("w\tMake a withdrawal from the current record");
        System.out.println("s\tSelect a record from the record list to become the current record");
        System.out.print("\nEnter a command from the list above (q to quit): ");
    }
}
