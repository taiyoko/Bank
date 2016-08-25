# Bank
A Java console-based bank ledger program written for Data Structures course


**Purpose of program:** To create a console interface for storing names and bank balances of multiple people, provide a way to correct name entries, and to perform account deposits and withdrawals.

**Algorithms used:**
* Switch statements are used to select which function the user desires, as well as deciding which of the Current Record headers to use.
* For loops used to iterate over the ledger list for printing to output and sorting in new/updated records.
* While loop used to keep program running until user exits the program deliberately.
* While loops used to allow limited retries for non-numeric entries of balance, deposit, or withdrawal amounts.

**Supplemental classes (data structures) used:**

* bankRecord: an object to store a single name and balance, with methods to allow manipulation of the stored contents.

* ledger: an extension of the LinkedList that holds only bankRecord objects and allows for list manipulation and access to stored objects.

* The program is invoked without any arguments and each user input is taken by typing the string and pressing enter.

**Note: Instructor requirements did _not_ require file input/output for persistant storage. All accounts will disappear upon program termination.**
