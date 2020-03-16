package com.practice.library.web;

import com.practice.library.entity.Author;
import com.practice.library.entity.Book;
import com.practice.library.repository.impl.MySQLAuthorRepositoryImpl;
import com.practice.library.repository.impl.MySQLBookRepositoryImpl;
import com.practice.library.service.AuthorService;
import com.practice.library.service.BookService;
import com.practice.library.service.impl.AuthorServiceImpl;
import com.practice.library.service.impl.BookServiceImpl;
import com.practice.library.util.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(
        name = "search-controller",
        urlPatterns = "/search"
)
public class SearchServlet extends HttpServlet {
    private AuthorService authorService = new AuthorServiceImpl(new MySQLAuthorRepositoryImpl());
    private BookService bookService = new BookServiceImpl(new MySQLBookRepositoryImpl());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            this.execute(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String refer_to_page = (String) request.getAttribute("refer_to_page");
        request.removeAttribute("refer_to_page");
        request.getRequestDispatcher(
                refer_to_page).forward(request, response);
    }

    private HttpServletRequest execute(HttpServletRequest request) throws SQLException {
        request.removeAttribute("book");
        request.removeAttribute("author");
        request.removeAttribute("nothing_found");

        String name = request.getParameter("name");
        if (name == null && name.isEmpty()) {
            return request;
        }
        String refer_to_page = Path.SEARCH_BY_NAME_PAGE;
        name = name.trim();
        Book book = bookService.find(name);
        if (book != null) {
            request.setAttribute("book", book);
            refer_to_page = Path.BOOK_DETAIL_PAGE;
            request.setAttribute("refer_to_page", refer_to_page);
            return request;
        }
        Author author = authorService.find(name);
        if (author != null) {
            request.setAttribute("author", author);
            refer_to_page = Path.AUTHOR_DETAIL_PAGE;
            request.setAttribute("refer_to_page", refer_to_page);
            return request;
        }
        request.setAttribute("refer_to_page", refer_to_page);
        request.setAttribute("nothing_found", "nothing was found, repeat your query spelling properly");
        return request;
    }
}
