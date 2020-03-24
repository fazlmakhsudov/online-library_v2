package com.practice.library.repository.impl;


import com.practice.library.entity.Author;
import com.practice.library.repository.AuthorRepository;
import com.practice.library.util.DBUtil;

import java.sql.Date;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;


public class MySQLAuthorRepositoryImpl implements AuthorRepository {
    private static final String BOOK_ID = "book_id";
    private static final String AUTHOR_ID = "author_id";
    private static final String NAME = "name";
    private static final String SECOND_NAME = "second_name";
    private static final String DATE_OF_BIRTH = "date_of_birth";
    private static final String ID = "id";


    private final DBUtil dbUtil = DBUtil.getInstance();


    @Override
    public int create(Author item) throws SQLException {
        return 0;
    }

    @Override
    public Author read(int id) throws SQLException {
        Optional<Author> optionalAuthor = Optional.empty();
        String sql = "SELECT * FROM author WHERE id = ?";

        dbUtil.connect();

        try (PreparedStatement statement = dbUtil.getJdbcConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String authorName = resultSet.getString(NAME);
                    String description = resultSet.getString(SECOND_NAME);
                    Date date = resultSet.getDate(DATE_OF_BIRTH);
                    LocalDate dateOfBirth = LocalDate.parse(date.toString());
                    optionalAuthor = Optional.of(new Author(id, authorName, description, dateOfBirth));
                }
            }
        }
        sql = "SELECT relation_table.book_id, book.name FROM relation_table cross join book on book.id = relation_table.book_id " +
                "where relation_table.author_id = " + id + ";";
        try (Statement statement = dbUtil.getJdbcConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int bookId = resultSet.getInt(BOOK_ID);
                String bookName = resultSet.getString(NAME);
                optionalAuthor.orElse(new Author()).getBooks().put(bookId, bookName);
            }
        }
        if (optionalAuthor.isPresent())
            return optionalAuthor.get();
        return null;
    }

    @Override
    public Author read(String name) throws SQLException {
        Author author = null;
        String sql = "SELECT * FROM author WHERE name = ?";

        dbUtil.connect();
        try (PreparedStatement statement = dbUtil.getJdbcConnection().prepareStatement(sql)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int authorId = resultSet.getInt(ID);
                    String description = resultSet.getString(SECOND_NAME);
                    Date date = resultSet.getDate(DATE_OF_BIRTH);
                    LocalDate authorDateOfBirth = LocalDate.parse(date.toString());
                    author = new Author(authorId, name, description, authorDateOfBirth);
                }
            }
        }
        if (author == null) {
            return author;
        }
        sql = "SELECT relation_table.book_id, book.name FROM relation_table cross join book on book.id = relation_table.book_id " +
                "where relation_table.author_id = " + author.getId() + ";";
        try (Statement statement = dbUtil.getJdbcConnection().prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int bookId = resultSet.getInt(BOOK_ID);
                String bookName = resultSet.getString(NAME);
                author.getBooks().put(bookId, bookName);
            }
        }
        return author;
    }

    @Override
    public boolean update(Author item) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }


    @Override
    public List<Author> getAll() throws SQLException {
        List<Author> listAuthor = new ArrayList<>();
        Map<Integer, Author> mapAuthor = new HashMap<>();
        String sql = "SELECT * FROM author";
        dbUtil.connect();
        try (Statement statement = dbUtil.getJdbcConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt(ID);
                String authorName = resultSet.getString(NAME);
                String description = resultSet.getString(SECOND_NAME);
                Date date = resultSet.getDate(DATE_OF_BIRTH);
                LocalDate authorDateOfBirth = LocalDate.parse(date.toString());
                Author author = new Author(id, authorName, description, authorDateOfBirth);
                listAuthor.add(author);
                mapAuthor.put(id, author);
            }
        }
        sql = "SELECT * FROM relation_table cross join book on book.id = relation_table.book_id;";
        try (Statement statement = dbUtil.getJdbcConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int bookId = resultSet.getInt(BOOK_ID);
                int authorId = resultSet.getInt(AUTHOR_ID);
                String bookName = resultSet.getString(NAME);
                if (mapAuthor.containsKey(authorId)) {
                    mapAuthor.get(authorId).getBooks().put(bookId, bookName);
                }
            }
        }
        dbUtil.disconnect();

        return listAuthor;
    }
}
