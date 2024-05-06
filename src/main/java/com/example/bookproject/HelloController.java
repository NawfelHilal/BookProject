package com.example.bookproject;

import com.example.bookproject.Model.Book;
import com.example.bookproject.repository.BookRepository;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import java.util.List;

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
        List<Book> liste = new BookRepository().findAll();
        ObservableList<Object> items = FXCollections.observableArrayList();
        for (Book book:liste){
            items.add(book);
        }
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
@FXML
    private void addBook() {
        Book newBook = new Book();
        newBook.setTitle(titleTextField.getText());
        newBook.setAuthor(authorTextArea.getText());
        newBook.setSummary(summaryTextArea.getText());
        newBook.setIsbn(isbnTextField.getText());
        newBook.setId(1234L);
        System.out.println(newBook);
        new BookRepository().save(newBook);
        booksListView.getItems().add(newBook);
        statusLabel.setText("New book added: " + newBook.getTitle());
    }
@FXML
    private void editBook() {
        Book selectedBook = booksListView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            selectedBook.setTitle(titleTextField.getText());
            selectedBook.setAuthor(authorTextArea.getText());
            selectedBook.setSummary(summaryTextArea.getText());
            selectedBook.setIsbn(isbnTextField.getText());
            bookRepository.update(selectedBook);
            statusLabel.setText("Updated book: " + selectedBook.getTitle());
        }
    }
@FXML
    private void deleteBook() {
        Book selectedBook = booksListView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            bookRepository.delete(selectedBook);
            booksListView.getItems().remove(selectedBook);
            statusLabel.setText("Deleted book: " + selectedBook.getTitle());
        }
    }
@FXML
    private void updateBookDetailsFields(Book book) {
        titleTextField.setText(book.getTitle());
        authorTextArea.setText(book.getAuthor());
        summaryTextArea.setText(book.getSummary());
        isbnTextField.setText(book.getIsbn());
    }
@FXML
    private void loadBooks() {
        booksListView.getItems().setAll(bookRepository.findAll());
    }
}
