package entity;

public class CarLoan extends Loan {
    private String carModel;
    private int carValue;

    // Default constructor
    public CarLoan() {
    }

    // Overloaded constructor with parameters
    public CarLoan(int loanID, int customerID, double principalAmount, double interestRate, int loanTermMonths, String loanStatus, String carModel, int carValue) {
        super(loanID, customerID, principalAmount, interestRate, loanTermMonths, "CarLoan", loanStatus);
        this.carModel = carModel;
        this.carValue = carValue;
    }

    // Getters and setters
    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public int getCarValue() {
        return carValue;
    }

    public void setCarValue(int carValue) {
        this.carValue = carValue;
    }

    // Method to print all information
    @Override
    public void printAllInfo() {
        super.printAllInfo();
        System.out.println("Car Model: " + carModel);
        System.out.println("Car Value: " + carValue);
    }
}