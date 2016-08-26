/*
 * Sarah Overlander
 */
package bank;

import java.text.NumberFormat;
import java.util.*;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sarah
 */
public class ledger extends LinkedList<bankRecord> {

    private int currentRecord = -1;
    private char modifier = 'n';
    private BigDecimal modQuantity;

    public String getCurrentRecord() {
        String output;
        if (currentRecord >= 0) {
            switch (modifier) {
                case 'n':
                    output = "Current record is: " + this.get(currentRecord).getFirstName() + " " + this.get(currentRecord).getLastName() + "\n";
                    return output;
                case 'd':
                    output = "Current record is: " + this.get(currentRecord).getFirstName() + " " + this.get(currentRecord).getLastName() + " with " + NumberFormat.getCurrencyInstance(Locale.US).format(modQuantity) + " added to deposit.\n";
                    modifier = 'n';
                    return output;
                case 'w':
                    output = "Current record is: " + this.get(currentRecord).getFirstName() + " " + this.get(currentRecord).getLastName() + " with " + NumberFormat.getCurrencyInstance(Locale.US).format(modQuantity) + " withdrawn.\n";
                    modifier = 'n';
                    return output;
            }

        }
        return "";
    }

    /*
     * Pre-condition: A LinkedList<bankRecord> bankRegister exists.
     * Post-condition: A formatted list of records will be printed to the
     * screen, or an alternate message if there have been no records added.
     */
    public void displayRecords() {
        if (this.size() == 0) {
            System.out.println("\nNo records exist!");
        } else {
            String FORMAT = "%-15s %-15s %-15s";
            System.out.print("\n");
            System.out.println(String.format(FORMAT, "First Name", "Last Name", "Balance"));
            System.out.println(String.format(FORMAT, "----------", "----------", "-------"));
            for (int i = 0; i < this.size(); i++) {
                bankRecord thisRecord = this.get(i);
                System.out.println(String.format(FORMAT, thisRecord.getFirstName(), thisRecord.getLastName(), thisRecord.getBalance()));
            }
            System.out.print("\n");
        }
    }
    /*
     * Pre-conditions: A Scanner userInput has been implemented to capture user
     * input and a LinkedList<bankRecord> bankRegister exists. Post-conditions:
     * A new bankRecord object is created with the specified parameters and is
     * inserted into the LinkedList bankRegister in the appropriate location,
     * sorted alphabetically by last name, then first name (if necessary).
     * Correct position can be verified by displaying the records.
     */

    public void newRecord(Scanner userInput) {
        //Gets the values for the new record
        System.out.print("\nEnter first name: ");
        String firstName = userInput.nextLine();
        System.out.print("Enter last name: ");
        String lastName = userInput.nextLine();
        System.out.print("Enter balance amount (not required): ");
        String balance = userInput.nextLine();
        int insertPosition = getInsertionPoint(lastName, firstName);
        if (insertPosition < 0) {
            return;
        }
        if (!balance.equals("")) {
            int count = 0;
            while (true) {
                try {
                    this.add(insertPosition, new bankRecord(firstName, lastName, balance));
                    break;
                } catch (NumberFormatException e) {
                    if (++count >= 3) {
                        throw e;
                    }
                    System.out.println("Please enter a number!");
                    System.out.print("Enter balance amount: ");
                    balance = userInput.nextLine();
                }
            }

        } else {
            this.add(insertPosition, new bankRecord(firstName, lastName));
        }
        currentRecord = insertPosition;
        System.out.print("\n");
    }

    /*
     * Pre-condition: LinkedList<bankRecord> bankRegister exists, and there is
     * at least one record in it that is currently selected. Post-condition: The
     * specified record is removed from the LinkedList and the record selection
     * is cleared.
     */
    public void removeRecord() {
        if (currentRecord >= 0) {
            System.out.println("Deleted: " + this.get(currentRecord).getFirstName() + " " + this.get(currentRecord).getLastName());
            this.remove(currentRecord);
            currentRecord = -1;
        } else {
            System.out.println("No record selected!\n");
        }
    }

    /*
     * Pre-condition: A valid Scanner userInput is configured to capture user
     * input and at least one record exists in a LinkedList<bankRecord>
     * bankRegister. Post-condition: A record is selected or an error message is
     * displayed if there is no matching record.
     */
    public void selectRecord(Scanner userInput) {
        System.out.print("Enter first name: ");
        String firstName = userInput.nextLine();
        System.out.print("Enter last name: ");
        String lastName = userInput.nextLine();
        currentRecord = -1;
        for (int i = 0; i < this.size(); i++) {
            bankRecord temp = this.get(i);
            if ((temp.getFirstName().equals(firstName)) && (temp.getLastName().equals(lastName))) {
                currentRecord = i;
            }
        }
        if (currentRecord < 0) {
            System.out.println("Record not found!\n");
        }
    }
    /*
     * Pre-conditions: LinkedList<bankRecord> bankRegister exists, with at least
     * one record, and a record is selected. Post-conditions: The specified
     * record has the first name changed as specified.
     */

