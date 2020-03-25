package com.practice.library.repository.impl;


import com.practice.library.entity.Book;
import com.practice.library.repository.BookRepository;
import com.practice.library.util.DBUtil;

import java.sql.Date;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;


public class MySQLBookRepositoryImpl implements BookRepository {
    private static final String BOOK_ID = "book_id";
    private static final String AUTHOR_ID = "author_id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String DATE_OF_PUBLISH = "date_of_publish";
    private static final String ID = "id";

    private final DBUtil dbUtil = DBUtil.getInstance();


    @Override
    public int create(Book book) throws SQLException {
        dbUtil.connect();
        boolean rowInserted = false;
        try (Statement statement = dbUtil.getJdbcConnection().createStatement()) {
            statement.executeQuery("set autocommit=0;");
            statement.executeQuery("Start transaction;");
        }
        String sql = "INSERT INTO book (name, description, date_of_publish) VALUES (?, ?, ?);";
        try (PreparedStatement preparedStatement = dbUtil.getJdbcConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, book.getName());
            preparedStatement.setString(2, book.getDescription());
            LocalDate date = book.getDateOfPublish();
            preparedStatement.setString(3, date.toString());
            rowInserted = preparedStatement.executeUpdate() > 0;
        }
        sql = "INSERT INTO relation_table (book_id, author_id) VALUES (?, ?);";
        try (PreparedStatement preparedStatement = dbUtil.getJdbcConnection().prepareStatement(sql)) {
            for (Integer author_id : book.getAuthors().keySet()) {
                preparedStatement.setInt(1, book.getId());
                preparedStatement.setInt(2, author_id);
                rowInserted = rowInserted && (preparedStatement.executeUpdate() > 0);
                preparedStatement.clearParameters();
            }
        }
        try (Statement statement = dbUtil.getJdbcConnection().createStatement()) {
            if (!rowInserted) {
                statement.executeQuery("rollback;");
            } else {
                statement.executeQuery("commit;");
            }
            statement.executeQuery("set autocommit=1;");
        }
        dbUtil.disconnect();
        if (rowInserted) {
            return book.getId();
        }
        return -1;
    }

    @Override
    public Book read(int id) throws SQLException {
        Optional<Book> optionalBook = Optional.empty();
        String sql = "SELECT * FROM book WHERE id = ?";
        dbUtil.connect();
        try (PreparedStatement statement = dbUtil.getJdbcConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String bookName = resultSet.getString(NAME);
                    String description = resultSet.getString(DESCRIPTION);
                    Date date = resultSet.getDate(DATE_OF_PUBLISH);
                    LocalDate localDate = LocalDate.parse(date.toString());
                    optionalBook = Optional.of(new Book(id, bookName, description, localDate));
                }
            }
        }
        sql = "SELECT relation_table.author_id, author.name FROM relation_table cross join author on author.id = relation_table.author_id where relation_table.book_id = " + id;
        try (Statement statement = dbUtil.getJdbcConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int authorId = resultSet.getInt(AUTHOR_ID);
                String authorName = resultSet.getString(NAME);
                optionalBook.orElse(new Book()).getAuthors().put(authorId, authorName);
            }
        }
        if (optionalBook.isPresent())
            return optionalBook.get();
        return null;
    }

    @Override
    public Book read(String name) throws SQLException {
        Book book = null;
        String sql = "SELECT * FROM book WHERE  name = ?";
        dbUtil.connect();
        try (PreparedStatement statement = dbUtil.getJdbcConnection().prepareStatement(sql)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt(ID);
                    String description = resultSet.getString(DESCRIPTION);
                    Date date = resultSet.getDate(DATE_OF_PUBLISH);
                    LocalDate localDate = LocalDate.parse(date.toString());
                    book = new Book(id, name, description, localDate);
                }
            }
        }
        if (book == null) {
            return book;
        }
        sql = "SELECT relation_table.author_id, author.name FROM relation_table cross join author on author.id = relation_table.author_id " +
                "where relation_table.book_id = " + book.getId() + ";";
        try (Statement statement = dbUtil.getJdbcConnection().prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int authorId = resultSet.getInt(AUTHOR_ID);
                String authorName = resultSet.getString(NAME);
                book.getAuthors().put(authorId, authorName);
            }
        }
        return book;
    }

    @Override
    public boolean update(Book book) throws SQLException {
        String sql = "UPDATE book SET name = ?, description = ?, date_of_publish = ?";
        sql += " WHERE book.id = ?";
        dbUtil.connect();
        boolean rowUpdated = false;
        try (PreparedStatement statement = dbUtil.getJdbcConnection().prepareStatement(sql)) {
            statement.setString(1, book.getName());
            statement.setString(2, book.getDescription());
            statement.setString(3, book.getDateOfPublish().toString());
            rowUpdated = statement.executeUpdate() > 0;
        }
        dbUtil.disconnect();
        return rowUpdated;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM book where book.id = ?";

        dbUtil.connect();
        boolean rowDeleted = false;
        try (PreparedStatement statement = dbUtil.getJdbcConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        dbUtil.disconnect();
        return rowDeleted;
    }


    @Override
    public List<Book> getAll() throws SQLException {
        List<Book> listBook = new ArrayList<>();
        Map<Integer, Book> mapBook = new HashMap<>();
        String sql = "SELECT * FROM book";
        dbUtil.connect();
        try (Statement statement = dbUtil.getJdbcConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt(ID);
                String bookName = resultSet.getString(NAME);
                String description = resultSet.getString(DESCRIPTION);
                Date date = resultSet.getDate(DATE_OF_PUBLISH);
                LocalDate localDate = LocalDate.parse(date.toString());
                Book book = new Book(id, bookName, description, localDate);
                listBook.add(book);
                mapBook.put(id, book);
            }
        }
        sql = "SELECT * FROM relation_table cross join author on author.id = relation_table.author_id;";
        try (Statement statement = dbUtil.getJdbcConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int bookId = resultSet.getInt(BOOK_ID);
                int authorId = resultSet.getInt(AUTHOR_ID);
                String authorName = resultSet.getString(NAME);
                if (mapBook.containsKey(bookId)) {
                    mapBook.get(bookId).getAuthors().put(authorId, authorName);
                }
            }
        }
        dbUtil.disconnect();
        return listBook;
    }
}
