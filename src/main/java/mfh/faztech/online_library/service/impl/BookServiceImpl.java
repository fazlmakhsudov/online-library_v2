package mfh.faztech.online_library.service.impl;


import mfh.faztech.online_library.entity.Book;
import mfh.faztech.online_library.repository.BookRepository;
import mfh.faztech.online_library.service.BookService;

import java.sql.SQLException;
import java.util.List;

public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public int add(Book item) throws SQLException {
        return bookRepository.create(item);
    }

    public Book find(int id) throws SQLException {
        return bookRepository.read(id);
    }

    public Book find(String name) throws SQLException {
        return bookRepository.read(name);
    }

    public boolean save(Book item) throws SQLException {
        return bookRepository.update(item);
    }

    public boolean remove(int id) throws SQLException {
        return bookRepository.delete(id);
    }

    public List<Book> findAll() throws SQLException {
        return bookRepository.getAll();
    }
}
