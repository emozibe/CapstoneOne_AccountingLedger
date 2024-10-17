/*
 * This application serves as a personal accounting ledger,
 * allowing users to manage and track financial transactions.
 * It provides methods for adding, displaying, and saving transactions,
 * along with user interaction through a menu-driven interface.
 */

package com.ps;

import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;

public class Main {
    // Declare necessary variables and collections for transaction management
    static Scanner cmdscnr = new Scanner(System.in); // Scanner that reads numbers for menu selection
    static Scanner inputscnr = new Scanner(System.in); // Scanner that reads text for user input
    static ArrayList <Transaction> allTransactions = new ArrayList<>(); // This ArrayList stores all transactions

    // Main method that welcomes the user and initiates transaction reading and menu display
    public static void main(String[] args) {
        System.out.println("Welcome to your accounting ledger!\n");

        readTransactions();
        mainMenu();
    }

    // Reads transactions from a CSV file and populates the allTransactions ArrayList
    public static void readTransactions() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("transactions.csv"));

            // Read the header line (not used)
            String firstLine = br.readLine();
            String input;

            // Read each line of the CSV file
            while ((input = br.readLine()) != null) {
                String[] transactionArr = input.split("\\|");
                String date = transactionArr[0];
                String time = transactionArr[1];
                String description = transactionArr[2];
                String vendor = transactionArr[3];
                double amount = Double.parseDouble(transactionArr[4]);

                // Add a new Transaction object to the list
                allTransactions.add(new Transaction(date, time, description, vendor, amount));
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions during file reading
        }
    }

    // Displays the main menu and handles user input for various ledger actions
    public static void mainMenu() {
        int choice;

        do {
            System.out.println("Please select one of the following options -");
            System.out.println(" 1) Add a deposit");
            System.out.println(" 2) Add a payment");
            System.out.println(" 3) View your ledger");
            System.out.println(" 4) Exit ledger\n");
            System.out.print("Please enter the number that corresponds to your selection: ");

            // Input handling for next action
            try {
                choice = cmdscnr.nextInt();
                cmdscnr.nextLine();

                // Execute corresponding action based on user choice
                switch (choice) {
                    case 1:
                        depositMenu(); // Navigate to deposit menu
                        break;
                    case 2:
                        paymentMenu(); // Navigate to payment menu
                        break;
                    case 3:
                        ledgerMenu(); // Navigate to ledger view
                        break;
                    case 4:
                        System.out.println("\nExiting...\nSee you soon :)");
                        System.exit(0); // Exit application
                        break;
                    default:
                        System.out.println("\nInvalid choice. Please try again.\n");
                }
            } catch (Exception e) {
                System.out.println("\nInvalid choice. Please try again.\n"); // Exception handling for invalid input
                cmdscnr.next();
            }
        } while (true); // Loop until a valid choice is made or exit
    }

    // Displays the deposit menu for entering deposits into the ledger
    public static void depositMenu() {
        int choice;
        System.out.println("\nYou have selected the deposit menu.");

        do {
            System.out.println("\nPlease enter the details of your deposit below -");

            // Get the current date and time
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            // Input fields for deposit details
            System.out.print("Description: ");
            String description = inputscnr.nextLine();

            System.out.print("Vendor: ");
            String vendor = inputscnr.nextLine();

            System.out.print("Amount: $");
            double amount = inputscnr.nextDouble();
            inputscnr.nextLine();
            amount = Math.abs(amount);

            // Create a new Transaction object and add it to the transaction list
            Transaction transaction = new Transaction(date, time, description, vendor, amount);
            allTransactions.add(transaction);

            // Write the new transaction to the CSV file
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("transactions.csv", true));
                bw.write(String.format("\n%s|%s|%s|%s|%.2f",
                        transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount()));

                bw.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("\nYou have entered a deposit into your ledger!\n");

            do {
                System.out.println("What would you like to do next?\n");
                System.out.println("Please select one of the following options -");
                System.out.println(" 1) Enter another deposit");
                System.out.println(" 2) Return to the main menu");
                System.out.println(" 3) Exit ledger\n");
                System.out.print("Please enter the number that corresponds to your selection: ");

                // Input handling for next action
                try {
                    choice = cmdscnr.nextInt();
                    cmdscnr.nextLine();

                    // Execute corresponding action based on user choice
                    switch (choice) {
                        case 1:
                            System.out.println("\nStarting another deposit...");
                            break;
                        case 2:
                            System.out.println("\nReturning to the main menu...\n");
                            mainMenu();
                            break;
                        case 3:
                            System.out.println("\nExiting...\nSee you soon :)");
                            System.exit(0);
                        default:
                            System.out.println("\nInvalid choice. Please try again.\n");
                            choice = 0;
                    }
                } catch (Exception e) {
                    System.out.println("\nInvalid choice. Please try again.\n");
                    cmdscnr.next();
                    choice = 0;
                }
            } while (choice < 1 || choice > 3);
        } while (choice == 1);
    }

    // Displays the payment menu for entering payments into the ledger
    public static void paymentMenu() {
        int choice;
        System.out.println("\nYou have selected the payment menu.");

        do {
            System.out.println("\nPlease enter the details of your payment below -");

            // Get the current date and time
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            // Input fields for payment details
            System.out.print("Description: ");
            String description = inputscnr.nextLine();

            System.out.print("Vendor: ");
            String vendor = inputscnr.nextLine();

            System.out.print("Amount: $");
            double amount = inputscnr.nextDouble();
            inputscnr.nextLine();
            amount = Math.abs(amount);
            amount *= -1;

            // Create a new Transaction object and add it to the transaction list
            Transaction transaction = new Transaction(date, time, description, vendor, amount);
            allTransactions.add(transaction);

            // Write the new transaction to the CSV file
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("transactions.csv", true));
                bw.write(String.format("\n%s|%s|%s|%s|%.2f",
                        transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount()));

                bw.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("\nYou have entered a payment into your ledger!\n");

            do {
                System.out.println("What would you like to do next?\n");
                System.out.println("Please select one of the following options -");
                System.out.println(" 1) Enter another payment");
                System.out.println(" 2) Return to the main menu");
                System.out.println(" 3) Exit ledger\n");
                System.out.print("Please enter the number that corresponds to your selection: ");

                // Input handling for next action
                try {
                    choice = cmdscnr.nextInt();
                    cmdscnr.nextLine();

                    // Execute corresponding action based on user choice
                    switch (choice) {
                        case 1:
                            System.out.println("\nStarting another payment...");
                            break;
                        case 2:
                            System.out.println("\nReturning to the main menu...\n");
                            mainMenu();
                            break;
                        case 3:
                            System.out.println("\nExiting...\nSee you soon :)");
                            System.exit(0);
                        default:
                            System.out.println("\nInvalid choice. Please try again.\n");
                            choice = 0;
                    }
                } catch (Exception e) {
                    System.out.println("\nInvalid choice. Please try again.\n");
                    cmdscnr.next();
                    choice = 0;
                }
            } while (choice < 1 || choice > 3);
        } while (choice == 1);
    }

    // Displays the ledger menu for viewing transactions and running reports
    public static void ledgerMenu() {
        int choice;
        System.out.println("\nYou have selected the ledger menu.\n");

        do {
            System.out.println("Please select one of the following options -");
            System.out.println(" 1) Display all ledger entries");
            System.out.println(" 2) Display only deposits");
            System.out.println(" 3) Display only payments");
            System.out.println(" 4) Run reports");
            System.out.println(" 5) Return to the main menu\n");
            System.out.print("Please enter the number that corresponds to your selection: ");

            // Input handling for next action
            try {
                choice = cmdscnr.nextInt();
                cmdscnr.nextLine();

                // Execute corresponding action based on user choice
                switch (choice) {
                    case 1:
                        displayAll(); // Show all ledger entries
                        break;
                    case 2:
                        displayDeposits(); // Show only deposits
                        break;
                    case 3:
                        displayPayments(); // Show only payments
                        break;
                    case 4:
                        reportsSubmenu(); // Access reports submenu
                        break;
                    case 5:
                        System.out.println("\nReturning to the main menu...\n");
                        mainMenu();
                        break;
                    default:
                        System.out.println("\nInvalid choice. Please try again.");
                        choice = 0; // Reset choice for re-prompt
                }
            } catch (Exception e) {
                System.out.println("\nInvalid choice. Please try again.\n");
                cmdscnr.next();
                choice = 0; // Reset choice for re-prompt
            }
        } while (choice != 5); // Continue until the user returns to the main menu
    }

    // Displays all transactions in reverse chronological order
    public static void displayAll() {
        System.out.println("\nDisplaying all transactions:\n");

        // Loop through transactions in reverse order for display
        for (int i = allTransactions.size() - 1; i >= 0; i--) {
            Transaction transaction = allTransactions.get(i);
            // Print transaction details
            System.out.printf("Date: %s, Time: %s, Description: %s, Vendor: %s, Amount: $%.2f\n",
                    transaction.getDate(),
                    transaction.getTime(),
                    transaction.getDescription(),
                    transaction.getVendor(),
                    transaction.getAmount());
        }

        System.out.println();
    }

    // Displays all deposit transactions in reverse chronological order
    public static void displayDeposits() {
        System.out.println("\nDisplaying deposits:\n");

        // Loop through transactions in reverse order for display
        for (int i = allTransactions.size() - 1; i >= 0; i--) {
            Transaction transaction = allTransactions.get(i);

            // Check if the transaction is a deposit
            if (transaction.getAmount() > 0) {
                // Print deposit transaction details
                System.out.printf("Date: %s, Time: %s, Description: %s, Vendor: %s, Amount: $%.2f\n",
                        transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount());
            }
        }

        System.out.println();
    }

    // Displays all payment transactions in reverse chronological order
    public static void displayPayments() {
        System.out.println("\nDisplaying payments:\n");

        // Loop through transactions in reverse order for display
        for (int i = allTransactions.size() - 1; i >= 0; i--) {
            Transaction transaction = allTransactions.get(i);

            // Check if the transaction is a payment
            if (transaction.getAmount() < 0) {
                // Print payment transaction details
                System.out.printf("Date: %s, Time: %s, Description: %s, Vendor: %s, Amount: $%.2f\n",
                        transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount());
            }
        }

        System.out.println();
    }

    // Displays the reports submenu for viewing various filtered reports
    public static void reportsSubmenu() {
        int choice;
        System.out.println("\nYou have selected the reports menu.");
        System.out.println("Listed below are an assortment of filters for viewing your ledger.\n");

        do {
            System.out.println("Please select one of the following reports -");
            System.out.println(" 1) Current month to date");
            System.out.println(" 2) Previous month's transactions");
            System.out.println(" 3) Current year to date");
            System.out.println(" 4) Previous year's transactions");
            System.out.println(" 5) Search by vendor");
            System.out.println(" 6) Custom search");
            System.out.println(" 7) Return to the ledger menu");
            System.out.println(" 8) Return to the main menu\n");
            System.out.print("Please enter the number that corresponds to your selection: ");

            // Input handling for next action
            try {
                choice = cmdscnr.nextInt();
                cmdscnr.nextLine();

                // Execute corresponding action based on user choice
                switch (choice) {
                    case 1:
                        monthToDate(); // Show transactions for the current month to date
                        break;
                    case 2:
                        previousMonth(); // Show transactions for the previous month
                        break;
                    case 3:
                        yearToDate(); // Show transactions for the current year to date
                        break;
                    case 4:
                        previousYear(); // Show transactions for the previous year
                        break;
                    case 5:
                        vendorSearch(); // Search transactions by vendor
                        break;
                    case 6:
                        customSearch(); // Perform a custom search on transactions
                        break;
                    case 7:
                        System.out.println("\nReturning to the ledger menu...");
                        ledgerMenu();
                        break;
                    case 8:
                        System.out.println("\nReturning to the main menu...\n");
                        mainMenu();
                        break;
                    default:
                        System.out.println("\nInvalid choice. Please try again.");
                        choice = 0; // Reset choice for re-prompt
                }
            } catch (Exception e) {
                System.out.println("\nInvalid choice. Please try again.\n");
                cmdscnr.next();
                choice = 0; // Reset choice for re-prompt
            }
        } while (choice != 7 || choice != 8); // Continue until returning to a menu
    }

    // Displays all transactions made in the current month
    public static void monthToDate() {
        LocalDate date = LocalDate.now(); // Get the current date
        int year = date.getYear(); // Extract the current year
        int month = date.getMonthValue(); // Extract the current month
        boolean hasTransactions = false;
        DateTimeFormatter frmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.println("\nDisplaying all transactions made this month:\n");

        for (int i = allTransactions.size() - 1; i >= 0; i--) {
            Transaction transaction = allTransactions.get(i);
            LocalDate transactionDate = LocalDate.parse(transaction.getDate(), frmt);

            // Check if the transaction is from the current month
            if (transactionDate.getYear() == year && transactionDate.getMonthValue() == month) {
                System.out.printf("Date: %s, Time: %s, Description: %s, Vendor: %s, Amount: $%.2f%n",
                        transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount());
                hasTransactions = true;
            }
        }

        // Notify if no transactions were found
        if (!hasTransactions) {
            System.out.println("No transactions have been made this month.");
        }

        System.out.println();
    }

    // Displays all transactions made in the previous month
    public static void previousMonth() {
        LocalDate date = LocalDate.now(); // Get the current date
        int year = date.getYear(); // Extract the current year
        int month = date.getMonthValue(); // Extract the current month
        boolean hasTransactions = false;
        DateTimeFormatter frmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Adjust year and month for January
        if (month == 1) {
            year -= 1; // Move to the previous year
            month = 12; // Set month to December
        } else {
            month -= 1; // Decrement month
        }

        System.out.println("\nDisplaying all transactions made last month:\n");

        for (int i = allTransactions.size() - 1; i >= 0; i--) {
            Transaction transaction = allTransactions.get(i);
            LocalDate transactionDate = LocalDate.parse(transaction.getDate(), frmt);

            // Check if the transaction is from the previous month
            if (transactionDate.getYear() == year && transactionDate.getMonthValue() == month) {
                System.out.printf("Date: %s, Time: %s, Description: %s, Vendor: %s, Amount: $%.2f%n",
                        transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount());
                hasTransactions = true;
            }
        }

        // Notify if no transactions were found
        if (!hasTransactions) {
            System.out.println("No transactions were made last month.");
        }

        System.out.println();
    }

    // Displays all transactions made in the current year
    public static void yearToDate() {
        LocalDate date = LocalDate.now(); // Get the current date
        int year = date.getYear(); // Extract the current year
        boolean hasTransactions = false;
        DateTimeFormatter frmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.println("\nDisplaying all transactions made this year:\n");

        for (int i = allTransactions.size() - 1; i >= 0; i--) {
            Transaction transaction = allTransactions.get(i);
            LocalDate transactionDate = LocalDate.parse(transaction.getDate(), frmt);

            // Check if the transaction is from the current year
            if (transactionDate.getYear() == year) {
                System.out.printf("Date: %s, Time: %s, Description: %s, Vendor: %s, Amount: $%.2f%n",
                        transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount());
                hasTransactions = true;
            }
        }

        // Notify if no transactions were found
        if (!hasTransactions) {
            System.out.println("No transactions have been made this year.");
        }

        System.out.println();
    }

    // Displays all transactions made in the previous year
    public static void previousYear() {
        LocalDate date = LocalDate.now(); // Get the current date
        int year = date.getYear() - 1; // Set to previous year
        boolean hasTransactions = false;
        DateTimeFormatter frmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.println("\nDisplaying all transactions made last year:\n");

        for (int i = allTransactions.size() - 1; i >= 0; i--) {
            Transaction transaction = allTransactions.get(i);
            LocalDate transactionDate = LocalDate.parse(transaction.getDate(), frmt);

            // Check if the transaction date is from the previous year
            if (transactionDate.getYear() == year) {
                System.out.printf("Date: %s, Time: %s, Description: %s, Vendor: %s, Amount: $%.2f%n",
                        transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount());
                hasTransactions = true;
            }
        }

        // Notify if no transactions were found for last year
        if (!hasTransactions) {
            System.out.println("No transactions were made last year.");
        }

        System.out.println();
    }

    // Prompts user to enter a vendor name and displays all transactions with that vendor
    public static void vendorSearch() {
        boolean hasTransactions = false;
        ArrayList <String> uniqueVendors = new ArrayList<>();

        // Collect unique vendors
        for (Transaction transaction : allTransactions) {
            String vendor = transaction.getVendor();
            if (!uniqueVendors.contains(vendor)) {
                uniqueVendors.add(vendor);
            }
        }

        // Display unique vendors to user
        System.out.println("\nDisplaying all unique vendors:");
        for (String vendor : uniqueVendors) {
            System.out.println(" - " + vendor);
        }

        System.out.print("\nPlease enter the vendor name to view related transactions: ");
        String vendor = inputscnr.nextLine();

        System.out.println("\nDisplaying all transactions made with \"" + vendor + "\":\n");

        for (int i = allTransactions.size() - 1; i >= 0; i--) {
            Transaction transaction = allTransactions.get(i);
            String transactionVendor = transaction.getVendor();

            // Check if the transaction's vendor matches user input
            if (transactionVendor.equalsIgnoreCase(vendor)) {
                System.out.printf("Date: %s, Time: %s, Description: %s, Vendor: %s, Amount: $%.2f%n",
                        transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount());
                hasTransactions = true;
            }
        }

        // Notify if no transactions were found for the vendor
        if (!hasTransactions) {
            System.out.println("No transactions have been made with \"" + vendor + "\".");
        }

        System.out.println();
    }

    // Custom search for transactions based on user-defined criteria
    public static void customSearch() {
        boolean hasTransactions = false;
        DateTimeFormatter frmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.println("\nPlease enter your search criteria below (leave fields blank to skip) -\n");

        // Gather and validate start date input
        String startDate;
        while (true) {
            System.out.print("Start Date (yyyy-MM-dd): ");
            startDate = inputscnr.nextLine();
            if (startDate.isEmpty()) break;
            try {
                LocalDate.parse(startDate, frmt);
                break;
            } catch (Exception e) {
                System.out.println("\nInvalid date format. Please use \"yyyy-MM-dd\".\n");
            }
        }

        // Gather and validate end date input
        String endDate;
        while (true) {
            System.out.print("End Date (yyyy-MM-dd): ");
            endDate = inputscnr.nextLine();
            if (endDate.isEmpty()) break;
            try {
                LocalDate.parse(endDate, frmt);
                break;
            } catch (Exception e) {
                System.out.println("\nInvalid date format. Please use \"yyyy-MM-dd\".\n");
            }
        }

        // Gather and validate description input
        System.out.print("Description: ");
        String description = inputscnr.nextLine();

        // Gather and validate vendor input
        System.out.print("Vendor: ");
        String vendor = inputscnr.nextLine();

        // Gather and validate amount input
        Double amount = null;
        while (true) {
            System.out.print("Amount: ");
            String amountInput = inputscnr.nextLine();
            if (amountInput.isEmpty()) break;
            try {
                amount = Double.parseDouble(amountInput);
                break;
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid amount. Please enter a valid number.\n");
            }
        }

        System.out.println("\nDisplaying all transactions that match the specified criteria:\n");

        for (int i = allTransactions.size() - 1; i >= 0; i--) {
            Transaction transaction = allTransactions.get(i);
            LocalDate transactionDate = LocalDate.parse(transaction.getDate(), frmt);

            // Check each criterion to see if the transaction matches
            boolean matchesStartDate = startDate.isEmpty() || !transactionDate.isBefore(LocalDate.parse(startDate, frmt));
            boolean matchesEndDate = endDate.isEmpty() || !transactionDate.isAfter(LocalDate.parse(endDate, frmt));
            boolean matchesDescription = description.isEmpty() || transaction.getDescription().equalsIgnoreCase(description);
            boolean matchesVendor = vendor.isEmpty() || transaction.getVendor().equalsIgnoreCase(vendor);
            boolean matchesAmount = amount == null || transaction.getAmount() == amount;

            // Display transaction if all provided criteria are met
            if (matchesStartDate && matchesEndDate && matchesDescription && matchesVendor && matchesAmount) {
                System.out.printf("Date: %s, Time: %s, Description: %s, Vendor: %s, Amount: $%.2f%n",
                        transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount());
                hasTransactions = true;
            }
        }

        // Notify if no transactions match the search criteria
        if (!hasTransactions) {
            System.out.println("No transactions match the specified criteria.");
        }

        System.out.println();
    }
}