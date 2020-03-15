package mfh.faztech.online_library.repository;

import mfh.faztech.online_library.entity.Author;

import java.sql.SQLException;

public interface AuthorRepository extends BaseRepository<Author> {
    boolean create(String email, String password) throws SQLException;

    Author read(String email, String password) throws SQLException;
}
