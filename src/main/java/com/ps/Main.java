package com.ps;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;

public class Main {
    static Scanner nmbrscnr = new Scanner(System.in);
    static Scanner txtscnr = new Scanner(System.in);
    static ArrayList <Transaction> allTransactions = new ArrayList<>();

    public static void main(String[] args) {
        readTransactions();

        System.out.println("Welcome to your accounting ledger!");


    }

    public static void readTransactions() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("transactions.csv"));

            String firstLine = br.readLine();
            String input;

            while ((input = br.readLine()) != null) {
                String[] transactionArr = input.split("\\|");
                LocalDate date = LocalDate.parse(transactionArr[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalTime time = LocalTime.parse(transactionArr[1], DateTimeFormatter.ofPattern("HH:mm:ss"));
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
}