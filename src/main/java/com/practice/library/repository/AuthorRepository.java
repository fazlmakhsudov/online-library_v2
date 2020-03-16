package com.practice.library.repository;

import com.practice.library.entity.Author;

import java.sql.SQLException;

public interface AuthorRepository extends BaseRepository<Author> {
    boolean create(String email, String password) throws SQLException;

    Author read(String email, String password) throws SQLException;
}
