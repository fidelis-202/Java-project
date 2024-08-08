import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;

class Book {
    String title;
    String author;
    int quantity;

    public Book(String title, String author, int quantity) {
        this.title = title;
        this.author = author;
        this.quantity = quantity;
    }

    public String toString() {
        return title + ", " + author + ", " + quantity;
    }
}

public class LibraryManagementSystem extends JFrame {
    private JTextField titleField, authorField, quantityField, searchField;
    private JTextArea bookList;
    private ArrayList<Book> books = new ArrayList<>();

    public LibraryManagementSystem() {
        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(4, 2));
        topPanel.add(new JLabel("Title:"));
        titleField = new JTextField();
        topPanel.add(titleField);
        topPanel.add(new JLabel("Author:"));
        authorField = new JTextField();
        topPanel.add(authorField);
        topPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        topPanel.add(quantityField);
        topPanel.add(new JLabel("Search:"));
        searchField = new JTextField();
        topPanel.add(searchField);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Book");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });
        buttonPanel.add(addButton);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchBook();
            }
        });
        buttonPanel.add(searchButton);
        JButton displayButton = new JButton("Display All");
        displayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayAllBooks();
            }
        });
        buttonPanel.add(displayButton);
        JButton saveButton = new JButton("Save");
        // Add action listener for save functionality
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveBooks("books.txt"); 
            }
        });
        buttonPanel.add(saveButton);
        JButton loadButton = new JButton("Load");
        // Add action listener for load functionality
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadBooks("books.txt");
            }
        });
        buttonPanel.add(loadButton);

        bookList = new JTextArea();
        bookList.setEditable(false);

        add(topPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(new JScrollPane(bookList), BorderLayout.SOUTH);

        setVisible(true);
    }

    private void addBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        int quantity = Integer.parseInt(quantityField.getText());
        books.add(new Book(title, author, quantity));
        updateBookList();
        clearFields();
    }

    private void searchBook() {
        String searchTerm = searchField.getText();
        bookList.setText("");
        for (Book book : books) {
            if (book.title.toLowerCase().contains(searchTerm.toLowerCase()) ||
                    book.author.toLowerCase().contains(searchTerm.toLowerCase())) {
                bookList.append(book.toString() + "\n");
            }
        }
    }

    private void displayAllBooks() {
        updateBookList();
    }

    private void saveBooks(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (Book book : books) {
                writer.write(book.title + "," + book.author + "," + book.quantity + "\n");
            }
            JOptionPane.showMessageDialog(this, "Books saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving books: " + e.getMessage());
        }
    }

    private void loadBooks(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            books.clear();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Book book = new Book(parts[0], parts[1], Integer.parseInt(parts[2]));
                books.add(book);

            }
            updateBookList();
            JOptionPane.showMessageDialog(this, "Books loaded successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading books: " + e.getMessage());
        }
    }

    private void updateBookList() {
        bookList.setText("");
        for (Book book : books) {
            bookList.append(book.toString() + "\n");
        }
    }

    private void clearFields() {
        titleField.setText("");
        authorField.setText("");
        quantityField.setText("");
    }

    public static void main(String[] args) {
        new LibraryManagementSystem();
    }
}