    public void changeFirstName(Scanner userInput) {
        if (currentRecord >= 0) {
            System.out.print("Enter new first name: ");
            String newName = userInput.nextLine();
            bankRecord temp = new bankRecord("", "");
            try {
                temp = this.get(currentRecord).clone();
            } catch (CloneNotSupportedException ex) {
                //this should never happen
                Logger.getLogger(ledger.class.getName()).log(Level.SEVERE, null, ex);
            }
            temp.modifyFirstName(newName);
            int insertionPoint = getInsertionPoint(temp.getLastName(), temp.getFirstName());
            if (insertionPoint >= 0) {
                this.add(insertionPoint, temp);
                this.remove(currentRecord);
                currentRecord = insertionPoint;
            } else {
                System.out.println("Someone with this first and last name already exists!");

            }

        } else {
            System.out.println("No record selected!\n");
        }
    }


    /*
     * Pre-conditions: LinkedList<bankRecord> bankRegister exists, with at least
     * one record, and a record is selected. Post-conditions: The specified
     * record has the last name changed as specified and the record has been
     * removed and re-sorted into the list.
     */
    public void changeLastName(Scanner userInput) {
        if (currentRecord >= 0) {
            System.out.print("Enter new last name: ");
            String newName = userInput.nextLine();
            bankRecord temp = new bankRecord("", "");
            try {
                temp = this.get(currentRecord).clone();
            } catch (CloneNotSupportedException ex) {
                //this should never happen
                Logger.getLogger(ledger.class.getName()).log(Level.SEVERE, null, ex);
            }
            temp.modifyLastName(newName);
            int insertionPoint = getInsertionPoint(temp.getLastName(), temp.getFirstName());
            if (insertionPoint >= 0) {
                this.add(insertionPoint, temp);
                this.remove(currentRecord);
                currentRecord = insertionPoint;
            } else {
                System.out.println("Someone with this first and last name already exists!");
            }
        } else {
            System.out.println("No record selected!\n");
        }
    }
    /*
     * Pre-conditions: LinkedList<bankRecord> bankRegister exists and has at
     * least one record, and a record is selected. Scanner userInput is
     * configured for receiving user data. Post-conditions: The specified
     * record's balance is increased by the deposit amount and the selected
     * record message reflects the deposit.
     */

    public void makeDeposit(Scanner userInput) {
        if (currentRecord >= 0) {
            modifier = 'd';
            System.out.print("Enter deposit amount: $");
            String deposit = userInput.nextLine();
            int tries = 0;
            while (true) {
                try {
                    modQuantity = new BigDecimal(deposit);
                    this.get(currentRecord).makeDeposit(deposit);
                } catch (NumberFormatException e) {
                    if (++tries > 3) {
                        throw e;
                    }
                    System.out.println("Please enter a number!");
                    System.out.print("Deposit amount: ");
                    deposit = userInput.nextLine();
                }
                System.out.print("\n");
            }
        } else {
            System.out.println("No record selected!\n");
        }
    }
    /*
     * Pre-conditions: LinkedList<bankRecord> bankRegister exists and has at
     * least one record, and a record is selected. Scanner userInput is
     * configured for receiving user data. Post-conditions: The specified
     * record's balance is decreased by the withcrawal amount and the selected
     * record message reflects the withdrawal.
     */

    public void makeWithdrawal(Scanner userInput) {
        if (currentRecord >= 0) {
            modifier = 'w';
            System.out.print("Enter withdrawal amount: $");
            String withdrawal = userInput.nextLine();
            int tries = 0;
            while (true) {
                try {
                    modQuantity = new BigDecimal(withdrawal);
                    this.get(currentRecord).makeWithdrawal(withdrawal);
                } catch (NumberFormatException e) {
                    if (++tries > 3) {
                        throw e;
                    }
                    System.out.println("Please enter a number!");
                    System.out.print("Withdrawal amount: ");
                    withdrawal = userInput.nextLine();
                }
                System.out.print("\n");
            }
        } else {
            System.out.println("No record selected!\n");
        }
    }

    public int getInsertionPoint(String lastName, String firstName) {
        if (this.size() > 0) {
            for (int i = 0; i < this.size(); i++) {
                bankRecord temp = this.get(i);
                //if the new last name comes before the first one, insert it first,
                //if the last names are the same, compare the first names.
                if (lastName.compareTo(temp.getLastName()) < 0) {
                    return i;
                } else if ((lastName.equals(temp.getLastName())) && (firstName.compareTo(temp.getFirstName()) < 0)) {
                    return i;
                } else if ((lastName.equals(temp.getLastName())) && (firstName.equals(temp.getFirstName()))) {
                    System.out.println("This username already exists!");
                    return -1;
                }
            }
        } else if (this.size() == 0) {
            return 0;
        }
        return this.size() + 1;
    }
}
