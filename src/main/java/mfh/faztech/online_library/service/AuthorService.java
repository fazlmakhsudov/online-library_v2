package mfh.faztech.online_library.service;


import mfh.faztech.online_library.entity.Author;

import java.sql.SQLException;

public interface AuthorService extends BaseService<Author> {
    Author find(String email, String password) throws SQLException;

    boolean add(String email, String password) throws SQLException;
}

