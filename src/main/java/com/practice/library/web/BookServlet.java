package com.practice.library.web;

import com.practice.library.entity.Book;
import com.practice.library.repository.impl.MySQLBookRepositoryImpl;
import com.practice.library.service.BookService;
import com.practice.library.service.impl.BookServiceImpl;
import com.practice.library.util.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

@WebServlet(
        name = "book-controller",
        urlPatterns = "/books"
)
public class BookServlet extends HttpServlet {
    private final BookService bookService = new BookServiceImpl(new MySQLBookRepositoryImpl());
    private static final String REFER_TO_PAGE = "refer_to_page";
    private static final String LIST_BOOKS = "list_books";
    private static final String BOOK = "book";
    private static final String ID = "id";
    private final Logger logger = Logger.getLogger("BookServlet");

    public BookService getBookService() {
        return bookService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            this.execute(request);
            String referToPage = (String) request.getAttribute(REFER_TO_PAGE);
            request.removeAttribute(REFER_TO_PAGE);
            request.getRequestDispatcher(referToPage).forward(request, response);
        } catch (Exception e) {
            logger.info(e.toString());
            e.printStackTrace();
        }
    }


    private HttpServletRequest execute(HttpServletRequest request) throws SQLException {
        String action = "main";
        if (request.getParameter(ID) != null) {
            action = "bookDetail";
        }

        request.removeAttribute(LIST_BOOKS);
        request.removeAttribute(BOOK);

        String referToPage = Path.MAIN_PAGE;
        switch (action) {
            case "main":
                request.setAttribute(LIST_BOOKS, bookService.findAll());
                break;
            case "bookDetail":
                int id = Integer.parseInt(request.getParameter(ID));
                Book book = bookService.find(id);
                request.setAttribute(BOOK, book);
                referToPage = Path.BOOK_DETAIL_PAGE;
                break;
            default:
                break;
        }
        request.setAttribute(REFER_TO_PAGE, referToPage);
        return request;
    }
}
