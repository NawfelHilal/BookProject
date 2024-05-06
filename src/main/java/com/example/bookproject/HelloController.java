package com.example.bookproject;

import com.example.bookproject.Model.Book;
import com.example.bookproject.repository.BookRepository;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.commons.mail.HtmlEmail;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.StringWriter;
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
    private TextArea preview;

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
        sendEmail();
        List<Book> liste = new BookRepository().findAll();
        ObservableList<Object> items = FXCollections.observableArrayList();
        for (Book book:liste){
            items.add(book);
        }
        booksListView.setItems(items);
        addButton.setOnAction(e -> addBook());
        editButton.setOnAction(e -> editBook());
        deleteButton.setOnAction(e -> deleteBook());
        editButton.setDisable(false);
        deleteButton.setDisable(false);
        booksListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null && newSelection instanceof Book) {
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
        sendEmail(newBook);
    }
    loadBooks();
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
            sendEmail(selectedBook);
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
        sendEmail(selectedBook);
    }
    loadBooks();
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
    private void sendEmail(){
    try {
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class",
                ClasspathResourceLoader.class.getName());
        ve.init();

        Template t = ve.getTemplate("templates/emailTemplate.vm");

        VelocityContext context = new VelocityContext();
        context.put("name", "Nawfel");

        StringWriter writer = new StringWriter();
        t.merge(context, writer);

        HtmlEmail email = new HtmlEmail();
        email.setHostName("mail.gmx.com");
        email.setSmtpPort(587);
        email.setAuthentication("emailjava@gmx.fr","emailjava06");
        email.setStartTLSEnabled(true);
        email.setFrom("emailjava@gmx.fr");
        email.setSubject("Livre ajouter");
        email.setMsg("Le livre ... a était ajouter avec succés");
        email.addTo("emailjava@gmx.fr");

        email.setTextMsg("Votre client mail ne supporte pas le html");
        System.out.println("email sent");
        email.send();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    private void sendEmail(Book book){
        try {
            VelocityEngine ve = new VelocityEngine();
            ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            ve.setProperty("classpath.resource.loader.class",
                    ClasspathResourceLoader.class.getName());
            ve.init();

            Template t = ve.getTemplate("templates/emailTemplate.vm");

            VelocityContext context = new VelocityContext();
            context.put("name", "Nawfel");

            StringWriter writer = new StringWriter();
            t.merge(context, writer);

            HtmlEmail email = new HtmlEmail();
            email.setHostName("mail.gmx.com");
            email.setSmtpPort(587);
            email.setAuthentication("emailjava@gmx.fr","emailjava06");
            email.setStartTLSEnabled(true);
            email.setFrom("emailjava@gmx.fr");
            email.setSubject("Livre ajouter");
            email.setMsg("Le livre"+ book.getTitle() + ", écrit par "+ book.getAuthor() +" a était ajouter avec succés");
            email.addTo("emailjava@gmx.fr");

            email.setTextMsg("Votre client mail ne supporte pas le html");
            System.out.println("email sent");
            email.send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

@FXML
    private void loadBooks() {
        booksListView.getItems().setAll(bookRepository.findAll());
    }
}
