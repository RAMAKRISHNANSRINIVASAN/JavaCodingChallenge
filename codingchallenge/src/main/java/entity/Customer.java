package entity;

public class Customer {
    private int customerID;
    private String name;
    private String emailAddress;
    private String phoneNumber;
    private String address;
    private int creditScore;

    // Default constructor
    public Customer() {
    }

    // Overloaded constructor with parameters
    public Customer(int customerID, String name, String emailAddress, String phoneNumber, String address, int creditScore) {
        this.customerID = customerID;
        this.name = name;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.creditScore = creditScore;
    }

    // Getters and setters
    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    // Method to print all information
    public void printAllInfo() {
        System.out.println("Customer ID: " + customerID);
        System.out.println("Name: " + name);
        System.out.println("Email Address: " + emailAddress);
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Address: " + address);
        System.out.println("Credit Score: " + creditScore);
    }
}

