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
    static Scanner cmdscnr = new Scanner(System.in);
    static Scanner inputscnr = new Scanner(System.in);
    static ArrayList <Transaction> allTransactions = new ArrayList<>();

    public static void main(String[] args) {
        readTransactions();

        System.out.println("Welcome to your accounting ledger!\n");

        mainMenu();
    }

    public static void readTransactions() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("transactions.csv"));

            String firstLine = br.readLine();
            String input;

            while ((input = br.readLine()) != null) {
                String[] transactionArr = input.split("\\|");
                String date = transactionArr[0];
                String time = transactionArr[1];
                String description = transactionArr[2];
                String vendor = transactionArr[3];
                double amount = Double.parseDouble(transactionArr[4]);

                allTransactions.add(new Transaction(date, time, description, vendor, amount));
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void mainMenu() {
        int choice;

        do {
            System.out.println("Please select one of the following options -");
            System.out.println(" 1) Add a deposit");
            System.out.println(" 2) Add a payment");
            System.out.println(" 3) View your ledger");
            System.out.println(" 4) Exit ledger\n");
            System.out.print("Please enter the number that corresponds to your selection: ");

            choice = cmdscnr.nextInt();

            switch (choice) {
                case 1:
                    depositMenu();
                    break;
                case 2:
                    paymentMenu();
                    break;
                case 3:
                    ledgerMenu();
                    break;
                case 4:
                    System.out.println("\nExiting...\nSee you soon :)");
                    System.exit(0);
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.\n");
            }
        } while (true);
    }

    public static void depositMenu() {
        int choice;
        System.out.println("\nYou have selected the deposit menu.");

        do {
            System.out.println("\nPlease enter the details of your deposit below -");

            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            System.out.print("Description: ");
            String description = inputscnr.nextLine();

            System.out.print("Vendor: ");
            String vendor = inputscnr.nextLine();

            System.out.print("Amount: $");
            double amount = inputscnr.nextDouble();
            inputscnr.nextLine();

            Transaction transaction = new Transaction(date, time, description, vendor, amount);
            allTransactions.add(transaction);

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
                choice = cmdscnr.nextInt();
                cmdscnr.nextLine();

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
                }
            } while (choice < 1 || choice > 3);
        } while (choice == 1);
    }

    public static void paymentMenu() {
        int choice;
        System.out.println("\nYou have selected the payment menu.");

        do {
            System.out.println("\nPlease enter the details of your payment below -");

            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            System.out.print("Description: ");
            String description = inputscnr.nextLine();

            System.out.print("Vendor: ");
            String vendor = inputscnr.nextLine();

            System.out.print("Amount: $");
            double amount = inputscnr.nextDouble();
            inputscnr.nextLine();

            amount *= -1;

            Transaction transaction = new Transaction(date, time, description, vendor, amount);
            allTransactions.add(transaction);

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
                choice = cmdscnr.nextInt();
                cmdscnr.nextLine();

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
                }
            } while (choice < 1 || choice > 3);
        } while (choice == 1);
    }

    public static void ledgerMenu() {
        int choice;
        System.out.println("\nYou have selected the ledger menu.");

        do {
            System.out.println("\nPlease select one of the following options -");
            System.out.println(" 1) Display all ledger entries");
            System.out.println(" 2) Display only deposits");
            System.out.println(" 3) Display only payments");
            System.out.println(" 4) Run reports");
            System.out.println(" 5) Return to the main menu\n");
            System.out.print("Please enter the number that corresponds to your selection: ");

            choice = cmdscnr.nextInt();

            switch (choice) {
                case 1:
                    displayAll();
                    break;
                case 2:
                    displayDeposits();
                    break;
                case 3:
                    displayPayments();
                    break;
                case 4:
                    reportsSubmenu();
                    break;
                case 5:
                    System.out.println("\nReturning to the main menu...\n");
                    mainMenu();
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    public static void displayAll() {
        System.out.println("\nDisplaying all transactions:\n");

        for (int i = allTransactions.size() - 1; i >= 0; i--) {
            Transaction transaction = allTransactions.get(i);
            System.out.printf("Date: %s, Time: %s, Description: %s, Vendor: %s, Amount: $%.2f\n",
                    transaction.getDate(),
                    transaction.getTime(),
                    transaction.getDescription(),
                    transaction.getVendor(),
                    transaction.getAmount());
        }
    }

    public static void displayDeposits() {
        System.out.println("\nDisplaying deposits:\n");

        for (int i = allTransactions.size() - 1; i >= 0; i--) {
            Transaction transaction = allTransactions.get(i);

            if (transaction.getAmount() > 0) {
                System.out.printf("Date: %s, Time: %s, Description: %s, Vendor: %s, Amount: $%.2f\n",
                        transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount());
            }
        }
    }

    public static void displayPayments() {
        System.out.println("\nDisplaying payments:\n");

        for (int i = allTransactions.size() - 1; i >= 0; i--) {
            Transaction transaction = allTransactions.get(i);

            if (transaction.getAmount() < 0) {
                System.out.printf("Date: %s, Time: %s, Description: %s, Vendor: %s, Amount: $%.2f\n",
                        transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount());
            }
        }
    }

    public static void reportsSubmenu() {
        int choice;
        System.out.println("\nYou have selected the reports menu.");
        System.out.println("Listed below are an assortment of filters for viewing your ledger.");

        do {
            System.out.println("\nPlease select one of the following reports -");
            System.out.println(" 1) Current month to date");
            System.out.println(" 2) Previous month's transactions");
            System.out.println(" 3) Current year to date");
            System.out.println(" 4) Previous year's transactions");
            System.out.println(" 5) Search by vendor");
            System.out.println(" 6) Custom search");
            System.out.println(" 7) Return to the ledger menu");
            System.out.println(" 8) Return to the main menu\n");
            System.out.print("Please enter the number that corresponds to your selection: ");

            choice = cmdscnr.nextInt();

            switch (choice) {
                case 1:
                    monthToDate();
                    break;
                case 2:
                    previousMonth();
                    break;
                case 3:
                    yearToDate();
                    break;
                case 4:
                    previousYear();
                    break;
                case 5:
                    vendorSearch();
                    break;
                case 6:
                    customSearch();
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
            }
        } while (choice != 7 || choice != 8);
    }

    public static void monthToDate() {
        LocalDate date = LocalDate.now();
        int year = date.getYear();
        int month = date.getMonthValue();
        boolean hasTransactions = false;
        DateTimeFormatter frmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.println("\nDisplaying all transactions made this month:\n");

        for (int i = allTransactions.size() - 1; i >= 0; i--) {
            Transaction transaction = allTransactions.get(i);
            LocalDate transactionDate = LocalDate.parse(transaction.getDate(), frmt);

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

        if (!hasTransactions) {
            System.out.println("No transactions have been made this month.");
        }
    }

    public static void previousMonth() {
        LocalDate date = LocalDate.now();
        int year = date.getYear();
        int month = date.getMonthValue();
        boolean hasTransactions = false;
        DateTimeFormatter frmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (month == 1) {
            year -= 1;
            month = 12;
        } else {
            month -= 1;
        }

        System.out.println("\nDisplaying all transactions made last month:\n");

        for (int i = allTransactions.size() - 1; i >= 0; i--) {
            Transaction transaction = allTransactions.get(i);
            LocalDate transactionDate = LocalDate.parse(transaction.getDate(), frmt);

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

        if (!hasTransactions) {
            System.out.println("No transactions were made last month.");
        }
    }

    public static void yearToDate() {
        LocalDate date = LocalDate.now();
        int year = date.getYear();
        boolean hasTransactions = false;
        DateTimeFormatter frmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.println("\nDisplaying all transactions made this year:\n");

        for (int i = allTransactions.size() - 1; i >= 0; i--) {
            Transaction transaction = allTransactions.get(i);
            LocalDate transactionDate = LocalDate.parse(transaction.getDate(), frmt);

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

        if (!hasTransactions) {
            System.out.println("No transactions have been made this year.");
        }
    }

    public static void previousYear() {
        LocalDate date = LocalDate.now();
        int year = date.getYear() - 1;
        boolean hasTransactions = false;
        DateTimeFormatter frmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.println("\nDisplaying all transactions made last year:\n");

        for (int i = allTransactions.size() - 1; i >= 0; i--) {
            Transaction transaction = allTransactions.get(i);
            LocalDate transactionDate = LocalDate.parse(transaction.getDate(), frmt);

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

        if (!hasTransactions) {
            System.out.println("No transactions were made last year.");
        }
    }

    public static void vendorSearch() {
        boolean hasTransactions = false;

        System.out.print("\nPlease enter the vendor name to view related transactions: ");
        String vendor = inputscnr.nextLine();

        System.out.println("\nDisplaying all transactions made with \"" + vendor + "\":\n");

        for (int i = allTransactions.size() - 1; i >= 0; i--) {
            Transaction transaction = allTransactions.get(i);
            String transactionVendor = transaction.getVendor();

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

        if (!hasTransactions) {
            System.out.println("No transactions have been made with \"" + vendor + "\".");
        }
    }

    public static void customSearch() {
        boolean hasTransactions = false;
        DateTimeFormatter frmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.println("\nPlease enter your search criteria below (leave fields blank to skip) -\n");

        System.out.print("Start Date (yyyy-MM-dd): ");
        String startDate = inputscnr.nextLine();
        System.out.print("End Date (yyyy-MM-dd): ");
        String endDate = inputscnr.nextLine();
        System.out.print("Description: ");
        String description = inputscnr.nextLine();
        System.out.print("Vendor: ");
        String vendor = inputscnr.nextLine();
        System.out.print("Amount: ");
        String amount = inputscnr.nextLine();

        System.out.println("\nDisplaying all transactions that match the specified criteria:\n");

        for (int i = allTransactions.size() - 1; i >= 0; i--) {
            Transaction transaction = allTransactions.get(i);
            LocalDate transactionDate = LocalDate.parse(transaction.getDate(), frmt);

            boolean matchesStartDate = startDate.isEmpty() || !transactionDate.isBefore(LocalDate.parse(startDate, frmt));
            boolean matchesEndDate = endDate.isEmpty() || !transactionDate.isAfter(LocalDate.parse(endDate, frmt));
            boolean matchesDescription = description.isEmpty() || transaction.getDescription().equalsIgnoreCase(description);
            boolean matchesVendor = vendor.isEmpty() || transaction.getVendor().equalsIgnoreCase(vendor);
            boolean matchesAmount = amount.isEmpty() || transaction.getAmount() == Double.parseDouble(amount);

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

        if (!hasTransactions) {
            System.out.println("No transactions match the specified criteria.");
        }
    }
}