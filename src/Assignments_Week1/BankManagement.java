package Assignments_Week1;

import java.util.Scanner;

public class BankManagement {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        double balance = 0;
        int choice;
        double amount;

        do {
            System.out.println("\n==============================");
            System.out.println("   BANK MANAGEMENT SYSTEM");
            System.out.println("==============================");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter deposit amount: ₹");
                    amount = sc.nextDouble();

                    if (amount > 0) {
                        balance += amount;
                        System.out.println("₹" + amount + " deposited successfully.");
                    } else {
                        System.out.println("Invalid amount!");
                    }
                    break;

                case 2:
                    System.out.print("Enter withdrawal amount: ₹");
                    amount = sc.nextDouble();

                    if (amount <= 0) {
                        System.out.println("Invalid amount!");
                    } else if (amount > balance) {
                        System.out.println("Insufficient Balance!");
                    } else {
                        balance -= amount;
                        System.out.println("₹" + amount + " withdrawn successfully.");
                    }
                    break;

                case 3:
                    System.out.println("Current Balance: ₹" + balance);
                    break;

                case 4:
                    System.out.println("Thank you for using Bank Management System.");
                    break;

                default:
                    System.out.println("Invalid Choice! Please try again.");
            }

        } while (choice!=4);

        sc.close();
    }
}