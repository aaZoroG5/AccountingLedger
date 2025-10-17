package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AccountingLedger {

    //create a scanner
    static Scanner scanner = new Scanner(System.in);
    //create an arraylist for all transactions
    public static ArrayList<Transaction> transactionsLedger = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("|| Welcome to AA Ledger ||");
        mainScreen();
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
            String homeOption = scanner.nextLine().trim().toUpperCase(); //adding the .toUppercase method here ensures all inputs are set as uppercase

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
                transactionsLedger.add(transaction);//forgot to add to array
                //format and print the new instance by using getter methods
                System.out.println("----------------------------------------------------");
            }
            bufferedReader.close();
//            for(var t : transactionsLedger){
//                System.out.println(t.getDescription());} TEST

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //THIS METHOD DISPLAYS THE LEDGER SCREEN OPTIONS
    public static void ledgerScreen() {
        while (true){
            //create ledger screen
            System.out.println("|| Ledger Options ||");
            System.out.println("All transactions (A)");
            System.out.println("See Deposits (D)");
            System.out.println("See Payments (P)");
            System.out.println("Reports (R)");
            System.out.println("Home (H)");

            //ask user what they would like to do
            System.out.print("Select an option: ");
            String ledgerOption = scanner.nextLine().trim().toUpperCase();

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
                return;//BUG FIX: TO ENSURE EACH SCREEN RETURNS CLEANLY, USE RETURN INSTEAD OF CALLING THE mainScreen()
            } else {
                System.out.println("Invalid option");
            }
            transactionsLedger.clear();//BUG FIX: HAD TO ADD THIS LINE SO TH READER REFRESHES WHEN I MOVE BETWEEN SCREENS
        }

    }
    //THIS METHOD WRITES NEW TRANSACTIONS THAT ARE EITHER DEPOSITS OR PAYMENTS
    public static void newTransaction(boolean deposit){
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
        transactionsLedger.add(transaction);
        writeTransaction(transaction);
        System.out.printf("Your %s of $%.2f has been processed!\n",transaction.getDescription(), transaction.getAmount());
    }
    //THIS METHOD WRITES NEW TRANSACTION OBJECTS TO THE ARRAYLIST
    public static void writeTransaction(Transaction transaction){//NOTE: had to add parameters so the writer can
        try {
            //create a file write object connected to the file
            FileWriter fileWriter = new FileWriter("transactions.csv", true);//BUG FIX: THE TRUE PARAMETER OPENS THE FILE IF IT EXISTS AND APPENDS TO THE FILE, DOESN'T DELETE WHAT'S ALREADY THERE
            //create a buffer writer to manage input stream
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            //format the buffer into the csv file format
            bufferedWriter.write(String.format("\n%s|%s|%s|%.2f|%s", transaction.getDate(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount(), transaction.getTime()));
            //added %n in the string format for a newline rather than \n
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file");
        }
    }
    //THIS METHOD FILTERS THROUGH TRANSACTION AND SHOWS DEPOSITS
    public static void filterDeposits(){
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
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error finding file");
        }catch (IOException e){
            System.out.println("Error reading file");
        }
//        Collections.sort(transactionsLedger, Comparator.comparing(Transaction::getDate));//got this method from the website shared in workbook 3a
//       transactionsLedger.forEach(System.out::println);
    }
    //THIS METHOD FILTERS THROUGH TRANSACTIONS AND SHOWS PAYMENTS
    public static void filterPayments(){
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
                Collections.sort(transactionsLedger, Comparator.comparing(Transaction::getDate));
                transactionsLedger.forEach(System.out::println);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error finding file");
        }catch (IOException e){
            System.out.println("Error reading file");
        }
    }
    //THIS METHOD DISPLAYS THE REPORT SCREEN
    public static void reportScreen(){
        while(true){
            System.out.println("""
                (1) Month to Date
                (2) Previous Month
                (3) Year to Date
                (4) Previous Year
                (5) Search by Vendor
                (0) Back to Ledger Screen
                """);
            //ask and save user input
            System.out.print("Select an option: ");
            int reportOption = scanner.nextInt();
            scanner.nextLine();

            //create an if statement for each report option
            if (reportOption == 1){
                readAccountingLedger();
                monthToDate();
            } else if (reportOption == 2) {
                readAccountingLedger();
                previousMonth();
            } else if (reportOption == 3) {
                readAccountingLedger();
                yearToDate();
            } else if (reportOption == 4) {
                readAccountingLedger();
                previousYear();
            } else if (reportOption == 5) {
                readAccountingLedger();
                searchByVendor();
            } else if (reportOption == 0) {
                return;//BUG FIX: TO ENSURE EACH SCREEN RETURNS CLEANLY, USE RETURN INSTEAD OF CALLING THE ledgerScreen()
            }else{
                System.out.println("Invalid option");
            }
        }

    }
    //THIS METHOD ONLY READS THE CSV INSTEAD OF PRINTING ALL TRANSACTIONS, MADE THIS FOR THE REPORT METHODS
    public static void readAccountingLedger (){
        try {
            //create a file reader object connected to the file
            FileReader fileReader = new FileReader("transactions.csv");
            //create a buffer reader to manage input stream
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String input;
            //create a loop that reads each line in the csv file
            while ((input = bufferedReader.readLine()) != null) {

                String[] firstSplit = input.split("\\|");
                if (firstSplit[0].equals("date")) { //I MADE THIS IF STATEMENT TO SKIP OVER THE FIRST LINE OF THE CSV FILE, IT WAS PREVENTING ME FROM PARSING STRINGS TO DOUBLES
                    continue;
                }
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
                transactionsLedger.add(transaction);//forgot to add to array
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //THIS METHOD DISPLAY TRANSACTIONS BY MONTH TO DATE
    public static void monthToDate() {
        LocalDate now = LocalDate.now();
        //TEST//System.out.println("Total transactions: " + transactionsLedger.size());
        transactionsLedger.stream()
                .filter(t -> t.getDate().getMonth() == now.getMonth() && t.getDate().getYear() == now.getYear())
                .toList()
                .forEach(t -> System.out.println(t.getDate() + "|" + t.getTime() + "|" + t.getDescription() + "|" + t.getVendor() + "|" + t.getAmount()
                ));
    }
    //THIS METHOD DISPLAYS TRANSACTIONS FROM THE PREVIOUS MONTH
    public static void previousMonth(){
        LocalDate now = LocalDate.now();
        LocalDate prevMonth = now.minusMonths(1);

        transactionsLedger.stream()
                .filter(t -> t.getDate().getMonth() == prevMonth.getMonth() &&
                        t.getDate().getYear() == prevMonth.getYear())
                .sorted(Comparator.comparing(Transaction::getDate).reversed())
                .toList()
                .forEach(t -> System.out.println(t.getDate() + "|" + t.getTime() + "|" + t.getDescription() + "|" + t.getVendor() + "|" + t.getAmount()
                ));
    }
    //THIS METHOD DISPLAYS TRANSACTIONS YEAR TO DATE
    public static void yearToDate(){
        LocalDate now = LocalDate.now();

        transactionsLedger.stream()
                .filter(t -> t.getDate().getYear() == now.getYear())
                .sorted(Comparator.comparing(Transaction::getDate).reversed())
                .toList()
                .forEach(t -> System.out.println(t.getDate() + "|" + t.getTime() + "|" + t.getDescription() + "|" + t.getVendor() + "|" + t.getAmount()
                ));
    }
    //THIS METHOD DISPLAYS TRANSACTION FROM PREVIOUS YEAR
    public static void previousYear(){
        LocalDate now = LocalDate.now();
        int prevYear = now.getYear() - 1;

        transactionsLedger.stream()
                .filter(t -> t.getDate().getYear() == prevYear)
                .sorted(Comparator.comparing(Transaction::getDate).reversed())
                .toList()
                .forEach(t -> System.out.println(t.getDate() + "|" + t.getTime() + "|" + t.getDescription() + "|" + t.getVendor() + "|" + t.getAmount()
                ));

    }
    //THIS METHOD DISPLAYS TRANSACTION BY VENDOR
    public static void searchByVendor(){
        System.out.print("Enter vendor name ");
        String vendorName = scanner.nextLine().trim();

        transactionsLedger.stream()
                .filter(t -> t.getVendor().toLowerCase().contains(vendorName))
                .sorted(Comparator.comparing(Transaction::getDate).reversed())
                .toList()
                .forEach(t -> System.out.println(t.getDate() + "|" + t.getTime() + "|" + t.getDescription() + "|" + t.getVendor() + "|" + t.getAmount()
                ));
    }
}
