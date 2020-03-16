package com.practice.library.web;

import com.practice.library.entity.Author;
import com.practice.library.repository.impl.MySQLAuthorRepositoryImpl;
import com.practice.library.service.AuthorService;
import com.practice.library.service.impl.AuthorServiceImpl;
import com.practice.library.util.Path;

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
    private AuthorService authorService = new AuthorServiceImpl(new MySQLAuthorRepositoryImpl());

    public AuthorService getAuthorService() {
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
