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

        initialMenu();
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

    public static void initialMenu() {
        int choice;

        do {
            System.out.println("Please select one of the following options -");
            System.out.println(" 1) Add a deposit");
            System.out.println(" 2) Make a payment");
            System.out.println(" 3) View all transactions");
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
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.\n");
            }
        } while (choice != 4);
    }

    public static void depositMenu() {
        int cont;
        System.out.println("\nYou have selected the deposit menu.");

        do {
            System.out.println("\nPlease enter the details of your deposit below -");

            System.out.print("Date (yyyy-mm-dd): ");
            String date = inputscnr.nextLine();

            System.out.print("Time (HH:mm:ss): ");
            String time = inputscnr.nextLine();

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
                cont = cmdscnr.nextInt();
                cmdscnr.nextLine();

                switch (cont) {
                    case 1:
                        System.out.println("\nStarting another deposit...");
                        break;
                    case 2:
                        System.out.println("\nReturning to the main menu...\n");
                        initialMenu();
                        break;
                    case 3:
                        System.out.println("\nExiting...\nSee you soon :)");
                        System.exit(0);
                    default:
                        System.out.println("\nInvalid choice. Please try again.\n");
                }
            } while (cont < 1 || cont > 3);
        } while (cont == 1);
    }

    public static void paymentMenu() {
        int cont;
        System.out.println("\nYou have selected the payment menu.");

        do {
            System.out.println("\nPlease enter the details of your payment below -");

            System.out.print("Date (yyyy-mm-dd): ");
            String date = inputscnr.nextLine();

            System.out.print("Time (HH:mm:ss): ");
            String time = inputscnr.nextLine();

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
                bw.write(String.format("\n%s|%s|%s|%s|-%.2f",
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
                cont = cmdscnr.nextInt();
                cmdscnr.nextLine();

                switch (cont) {
                    case 1:
                        System.out.println("\nStarting another payment...");
                        break;
                    case 2:
                        System.out.println("\nReturning to the main menu...\n");
                        initialMenu();
                        break;
                    case 3:
                        System.out.println("\nExiting...\nSee you soon :)");
                        System.exit(0);
                    default:
                        System.out.println("\nInvalid choice. Please try again.\n");
                }
            } while (cont < 1 || cont > 3);
        } while (cont == 1);
    }

    public static void ledgerMenu() {

    }
}