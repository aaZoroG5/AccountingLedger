package com.pluralsight;

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
            System.out.println("test2");
        } else if (homeOption.equals("X")) {
            System.out.println("test3");
        }
        else{
            System.out.println("Invalid option");
        }
        //TODO: considering creating a ledger screen class to separate it from the main screen
    }

}
