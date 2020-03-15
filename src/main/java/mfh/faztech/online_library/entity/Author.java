package mfh.faztech.online_library.entity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Author {
    protected int id;
    protected String name;
    private String secondName;
    private LocalDate dateOfBirth;
    private Map<Integer, String> books = new HashMap<>();


    public Author() {
    }

    public Author(int id, String name, String secondName, LocalDate dateOfBirth) {
        this.id = id;
        this.name = name;
        this.secondName = secondName;
        this.dateOfBirth = dateOfBirth;
        this.books = books;
    }

    public int getId() {
        return id;
    }

    public void setId(int mId) {
        this.id = mId;
    }

    public String getName() {
        return name;
    }

    public void setName(String mName) {
        this.name = mName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String mSecondName) {
        this.secondName = mSecondName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate mDateOfBirth) {
        this.dateOfBirth = mDateOfBirth;
    }

    public Map<Integer, String> getBooks() {
        return books;
    }

    public void setBooks(Map<Integer, String> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;
        if (!super.equals(o)) return false;
        Author author = (Author) o;
        return secondName.equals(author.secondName) &&
                dateOfBirth.equals(author.dateOfBirth) &&
                books.equals(author.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), secondName, dateOfBirth, books);
    }

    @Override
    public String toString() {
        return "Author{" +
                "Id=" + id +
                ", Name='" + name + '\'' +
                ", SecondName='" + secondName + '\'' +
                ", DateOfBirth=" + dateOfBirth +
                ", Books=" + books +
                '}';
    }
}
