package com.example.bookproject;

import com.example.bookproject.Model.Book;
import com.example.bookproject.repository.BookRepository;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class HelloController {

    @FXML
    private ListView<Book> booksListView;

    @FXML
    private TextField titleTextField;
    @FXML
    private TextArea authorTextArea;
    @FXML
    private TextArea summaryTextArea;
    @FXML
    private TextField isbnTextField;

    @FXML
    private Label statusLabel;

    @FXML
    private Button addButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    private final BookRepository bookRepository = new BookRepository();

    @FXML
    private void initialize() {
        addButton.setOnAction(e -> addBook());
        editButton.setOnAction(e -> editBook());
        deleteButton.setOnAction(e -> deleteBook());
        booksListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateBookDetailsFields(newSelection);
            }
        });
        loadBooks();
    }

    private void addBook() {
        Book newBook = new Book();
        newBook.setTitle(titleTextField.getText());
        newBook.setAuthor(authorTextArea.getText());
        newBook.setBackCoverText(summaryTextArea.getText());
        newBook.setIsbn(isbnTextField.getText());
        bookRepository.save(newBook);
        booksListView.getItems().add(newBook);
        statusLabel.setText("New book added: " + newBook.getTitle());
    }

    private void editBook() {
        Book selectedBook = booksListView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            selectedBook.setTitle(titleTextField.getText());
            selectedBook.setAuthor(authorTextArea.getText());
            selectedBook.setBackCoverText(summaryTextArea.getText());
            selectedBook.setIsbn(isbnTextField.getText());
            bookRepository.update(selectedBook);
            statusLabel.setText("Updated book: " + selectedBook.getTitle());
        }
    }

    private void deleteBook() {
        Book selectedBook = booksListView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            bookRepository.delete(selectedBook);
            booksListView.getItems().remove(selectedBook);
            statusLabel.setText("Deleted book: " + selectedBook.getTitle());
        }
    }

    private void updateBookDetailsFields(Book book) {
        titleTextField.setText(book.getTitle());
        authorTextArea.setText(book.getAuthor());
        summaryTextArea.setText(book.getBackCoverText());
        isbnTextField.setText(book.getIsbn());
    }

    private void loadBooks() {
        booksListView.getItems().setAll(bookRepository.findAll());
    }
}
