# 💰 Accounting Ledger Application

A command-line **Java accounting ledger** that allows users to record deposits, payments, and generate financial reports.

---

## 📋 Table of Contents
- [About the Project](#about-the-project)
- [Features](#features)
- [Installation & Setup](#installation--setup)
- [Interesting Code Snippet](#interesting-code-snippet)
- [Technologies Used](#technologies-used)
- [Author](#author)

---

## 🧾 About the Project

The **Accounting Ledger** is a Java console-based application that lets users:
- Record **deposits** and **payments**
- View a running **ledger** of transactions
- Generate **reports** such as Month-to-Date, Year-to-Date, and Previous Month/Year summaries
- Search transactions by **vendor**

All transactions are stored in a CSV file for easy reading and editing.

---

## ✨ Features

✅ Add deposits and payments  
✅ View all transactions  
✅ Filter deposits or payments  
✅ Generate date-based reports  
✅ Search transactions by vendor  
✅ Data saved in CSV format  
✅ User-friendly CLI navigation  

---

## Interesting Code Snippet

    public static void reportScreen(){
        while(true){
            System.out.println("""
                || Report Options ||
                (1) Month to Date
                (2) Previous Month
                (3) Year to Date
                (4) Previous Year
                (5) Search by Vendor
                (0) Back to Ledger Screen
                """);
            System.out.print("Select an option: ");
            int reportOption = scanner.nextInt();
            scanner.nextLine();

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
                return;
            }else{
                System.out.println("Invalid option");
            }
        }
    }

---

## Technologies Used

Java 17

File I/O (BufferedReader / BufferedWriter)

Collections Framework (ArrayList)

Java Streams & Loops

LocalDate / LocalTime

CSV Data Format

---

## Author

Andy.A

GitHub: https://github.com/aaZoroG5
