package mfh.faztech.online_library.entity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Book {
    protected int id;
    protected String name;
    private String description;
    private LocalDate dateOfPublish;
    private Map<Integer, String> authors = new HashMap<>();

    public Book() {
    }

    public Book(int id, String name, String description, LocalDate dateOfPublish) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateOfPublish = dateOfPublish;
        this.authors = authors;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String mDescription) {
        this.description = mDescription;
    }

    public LocalDate getDateOfPublish() {
        return dateOfPublish;
    }

    public void setDateOfPublish(LocalDate mDateOfPublish) {
        this.dateOfPublish = mDateOfPublish;
    }

    public Map<Integer, String> getAuthors() {
        return authors;
    }

    public void setAuthors(Map<Integer, String> authors) {
        this.authors = authors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        if (!super.equals(o)) return false;
        Book book = (Book) o;
        return description.equals(book.description) &&
                dateOfPublish.equals(book.dateOfPublish) &&
                authors.equals(book.authors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), description, dateOfPublish, authors);
    }

    @Override
    public String toString() {
        return "Book{" +
                "Id=" + id +
                ", Name='" + name + '\'' +
                ", Description='" + description + '\'' +
                ", LocalDate=" + dateOfPublish +
                ", Authors=" + authors +
                '}';
    }
}
