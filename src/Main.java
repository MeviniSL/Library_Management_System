import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.InputMismatchException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;,search,delete
public class Main {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/library_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "yourpassword";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Library Management System:");
            System.out.println("1. Add Book");
            System.out.println("2. Update Book");
            System.out.println("3. Search Book");
            System.out.println("4. Delete Book");
            System.out.println("5. Add Member");
            System.out.println("6. Loan a Book");
            System.out.println("7. Return Book");
            System.out.println("8. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    updateBook();
                    break;
                case 3:
                    searchBook();
                    break;
                case 4:
                    deleteBook();
                    break;
                case 5:
                    addmember();
                    break;
                case 6:
                    recordloan();
                    break;
                case 7:
                    returnbook();
                    break;
                case 8:
                    System.out.println("Exiting.....");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    public static void addBook() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter book title: ");
        String title = scanner.nextLine();

        System.out.print("Enter book author: ");
        String author = scanner.nextLine();

        System.out.print("Enter book publisher: ");
        String publisher = scanner.nextLine();

        System.out.print("Enter year published: ");
        int year_published = scanner.nextInt();

        String sql = "INSERT INTO books (title, author, publisher, year_published) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, title);
            statement.setString(2, author);
            statement.setString(3, publisher);
            statement.setInt(4, year_published);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new book was inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateBook() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter book ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter book title: ");
        String title = scanner.nextLine();

        System.out.print("Enter book author: ");
        String author = scanner.nextLine();

        System.out.print("Enter book publisher: ");
        String publisher = scanner.nextLine();

        System.out.print("Enter year published: ");
        int year_published = scanner.nextInt();

        String sql = "UPDATE books SET title = ?, author = ?, publisher = ?, year_published = ? WHERE book_id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, title);
            statement.setString(2, author);
            statement.setString(3, publisher);
            statement.setInt(4, year_published);
            statement.setInt(5, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Book was updated successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void searchBook() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter search keyword(Title/Author/Year_published): ");
        String keyword = scanner.nextLine();

        String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? OR CAST(year_published AS CHAR) LIKE ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String likeKeyword = "%" + keyword + "%";
            statement.setString(1, likeKeyword);
            statement.setString(2, likeKeyword);
            statement.setString(3, likeKeyword);

            ResultSet resultSet = statement.executeQuery();

            boolean found = false;
            while (resultSet.next()) {
                found = true;
                int id = resultSet.getInt("book_id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String publisher = resultSet.getString("publisher");
                int year_published = resultSet.getInt("year_published");

                if (title.contains(keyword)) {
                    System.out.printf("id: %d, title: %s author:%s publisher:%s year_published%d%n", id, title,author,publisher,year_published);
                }
                if (author.contains(keyword)) {
                    System.out.printf("id: %d, title: %s author:%s publisher:%s year_published%d%n", id, title,author,publisher,year_published);
                }
                if (String.valueOf(year_published).contains(keyword)) {
                    System.out.printf("id: %d, title: %s author:%s publisher:%s year_published%d%n", id, title,author,publisher,year_published);
                }
            }

            if (!found) {
                System.out.println("No books found matching the keyword.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteBook() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.print("Enter book title to delete: ");
                String title= scanner.nextLine();

                String sql = "DELETE FROM books WHERE title = ?";

                try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                     PreparedStatement statement = connection.prepareStatement(sql)) {

                    statement.setString(1, title);

                    int rowsDeleted = statement.executeUpdate();
                    if (rowsDeleted > 0) {
                        System.out.println("Book deleted successfully!");
                    } else {
                        System.out.println("No book found with the given ID.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break; // Exit the loop if deletion is successful or no book is found
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer for the book ID.");
                scanner.next(); // Clear the invalid input
            }
        }
    }

    public static void addmember() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter member's name: ");
        String name = scanner.nextLine();

        System.out.print("Enter member's email: ");
        String email = scanner.nextLine();

        System.out.print("Enter member's phone number: ");
        String phone = scanner.nextLine();


        String sql = "INSERT INTO members (name,email,phone) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, phone);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new member was added successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void recordloan() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter book ID : ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter member ID: ");
        int mid = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter loan date (YYYY-MM-DD):");
        String loanDateStr = scanner.nextLine();
        Date loanDate = Date.valueOf(loanDateStr);


        System.out.println("Enter due date (YYYY-MM-DD):");
        String returnDateStr = scanner.nextLine();
        Date returnDate = Date.valueOf(returnDateStr);

        String sql = "INSERT INTO loans (book_id, member_id, loan_date, return_date) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.setInt(2, mid);
            statement.setDate(3, loanDate);
            statement.setDate(4, returnDate);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Loan Book was recorded successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void returnbook() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Loan ID :");
        int lid = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter return date (YYYY-MM-DD):");
        String returnDateStr = scanner.nextLine();
        Date returnDate = Date.valueOf(returnDateStr);

        String sql = "UPDATE loans SET return_date = ? WHERE loan_id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, returnDate);
            statement.setInt(2, lid);


            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Loan Book was recorded successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}


