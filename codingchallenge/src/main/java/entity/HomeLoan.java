package entity;

public class HomeLoan extends Loan {
    private String propertyAddress;
    private int propertyValue;

    // Default constructor
    public HomeLoan() {
    }

    // Overloaded constructor with parameters
    public HomeLoan(int loanID, int customerID, double principalAmount, double interestRate, int loanTermMonths, String loanStatus, String propertyAddress, int propertyValue) {
        super(loanID, customerID, principalAmount, interestRate, loanTermMonths, "HomeLoan", loanStatus);
        this.propertyAddress = propertyAddress;
        this.propertyValue = propertyValue;
    }

    // Getters and setters
    public String getPropertyAddress() {
        return propertyAddress;
    }

    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress;
    }

    public int getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(int propertyValue) {
        this.propertyValue = propertyValue;
    }

    // Method to print all information
    @Override
    public void printAllInfo() {
        super.printAllInfo();
        System.out.println("Property Address: " + propertyAddress);
        System.out.println("Property Value: " + propertyValue);
    }
}
