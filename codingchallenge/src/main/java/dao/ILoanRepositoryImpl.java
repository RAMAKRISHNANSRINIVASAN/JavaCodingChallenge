package dao;
import exception.InvalidLoanException;
import entity.Loan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ILoanRepositoryImpl implements ILoanRepository {
    private Connection connection;

    // Constructor to initialize connection
    public ILoanRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void applyLoan(Loan loan) {
        // Confirm loan application with user
        Scanner scanner = new Scanner(System.in);
        System.out.println("Confirm loan application (Yes/No): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if (confirmation.equals("yes")) {
            // Set loan status to pending before storing in the database
            loan.setLoanStatus("pending");

            // Prepare SQL query to insert loan information into the Loan table
            String insertQuery = "INSERT INTO Loan (LoanID, CustomerID, PrincipalAmount, InterestRate, LoanTermMonths, LoanType, LoanStatus) " +
                                 "VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                // Set parameters for the prepared statement
                preparedStatement.setInt(1, loan.getLoanID());
                preparedStatement.setInt(2, loan.getCustomerID());
                preparedStatement.setDouble(3, loan.getPrincipalAmount());
                preparedStatement.setDouble(4, loan.getInterestRate());
                preparedStatement.setInt(5, loan.getLoanTermMonths());
                preparedStatement.setString(6, loan.getLoanType());
                preparedStatement.setString(7, loan.getLoanStatus());

                // Execute the SQL query
                preparedStatement.executeUpdate();

                System.out.println("Loan application submitted successfully.");
            } catch (SQLException e) {
                System.out.println("Error while applying for loan: " + e.getMessage());
            }
        } else {
            System.out.println("Loan application canceled.");
        }
    }
    
   

    // Other methods of ILoanRepository interface will be implemented here
    @Override
    public double calculateInterest(int loanId) throws InvalidLoanException {
        // Retrieve loan from the database
        Loan loan = getLoanByIdFromDatabase(loanId);

        if (loan == null) {
            throw new InvalidLoanException("Loan not found with ID: " + loanId);
        }

        // Calculate interest amount
        double interest = (loan.getPrincipalAmount() * loan.getInterestRate() * loan.getLoanTermMonths()) / 12.0;

        return interest;
    }

    @Override
    public double calculateInterest(Loan loan) {
        // Calculate interest amount for the provided loan
        return (loan.getPrincipalAmount() * loan.getInterestRate() * loan.getLoanTermMonths()) / 12.0;
    }

    // Helper method to retrieve loan from the database by ID
    private Loan getLoanByIdFromDatabase(int loanId) {
        Loan loan = null;
        String query = "SELECT * FROM Loan WHERE LoanID = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, loanId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                loan = new Loan(
                        resultSet.getInt("LoanID"),
                        resultSet.getInt("CustomerID"),
                        resultSet.getDouble("PrincipalAmount"),
                        resultSet.getDouble("InterestRate"),
                        resultSet.getInt("LoanTermMonths"),
                        resultSet.getString("LoanType"),
                        resultSet.getString("LoanStatus")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loan;
    }

    @Override
    public String loanStatus(int loanId) throws InvalidLoanException {
        // Retrieve loan from the database
        Loan loan = getLoanByIdFromDatabase(loanId);

        if (loan == null) {
            throw new InvalidLoanException("Loan not found with ID: " + loanId);
        }

        // Retrieve credit score of associated customer from the database
        int creditScore = getCustomerCreditScore(loan.getCustomerID());

        // Determine loan status based on credit score
        String loanStatus;
        if (creditScore > 650) {
            loanStatus = "approved";
        } else {
            loanStatus = "rejected";
        }

        // Update loan status in the database
        updateLoanStatusInDatabase(loanId, loanStatus);

        return loanStatus;
    }


    // Helper method to retrieve customer credit score from the database
 // Helper method to retrieve customer credit score from the database
    private int getCustomerCreditScore(int customerId) {
        int creditScore = -1; // Default value if credit score is not found

        String query = "SELECT CreditScore FROM Customer WHERE CustomerID = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                creditScore = resultSet.getInt("CreditScore");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return creditScore;
    }


 // Helper method to update loan status in the database
    private void updateLoanStatusInDatabase(int loanId, String loanStatus) {
        String updateQuery = "UPDATE Loan SET LoanStatus = ? WHERE LoanID = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, loanStatus);
            preparedStatement.setInt(2, loanId);

            // Execute the update statement
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Loan status updated successfully.");
            } else {
                System.out.println("Failed to update loan status.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    
    @Override
    public double calculateEMI(int loanId) throws InvalidLoanException {
        // Retrieve loan from the database
        Loan loan = getLoanByIdFromDatabase(loanId);

        if (loan == null) {
            throw new InvalidLoanException("Loan not found with ID: " + loanId);
        }

        // Calculate monthly interest rate
        double monthlyInterestRate = loan.getInterestRate() / 12.0 / 100.0;

        // Calculate EMI
        double principalAmount = loan.getPrincipalAmount();
        int loanTermMonths = loan.getLoanTermMonths();
        double emi = (principalAmount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, loanTermMonths)) /
                     (Math.pow(1 + monthlyInterestRate, loanTermMonths) - 1);

        return emi;
    }

    @Override
    public double calculateEMI(Loan loan) {
        // Calculate monthly interest rate
        double monthlyInterestRate = loan.getInterestRate() / 12.0 / 100.0;

        // Calculate EMI for the provided loan
        double principalAmount = loan.getPrincipalAmount();
        int loanTermMonths = loan.getLoanTermMonths();
        double emi = (principalAmount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, loanTermMonths)) /
                     (Math.pow(1 + monthlyInterestRate, loanTermMonths) - 1);

        return emi;
    }

    // Helper method to retrieve loan from the database by ID
