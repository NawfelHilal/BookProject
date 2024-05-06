package com.example.bookproject;

import com.example.bookproject.Model.Book;
import com.example.bookproject.repository.BookRepository;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import java.util.Optional;

public class HelloController {

    @FXML
    private ListView booksListView;

    @FXML
    private TextField titleTextField;
    @FXML
    private TextArea authorTextArea;
    @FXML
    private TextArea summaryTextArea;
    @FXML
    private TextField isbnTextField;
    @FXML
    private  TextField publicationYearField;
    @FXML
    private TextField pageCountField;
    @FXML
    TextArea fourCouvertureField;
    @FXML
    private TextField preview;

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
        booksListView.setItems(items);
        addButton.setOnAction(e -> addBook());
        editButton.setOnAction(e -> editBook());
        deleteButton.setOnAction(e -> deleteBook());
        booksListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateBookDetailsFields((Book) newSelection);
            }
        });
        loadBooks();
    }
@FXML
    private void addBook() {
    String titreTexte = titleTextField.getText();

    boolean bookExistante = false;
    for (Object item : booksListView.getItems()) {
        if (item instanceof Book) {
            Book book = (Book) item;
            if (book.getTitle().equals(titreTexte)){
                bookExistante = true;
                break;
            }
        }
    }

    if (bookExistante) {
        preview.setText("Livre déjà existant");
        System.out.println("Un livre avec le même titre existe déjà. Impossible d'ajouter un nouveau livre avec le même titre.");
    } else {
        String title =titleTextField.getText();
        String author =authorTextArea.getText();
        String summary =summaryTextArea.getText();
        String isbn =isbnTextField.getText();
        String publicationYear =publicationYearField.getText();
        String pageCount = pageCountField.getText();
        String fourCouverture = fourCouvertureField.getText();
        Book newBook = new Book(title, author, summary, isbn, publicationYear, pageCount, fourCouverture);
        booksListView.getItems().add(newBook);
        new BookRepository().save(newBook);
        System.out.println("Nouveau livre ajoutée : " + newBook);
        preview.setText(String.valueOf(newBook));
        statusLabel.setText("New book added: " + newBook.getTitle());
    }
    }
@FXML
    private void editBook() {
        Book selectedBook = (Book) booksListView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            selectedBook.setTitle(titleTextField.getText());
            selectedBook.setAuthor(authorTextArea.getText());
            selectedBook.setSummary(summaryTextArea.getText());
            selectedBook.setIsbn(isbnTextField.getText());
            selectedBook.setPublicationYear(publicationYearField.getText());
            selectedBook.setPageCount(pageCountField.getText());
            selectedBook.setFourCouverture(fourCouvertureField.getText());
            bookRepository.update(selectedBook);
            statusLabel.setText("Updated book: " + selectedBook.getTitle());
            loadBooks();
        }
    }
@FXML
    private void deleteBook() {
        Book selectedBook = (Book) booksListView.getSelectionModel().getSelectedItem();
    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    confirmationAlert.setTitle("Confirmation suppression");
    confirmationAlert.setHeaderText(null);
    confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer le livre '" + selectedBook.getTitle() + "' ?");

    ButtonType confirmationButton = new ButtonType("Supprimer", ButtonBar.ButtonData.OK_DONE);
    confirmationAlert.getButtonTypes().setAll(confirmationButton, ButtonType.CANCEL);
    Optional<ButtonType> result = confirmationAlert.showAndWait();

    if (result.isPresent() && result.get() == confirmationButton) {

        booksListView.getItems().remove(selectedBook);
        new BookRepository().delete(selectedBook);

        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Suppression réussie");
        successAlert.setHeaderText(null);
        successAlert.setContentText("Le livre a été supprimée avec succès.");
        successAlert.showAndWait();
    }
    }
@FXML
    private void updateBookDetailsFields(Book book) {
        titleTextField.setText(book.getTitle());
        authorTextArea.setText(book.getAuthor());
        summaryTextArea.setText(book.getSummary());
        isbnTextField.setText(book.getIsbn());
        publicationYearField.setText(book.getPublicationYear());
        pageCountField.setText(book.getPageCount());
        fourCouvertureField.setText(book.getFourCouverture());
        preview.setText(book.getPreview());
    }
@FXML
    private void loadBooks() {
        booksListView.getItems().setAll(bookRepository.findAll());
    }
}
