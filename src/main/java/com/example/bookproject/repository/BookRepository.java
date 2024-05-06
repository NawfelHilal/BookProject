package com.example.bookproject.repository;

import com.example.bookproject.Model.Book;
import com.example.bookproject.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class BookRepository {

    // Trouver un livre par son ID
    public Book findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Book.class, id);
        }
    }

    // Récupérer la liste de tous les livres
    public List<Book> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Book", Book.class).list();
        }
    }

    // Ajouter un nouveau livre
    public void save(Book book) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println(session);
            transaction = session.beginTransaction();
            session.save(book);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    // Mettre à jour un livre existant
    public void update(Book book) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(book); // ou session.merge(book) selon votre cas d'utilisation
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    // Supprimer un livre
    public void delete(Book book) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(book);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
}