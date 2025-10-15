package com.pluralsight;

import javax.sql.rowset.spi.TransactionalWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class AccountingLedger {

    //create a scanner
    static Scanner scanner = new Scanner(System.in);
    //create an arraylist for all transactions
    public static ArrayList<Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) {
        //created this loop around the menu,so I can exit the program
        while (true) {
            //create home screen
            System.out.println("|| Welcome to AA Ledger ||");
            System.out.println("Deposit (D)");
            System.out.println("Make a Payment (P)");
            System.out.println("Ledger (L)");
            System.out.println("Exit (X)");

            //ask user what they would like to do
            System.out.print("Select an option: ");
            String homeOption = scanner.nextLine().toUpperCase(); //adding the .toUppercase method here ensures all inputs are set as uppercase

            //create an if statement for the home options given
            if (homeOption.equals("D")) {
                newTransaction(true);
            } else if (homeOption.equals("P")) {
                newTransaction(false);
            } else if (homeOption.equals("L")) {
                ledgerScreen();
            } else if (homeOption.equals("X")) {
                break;
            } else {
                System.out.println("Invalid option");
            }
        }
    }

    //THIS METHOD READS THE CSV FILE
    public static void fileReader() {
        //create a try statement that catches I/O exception
        try {
            //create a file reader object connected to the file
            FileReader payRollReader = new FileReader("transactions_30.csv");
            //create a buffer reader to manage input stream
            BufferedReader bufferedReader = new BufferedReader(payRollReader);

            String input;
            //create a loop that reads each line in the csv file
            while ((input = bufferedReader.readLine()) != null) {
                System.out.println(input);
                String[] firstSplit = input.split("\\|");
                if (firstSplit[0].equals("date")) { //I MADE THIS IF STATEMENT TO SKIP OVER THE FIRST LINE OF THE CSV FILE, IT WAS PREVENTING ME FROM PARSING STRINGS TO DOUBLES
                    continue;
                }
                //TEST for split //System.out.println(firstSplit[0]);

                //created a double array to convert string values into double, note we are only parsing 3 indexes because the fourth is already a string
                double[] stringToDouble = new double[1];
                //parse the strings into double
                stringToDouble[0] = Double.parseDouble(firstSplit[3]);

                LocalDate[] stringToTime = new LocalDate[1];
                stringToTime[0] = LocalDate.parse(firstSplit[0]);

                //initialize the variable to the corresponding arrays
                LocalDate date = stringToTime[0];
                String description = firstSplit[1];
                String vendor = firstSplit[2];
                double amount = stringToDouble[0];

                //create a new instance of the employee class
                Transaction transaction = new Transaction(vendor, date, description, amount);

                //format and print the new instance by using getter methods
                System.out.println("----------------------------------------------------");
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //THIS METHOD DISPLAYS THE LEDGER SCREEN OPTIONS
    static void ledgerScreen() {
        //create ledger screen
        System.out.println("Display all transactions (A)");
        System.out.println("Deposits (D)");
        System.out.println("Payments (P)");
        System.out.println("Reports (R)");
        System.out.println("Home (H)");

        //ask user what they would like to do
        System.out.print("Select an option: ");
        String ledgerOption = scanner.nextLine().toUpperCase();

        //create an if statement for the ledger options given
        if (ledgerOption.equals("A")) {
            fileReader();
        } else if (ledgerOption.equals("D")) {

        } else if (ledgerOption.equals("P")) {

        } else if (ledgerOption.equals("R")) {

        } else if (ledgerOption.equals("H")) {

        } else {
            System.out.println("Invalid option");
        }

    }
    //THIS METHOD WRITES NEW TRANSACTIONS THAT ARE EITHER DEPOSITS OR PAYMENTS
    static void newTransaction(boolean deposit){
        //ask the user for the data for a new transaction,if the parameter is true, the transaction is a deposit
        System.out.print("Name of the vendor: ");
        String vendor = scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();
        System.out.print("Amount: ");
        double amount = scanner.nextInt();
        scanner.nextLine();

        //if parameter is false, then the transaction is a payment
        if(deposit == false){
            amount = -amount;
        }

        //create a new transaction object with the user input we previously collected
        Transaction transaction = new Transaction(vendor, LocalDate.now(), description, amount);
        //TransactionalWriter.appendTransaction(transaction);
        System.out.println(transaction.getAmount());
    }
    //TODO:ADD FILE WRITER METHOD
}
