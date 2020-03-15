package mfh.faztech.online_library.web;

import mfh.faztech.online_library.entity.Book;
import mfh.faztech.online_library.repository.impl.MySQLBookRepositoryImpl;
import mfh.faztech.online_library.service.BookService;
import mfh.faztech.online_library.service.impl.BookServiceImpl;
import mfh.faztech.online_library.util.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(
        name = "book-controller",
        urlPatterns = "/books"
)
public class BookServlet extends HttpServlet {
    private static BookService bookService = new BookServiceImpl(new MySQLBookRepositoryImpl());

    public static BookService getBookService() {
        return bookService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    private HttpServletRequest execute(HttpServletRequest request) throws SQLException {
        String action = "main";
        if (request.getParameter("id") != null) {
            action = "bookDetail";
        }

        request.removeAttribute("list_books");
        request.removeAttribute("book");

        String refer_to_page = Path.MAIN_PAGE;
        switch (action) {
            case "main":
                request.setAttribute("list_books", bookService.findAll());
                break;
            case "bookDetail":
                int id = Integer.parseInt(request.getParameter("id"));
                Book book = bookService.find(id);
                request.setAttribute("book", book);
                refer_to_page = Path.BOOK_DETAIL_PAGE;
                    break;
            default:
                break;
        }
        request.setAttribute("refer_to_page", refer_to_page);
        return request;
    }
}
