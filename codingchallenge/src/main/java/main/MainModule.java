package main;

import dao.ILoanRepository;
import dao.ILoanRepositoryImpl;
import entity.Loan;
import exception.InvalidLoanException;
import util.DBConnUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MainModule {
    public static void main(String[] args) {
        // Establish connection to the database
        try {
            Connection connection = DBConnUtil.getConnection();
            ILoanRepositoryImpl loanRepository = new ILoanRepositoryImpl(connection);
            Scanner scanner = new Scanner(System.in);

            // Main menu loop
            boolean running = true;
            while (running) {
                // Display menu options
                System.out.println("\nLoan Management System");
                System.out.println("1. Apply for a loan");
                System.out.println("2. Get all loans");
                System.out.println("3. Get loan by ID");
                System.out.println("4. Repay a loan");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                try {
                    // Read user choice
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character

                    switch (choice) {
                        case 1:
                            // Apply for a loan
                            applyForLoan(loanRepository, scanner);
                            break;
                        case 2:
                            // Get all loans
                            getAllLoans(loanRepository);
                            break;
                        case 3:
                            // Get loan by ID
                            getLoanById(loanRepository, scanner);
                            break;
                        case 4:
                            // Repay a loan
                            repayLoan(loanRepository, scanner);
                            break;
                        case 5:
                            // Exit the program
                            running = false;
                            break;
                        default:
                            System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.nextLine(); // Consume invalid input
                }
            }

            // Close the connection when done
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
    }

    // Method to apply for a loan
    private static void applyForLoan(ILoanRepository loanRepository, Scanner scanner) {
        // Prompt user for loan details
        System.out.println("\nApplying for a loan");
        System.out.print("Enter loan ID: ");
        int loanId = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
        // You can prompt for other loan details similarly

        // Create a loan object with the entered details
        Loan loan = new Loan();
        // Set loan details

        // Apply for the loan
        loanRepository.applyLoan(loan);
    }

    // Method to get all loans
    private static void getAllLoans(ILoanRepository loanRepository) {
        System.out.println("\nAll Loans:");
        List<Loan> loans = loanRepository.getAllLoan();
        for (Loan loan : loans) {
            loan.printAllInfo();
        }
    }

    // Method to get loan by ID
    private static void getLoanById(ILoanRepository loanRepository, Scanner scanner) {
        System.out.println("\nGet Loan by ID");
        System.out.print("Enter loan ID: ");
        int loanId = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        try {
            Loan loan = loanRepository.getLoanById(loanId);
            loan.printAllInfo();
        } catch (InvalidLoanException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to repay a loan
    private static void repayLoan(ILoanRepository loanRepository, Scanner scanner) {
        System.out.println("\nRepay a Loan");
        System.out.print("Enter loan ID: ");
        int loanId = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
        System.out.print("Enter repayment amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline character

        try {
            loanRepository.loanRepayment(loanId, amount);
            System.out.println("Loan repayment successful.");
        } catch (InvalidLoanException e) {
            System.out.println(e.getMessage());
        }
    }
}

