package mfh.faztech.online_library.repository.impl;


import mfh.faztech.online_library.entity.Book;
import mfh.faztech.online_library.repository.BookRepository;
import mfh.faztech.online_library.util.DBUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySQLBookRepositoryImpl implements BookRepository {


    private final DBUtil dbUtil = DBUtil.getInstance();

    @Override
    public int create(Book book) throws SQLException {
        dbUtil.connect();

        Statement statement = dbUtil.getJdbcConnection().createStatement();
        statement.executeQuery("set autocommit=0;");
        statement.executeQuery("Start transaction;");
        statement.close();

        String sql = "INSERT INTO book (name, description, date_of_publish) VALUES (?, ?, ?);";
        PreparedStatement preparedStatement = dbUtil.getJdbcConnection().prepareStatement(sql);
        preparedStatement.setString(1, book.getName());
        preparedStatement.setString(2, book.getDescription());
        LocalDate date = book.getDateOfPublish();
        preparedStatement.setDate(3, new Date(date.getYear(), date.getMonthValue(), date.getDayOfMonth()));
        boolean rowInserted = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();

        sql = "INSERT INTO relation_table (book_id, author_id) VALUES (?, ?);";
        PreparedStatement preparedStatement2 = dbUtil.getJdbcConnection().prepareStatement(sql);
        for (Integer author_id : book.getAuthors().keySet()) {
            preparedStatement2.setInt(1, book.getId());
            preparedStatement2.setInt(2, author_id);
            rowInserted = rowInserted && (preparedStatement2.executeUpdate() > 0);
            preparedStatement2.clearParameters();
        }

        statement = dbUtil.getJdbcConnection().createStatement();
        if (!rowInserted) {
            statement.executeQuery("rollback;");
        } else {
            statement.executeQuery("commit;");
        }
        statement.executeQuery("set autocommit=1;");
        statement.close();
        dbUtil.disconnect();
        if (rowInserted) {
            return book.getId();
        }
        return -1;
    }

    @Override
    public Book read(int id) throws SQLException {
        Book book = null;
        String sql = "SELECT * FROM book WHERE id = ?";
        dbUtil.connect();
        PreparedStatement statement = dbUtil.getJdbcConnection().prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            Date date = resultSet.getDate("date_of_publish");
            LocalDate localDate = LocalDate.parse(date.toString());
            book = new Book(id, name, description, localDate);
        }
        sql = "SELECT relation_table.author_id, author.name FROM relation_table cross join author on author.id = relation_table.author_id where relation_table.book_id = "+id;
        statement = dbUtil.getJdbcConnection().prepareStatement(sql);
   //     statement.setInt(1, id);
        System.out.println("*** 2");
        resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            int author_id = resultSet.getInt("author_id");
            String author_name = resultSet.getString("name");
            book.getAuthors().put(author_id, author_name);
        }

        resultSet.close();
        statement.close();

        return book;
    }

    @Override
    public Book read(String name) throws SQLException {
        Book book = null;
        String sql = "SELECT * FROM book WHERE  name = ?";
        dbUtil.connect();
        PreparedStatement statement = dbUtil.getJdbcConnection().prepareStatement(sql);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String description = resultSet.getString("description");
            Date date = resultSet.getDate("date_of_publish");
            LocalDate localDate = LocalDate.parse(date.toString());
            book = new Book(id, name, description, localDate);
        }
        if (book == null) {
            return book;
        }
        sql = "SELECT relation_table.author_id, author.name FROM relation_table cross join author on author.id = relation_table.author_id " +
                "where relation_table.book_id = " + book.getId() + ";";
        statement = dbUtil.getJdbcConnection().prepareStatement(sql);
     //   statement.setInt(1, book.getId());
        resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            int author_id = resultSet.getInt("author_id");
            String author_name = resultSet.getString("name");
            book.getAuthors().put(author_id, author_name);
        }

        resultSet.close();
        statement.close();

        return book;
    }

    @Override
    public boolean update(Book book) throws SQLException {
        String sql = "UPDATE book SET title = ?, author = ?, price = ?, user_id = ?";
        sql += " WHERE book_id = ?";
        dbUtil.connect();

        PreparedStatement statement = dbUtil.getJdbcConnection().prepareStatement(sql);


        statement.setInt(5, book.getId());

        boolean rowUpdated = statement.executeUpdate() > 0;
        statement.close();
        dbUtil.disconnect();
        return rowUpdated;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM book where book_id = ?";

        dbUtil.connect();

        PreparedStatement statement = dbUtil.getJdbcConnection().prepareStatement(sql);
        statement.setInt(1, id);

        boolean rowDeleted = statement.executeUpdate() > 0;
        statement.close();
        dbUtil.disconnect();
        return rowDeleted;
    }

    @Override
    public List<Book> getAll() throws SQLException {
        List<Book> listBook = new ArrayList<>();
        Map<Integer, Book> mapBook = new HashMap<>();
        String sql = "SELECT * FROM book";
        dbUtil.connect();
        Statement statement = dbUtil.getJdbcConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            Date date = resultSet.getDate("date_of_publish");
            LocalDate localDate = LocalDate.parse(date.toString());
            Book book = new Book(id, name, description, localDate);
            listBook.add(book);
            mapBook.put(id, book);
        }
        sql = "SELECT * FROM relation_table cross join author on author.id = relation_table.author_id;";
        resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            int book_id = resultSet.getInt("book_id");
            int author_id = resultSet.getInt("author_id");
            String author_name = resultSet.getString("name");
            if (mapBook.containsKey(book_id)) {
                mapBook.get(book_id).getAuthors().put(author_id, author_name);
            }
        }

        resultSet.close();
        statement.close();

        dbUtil.disconnect();

        return listBook;
    }
}
