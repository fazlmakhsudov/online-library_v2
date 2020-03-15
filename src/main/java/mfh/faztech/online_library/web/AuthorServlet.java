package mfh.faztech.online_library.web;

import mfh.faztech.online_library.entity.Author;
import mfh.faztech.online_library.repository.impl.MySQLAuthorRepositoryImpl;
import mfh.faztech.online_library.service.AuthorService;
import mfh.faztech.online_library.service.impl.AuthorServiceImpl;
import mfh.faztech.online_library.util.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(
        name = "author-controller",
        urlPatterns = "/authors"
)
public class AuthorServlet extends HttpServlet {
    private static AuthorService authorService = new AuthorServiceImpl(new MySQLAuthorRepositoryImpl());

    public static AuthorService getAuthorService() {
        return authorService;
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
        request.removeAttribute("list_authors");
        request.removeAttribute("author");


        String action = "main";
        if (request.getParameter("id") != null) {
            action = "authorDetail";
        }
        String refer_to_page = Path.MAIN_PAGE;
        switch (action) {
            case "list_authors":
                request.setAttribute("list_authors", authorService.findAll());
                break;
            case "authorDetail":
                int id = Integer.parseInt(request.getParameter("id"));
                Author author = authorService.find(id);
                request.setAttribute("author", author);
                refer_to_page = Path.AUTHOR_DETAIL_PAGE;
                break;
            default:
                break;
        }
        request.setAttribute("refer_to_page", refer_to_page);
        return request;
    }
}
