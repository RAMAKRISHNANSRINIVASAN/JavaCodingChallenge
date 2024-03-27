package entity;

public class Loan {
    private int loanID;
    private int customerID;
    private double principalAmount;
    private double interestRate;
    private int loanTermMonths;
    private String loanType;
    private String loanStatus;

    // Default constructor
    public Loan() {
    }

    // Overloaded constructor with parameters
    public Loan(int loanID, int customerID, double principalAmount, double interestRate, int loanTermMonths, String loanType, String loanStatus) {
        this.loanID = loanID;
        this.customerID = customerID;
        this.principalAmount = principalAmount;
        this.interestRate = interestRate;
        this.loanTermMonths = loanTermMonths;
        this.loanType = loanType;
        this.loanStatus = loanStatus;
    }

    // Getters and setters
    public int getLoanID() {
        return loanID;
    }

    public void setLoanID(int loanID) {
        this.loanID = loanID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public double getPrincipalAmount() {
        return principalAmount;
    }

    public void setPrincipalAmount(double principalAmount) {
        this.principalAmount = principalAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getLoanTermMonths() {
        return loanTermMonths;
    }

    public void setLoanTermMonths(int loanTermMonths) {
        this.loanTermMonths = loanTermMonths;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }

    // Method to print all information
    public void printAllInfo() {
        System.out.println("Loan ID: " + loanID);
        System.out.println("Customer ID: " + customerID);
        System.out.println("Principal Amount: " + principalAmount);
        System.out.println("Interest Rate: " + interestRate);
        System.out.println("Loan Term (Months): " + loanTermMonths);
        System.out.println("Loan Type: " + loanType);
        System.out.println("Loan Status: " + loanStatus);
    }
}

