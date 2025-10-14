package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class AccountingLedger {

    //create a scanner
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

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
        if(homeOption.equals("D")){
            System.out.println("test");
        } else if (homeOption.equals("P")) {
            System.out.println("test1");
        } else if (homeOption.equals("L")) {
            fileReader();
        } else if (homeOption.equals("X")) {
            System.out.println("test3");
        }
        else{
            System.out.println("Invalid option");
        }
    }
    //THIS METHOD READS THE CSV FILE
    public static void fileReader(){
        //create a try statement that catches I/O exception
        try{
            //create a file reader object connected to the file
            FileReader payRollReader = new FileReader("transactions_30.csv");
            //create a buffer reader to manage input stream
            BufferedReader bufferedReader = new BufferedReader(payRollReader);
            //can also write this in one line
            //BufferReader bufferreader = new BufferedReader(new FileReader("poem.csv"))

            String input;
            //create a loop that reads each line in the csv file
            while ((input = bufferedReader.readLine()) != null) {
                System.out.println(input);
                String [] firstSplit = input.split("\\|");
                if(firstSplit[0].equals("date")){ //I MADE THIS IF STATEMENT TO SKIP OVER THE FIRST LINE OF THE CSV FILE, IT WAS PREVENTING ME FROM PARSING STRINGS TO DOUBLES
                    continue;
                }
                //TEST for split //System.out.println(firstSplit[0]);

                //created a double array to convert string values into double, note we are only parsing 3 indexes because the fourth is already a string
                double[] stringToDouble = new double[3];
                //parse the strings into double
                stringToDouble[0] = Double.parseDouble(firstSplit[3]);

                LocalDate[] stringToTime = new LocalDate[1];
                stringToTime[0] = LocalDate.parse(firstSplit[0]);

                //TEST for parse //System.out.println(stringToDouble[0]);

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
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    static void ledgerScreen(){

    }
}
