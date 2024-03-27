package dao;

import entity.Loan;
import exception.InvalidLoanException;

import java.util.List;

public interface ILoanRepository {
    // Apply for a new loan
    void applyLoan(Loan loan);

    // Calculate interest for a given loan ID
    double calculateInterest(int loanId) throws InvalidLoanException;

    // Get the status of a loan by loan ID
    String loanStatus(int loanId) throws InvalidLoanException;

    // Calculate EMI for a given loan ID
    double calculateEMI(int loanId) throws InvalidLoanException;

    // Repay a portion of a loan by loan ID and amount
    void loanRepayment(int loanId, double amount) throws InvalidLoanException;

    // Get all loans
    List<Loan> getAllLoan();

    // Get loan by ID
    Loan getLoanById(int loanId) throws InvalidLoanException;

	double calculateInterest(Loan loan);

	double calculateEMI(Loan loan);
}
