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
import java.util.Optional;
import java.util.logging.Logger;

@WebServlet(
        name = "search-controller",
        urlPatterns = "/search"
)
public class SearchServlet extends HttpServlet {
    private final AuthorService authorService = new AuthorServiceImpl(new MySQLAuthorRepositoryImpl());
    private final BookService bookService = new BookServiceImpl(new MySQLBookRepositoryImpl());
    private static final String REFER_TO_PAGE = "refer_to_page";
    private static final String BOOK = "book";
    private static final String AUTHOR = "author";
    private static final String NOTHING_FOUND = "nothing_found";
    private static final String NAME = "name";
    private final Logger logger = Logger.getLogger("SearchServlet");

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            this.execute(request);
            String referToPage = (String) request.getAttribute(REFER_TO_PAGE);
            request.removeAttribute(REFER_TO_PAGE);
            request.getRequestDispatcher(referToPage).forward(request, response);
        } catch (Exception e) {
            logger.info(e.toString());
        }
    }

    private HttpServletRequest execute(HttpServletRequest request) throws SQLException {
        request.removeAttribute(BOOK);
        request.removeAttribute(AUTHOR);
        request.removeAttribute(NOTHING_FOUND);
        Optional<String> optionalName = Optional.of(request.getParameter(NAME));
        if (optionalName.orElse("nothing").compareTo("nothing") == 0) {
            return request;
        }
        String referToPage = Path.SEARCH_BY_NAME_PAGE;
        Book book = bookService.find(optionalName.get().trim());
        if (book != null) {
            request.setAttribute(BOOK, book);
            referToPage = Path.BOOK_DETAIL_PAGE;
            request.setAttribute(REFER_TO_PAGE, referToPage);
            return request;
        }
        Author author = authorService.find(optionalName.get().trim());
        if (author != null) {
            request.setAttribute(AUTHOR, author);
            referToPage = Path.AUTHOR_DETAIL_PAGE;
            request.setAttribute(REFER_TO_PAGE, referToPage);
            return request;
        }
        request.setAttribute(REFER_TO_PAGE, referToPage);
        request.setAttribute(NOTHING_FOUND, "nothing was found, repeat your query spelling properly");
        return request;
    }
}
