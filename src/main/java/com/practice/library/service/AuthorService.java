package com.practice.library.service;


import com.practice.library.entity.Author;

import java.sql.SQLException;

public interface AuthorService extends BaseService<Author> {
    Author find(String email, String password) throws SQLException;

    boolean add(String email, String password) throws SQLException;
}

