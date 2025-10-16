package com.pluralsight;

import javax.sql.rowset.spi.TransactionalWriter;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class AccountingLedger {

    //create a scanner
    static Scanner scanner = new Scanner(System.in);
    //create an arraylist for all transactions
    public static ArrayList<Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("|| Welcome to AA Ledger ||");
        mainScreen();
//        if (!transactions.isEmpty()) {
//            String latestTransaction = String.valueOf(transactions.get(transactions.size() - 1));
//            System.out.println(latestTransaction);
//        }else{
//            System.out.println("arrayList is empty");
//        }
    }
    //THIS METHOD DISPLAYS THE MAIN SCREEN
    private static void mainScreen() {
        //created this loop around the menu,so I can exit the program
        while (true) {
            //create home screen
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
    //THIS METHOD READS THE CSV FILE AND DISPLAYS ALL TRANSACTIONS
    public static void displayAll() {
        //create a try statement that catches I/O exception
        try {
            //create a file reader object connected to the file
            FileReader fileReader = new FileReader("transactions.csv");
            //create a buffer reader to manage input stream
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String input;
            //create a loop that reads each line in the csv file
            while ((input = bufferedReader.readLine()) != null) {
                System.out.println(input);
                String[] firstSplit = input.split("\\|");
                if (firstSplit[0].equals("date")) { //I MADE THIS IF STATEMENT TO SKIP OVER THE FIRST LINE OF THE CSV FILE, IT WAS PREVENTING ME FROM PARSING STRINGS TO DOUBLES
                    continue;
                }
                //TEST for split //System.out.println(firstSplit[0]);

                //create an array to convert string values into corresponding data types
                double[] stringToDouble = new double[1];
                stringToDouble[0] = Double.parseDouble(firstSplit[3]);

                LocalDate[] stringToDate = new LocalDate[1];
                stringToDate[0] = LocalDate.parse(firstSplit[0]);

                LocalTime[] stringToTime = new LocalTime[1];
                stringToTime[0] = LocalTime.parse(firstSplit[4]);

                //initialize the variable to the corresponding arrays
                LocalDate date = stringToDate[0];
                String description = firstSplit[1];
                String vendor = firstSplit[2];
                double amount = stringToDouble[0];
                LocalTime time = stringToTime[0];

                //create a new instance of the employee class
                Transaction transaction = new Transaction(vendor, date, description, amount, time);

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
        System.out.println("|| Ledger Options ||");
        System.out.println("All transactions (A)");
        System.out.println("See Deposits (D)");
        System.out.println("See Payments (P)");
        System.out.println("Reports (R)");
        System.out.println("Home (H)");

        //ask user what they would like to do
        System.out.print("Select an option: ");
        String ledgerOption = scanner.nextLine().toUpperCase();

        //create an if statement for the ledger options given
        if (ledgerOption.equals("A")) {
            displayAll();
        } else if (ledgerOption.equals("D")) {
            filterDeposits();
        } else if (ledgerOption.equals("P")) {
            filterPayments();
        } else if (ledgerOption.equals("R")) {
            reportScreen();
        } else if (ledgerOption.equals("H")) {
            mainScreen();
        } else {
            System.out.println("Invalid option");
        }

    }
    //THIS METHOD WRITES NEW TRANSACTIONS THAT ARE EITHER DEPOSITS OR PAYMENTS
    static void newTransaction(boolean deposit){
        //ask the user for the data for a new transaction,if the parameter is true, the transaction is a deposit
        String description = "Deposit";
        System.out.print("Name of the vendor: ");
        String vendor = scanner.nextLine();
        System.out.print("Amount: ");
        double amount = scanner.nextInt();
        scanner.nextLine();

        //if parameter is false, then the transaction is a payment
        if(deposit == false){
            description = "Payment";
            amount = -amount;
        }

        //create a new transaction object with the user input we previously collected
        Transaction transaction = new Transaction(vendor, LocalDate.now(), description, amount, LocalTime.now());
 //       writeTransaction(transaction);
        System.out.printf("Your %s of $%.2f has been processed!\n",transaction.getDescription(), transaction.getAmount());
    }
    //THIS METHOD WRITES NEW TRANSACTION OBJECTS TO THE ARRAYLIST
    static void writeTransaction(Transaction transaction){//NOTE: had to add parameters so the writer can
        try {
            //create a file write object connected to the file
            FileWriter fileWriter = new FileWriter("transactions.csv");
            //create a buffer writer to manage input stream
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            //format the buffer into the csv file format
            bufferedWriter.write(String.format("%s|%s|%s|%.2f|%s\n", transaction.getVendor(), transaction.getDate(), transaction.getDescription(), transaction.getAmount(), transaction.getTime()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //THIS METHOD FILTERS THROUGH TRANSACTION AND SHOWS DEPOSITS
    static void filterDeposits(){
        try {
            FileReader fileReader = new FileReader("transactions.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String input;
            while((input = bufferedReader.readLine()) !=null){
                String[] split = input.split("\\|");
                if(split[0].equals("date")){//this if statement skips over the first line of the csv file
                    continue;
                }
                double amountToDouble = Double.parseDouble(split[3]);
                if (amountToDouble > 0){
                    System.out.println(input);
                }
                Collections.sort(transactions, Comparator.comparing(Transaction::getDate));//got this method from the website shared in workbook 3a
                transactions.forEach(System.out::println);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error finding file");
        }catch (IOException e){
            System.out.println("Error reading file");
        }
    }
    //THIS METHOD FILTERS THROUGH TRANSACTIONS AND SHOWS PAYMENTS
    static void filterPayments(){
        try {
            FileReader fileReader = new FileReader("transactions.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String input;
            while((input = bufferedReader.readLine()) !=null){
                String[] split = input.split("\\|");
                if(split[0].equals("date")){
                    continue;
                }
                double amountToDouble = Double.parseDouble(split[3]);
                if (amountToDouble < 0){
                    System.out.println(input);
                }
                Collections.sort(transactions, Comparator.comparing(Transaction::getDate));
                transactions.forEach(System.out::println);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error finding file");
        }catch (IOException e){
            System.out.println("Error reading file");
        }
    }
    //THIS METHOD DISPLAYS THE REPORT SCREEN
    static void reportScreen(){
        System.out.println("""
                (1) Month to Date
                (2) Previous Month
                (3) Year to Date
                (4) Previous Year
                (5) Search by Vendor
                (0) Back
                """);
        //ask and save user input
        System.out.print("Select an option: ");
        int reportOption = scanner.nextInt();

        //create an if statement for each report option
        if (reportOption == 1){

        } else if (reportOption == 2) {

        } else if (reportOption == 3) {

        } else if (reportOption == 4) {

        } else if (reportOption == 5) {

        } else if (reportOption == 0) {
            ledgerScreen();
        }else{
            System.out.println("Invalid option");
        }
    }
    //THIS METHOD
}