//    private Loan getLoanByIdFromDatabase(int loanId) {
//        Loan loan = null;
//        String query = "SELECT * FROM Loan WHERE LoanID = ?";
//        
//        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//            preparedStatement.setInt(1, loanId);
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            if (resultSet.next()) {
//                loan = new Loan(
//                        resultSet.getInt("LoanID"),
//                        resultSet.getInt("CustomerID"),
//                        resultSet.getDouble("PrincipalAmount"),
//                        resultSet.getDouble("InterestRate"),
//                        resultSet.getInt("LoanTermMonths"),
//                        resultSet.getString("LoanType"),
//                        resultSet.getString("LoanStatus")
//                );
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return loan;
//    }
    
    
    @Override
    public void loanRepayment(int loanId, double amount) throws InvalidLoanException {
        // Retrieve loan from the database
        Loan loan = getLoanByIdFromDatabase(loanId);

        if (loan == null) {
            throw new InvalidLoanException("Loan not found with ID: " + loanId);
        }

        // Calculate EMI
        double emi = calculateEMI(loan);

        // Determine the number of EMIs that can be paid with the provided amount
        int numberOfEmisToPay = (int) (amount / emi);

        // If the amount is less than the EMI, reject the payment
        if (numberOfEmisToPay == 0) {
            return; // Payment rejected
        }

        // Update loan details in the database
        updateLoanDetailsInDatabase(loanId, numberOfEmisToPay);
    }

    // Helper method to retrieve loan from the database by ID
//    private Loan getLoanByIdFromDatabase(int loanId) {
//        Loan loan = null;
//        String query = "SELECT * FROM Loan WHERE LoanID = ?";
//        
//        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//            preparedStatement.setInt(1, loanId);
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            if (resultSet.next()) {
//                loan = new Loan(
//                        resultSet.getInt("LoanID"),
//                        resultSet.getInt("CustomerID"),
//                        resultSet.getDouble("PrincipalAmount"),
//                        resultSet.getDouble("InterestRate"),
//                        resultSet.getInt("LoanTermMonths"),
//                        resultSet.getString("LoanType"),
//                        resultSet.getString("LoanStatus")
//                );
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return loan;
//    }

    // Helper method to calculate EMI
//    private double calculateEMI(Loan loan) {
//        double monthlyInterestRate = loan.getInterestRate() / 12.0 / 100.0;
//        double principalAmount = loan.getPrincipalAmount();
//        int loanTermMonths = loan.getLoanTermMonths();
//        return (principalAmount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, loanTermMonths)) /
//                (Math.pow(1 + monthlyInterestRate, loanTermMonths) - 1);
//    }

    // Helper method to update loan details in the database
    private void updateLoanDetailsInDatabase(int loanId, int numberOfEmisToPay) {
        String updateQuery = "UPDATE Loan SET NumberOfEmisToPay = ? WHERE LoanID = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setInt(1, numberOfEmisToPay);
            preparedStatement.setInt(2, loanId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    @Override
    public List<Loan> getAllLoan() {
        List<Loan> loanList = new ArrayList<>();

        String query = "SELECT * FROM Loan";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Loan loan = new Loan(
                        resultSet.getInt("LoanID"),
                        resultSet.getInt("CustomerID"),
                        resultSet.getDouble("PrincipalAmount"),
                        resultSet.getDouble("InterestRate"),
                        resultSet.getInt("LoanTermMonths"),
                        resultSet.getString("LoanType"),
                        resultSet.getString("LoanStatus")
                );
                loanList.add(loan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loanList;
    }
    
    @Override
    public Loan getLoanById(int loanId) throws InvalidLoanException {
        Loan loan = null;

        String query = "SELECT * FROM Loan WHERE LoanID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, loanId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                loan = new Loan(
                        resultSet.getInt("LoanID"),
                        resultSet.getInt("CustomerID"),
                        resultSet.getDouble("PrincipalAmount"),
                        resultSet.getDouble("InterestRate"),
                        resultSet.getInt("LoanTermMonths"),
                        resultSet.getString("LoanType"),
                        resultSet.getString("LoanStatus")
                );

                // Print loan details
                loan.printAllInfo();
            } else {
                throw new InvalidLoanException("Loan not found with ID: " + loanId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loan;
    }
}
