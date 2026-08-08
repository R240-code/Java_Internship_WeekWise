package Assignments_Week2;

// Abstract User class
abstract class User {

    // Encapsulation
    private String name;

    User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Abstraction
    abstract void displayUserType();
}


// Inheritance
class Member extends User {

    Member(String name) {
        super(name);
    }

    @Override
    void displayUserType() {
        System.out.println("User Type: Library Member");
    }
}


// Book class
class Book {

    // Encapsulation
    private String title;
    private String author;
    private boolean available;

    Book(String title, String author) {
        this.title = title;
        this.author = author;
        available = true;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}


// Library class
class Library {

    private Book book;

    // Add Book
    public void addBook(Book book) {
        this.book = book;
        System.out.println("Book added successfully!");
    }

    // Borrow Book
    public void borrowBook(User user) {

        if (book != null && book.isAvailable()) {

            book.setAvailable(false);

            System.out.println(
                    user.getName() + " borrowed "
                            + book.getTitle()
            );

        } else {

            System.out.println("Book is not available.");
        }
    }

    // Return Book
    public void returnBook() {

        if (book != null && !book.isAvailable()) {

            book.setAvailable(true);

            System.out.println(
                    "Book returned successfully!"
            );

        } else {

            System.out.println("Book was not borrowed.");
        }
    }
}


// Main class
public class LibraryManagement {

    public static void main(String[] args) {

        // Create Book object
        Book book = new Book(
                "Java Programming",
                "James Gosling"
        );

        // Create Member object
        User user = new Member("Rohit");

        // Create Library object
        Library library = new Library();

        // Add Book
        library.addBook(book);

        // Display User Type
        user.displayUserType();

        // Borrow Book
        library.borrowBook(user);

        // Try to borrow the same book
        library.borrowBook(user);

        // Return Book
        library.returnBook();

        // Borrow Book again
        library.borrowBook(user);
    }
}

