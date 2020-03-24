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
import java.util.logging.Logger;

@WebServlet(
        name = "author-controller",
        urlPatterns = "/authors"
)
public class AuthorServlet extends HttpServlet {
    private final AuthorService authorService = new AuthorServiceImpl(new MySQLAuthorRepositoryImpl());
    private static final String LIST_AUTHORS = "list_authors";
    private static final String AUTHOR = "author";
    private static final String ID = "id";
    private final Logger logger = Logger.getLogger("AuthorServlet");

    public AuthorService getAuthorService() {
        return authorService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.removeAttribute(LIST_AUTHORS);
            request.removeAttribute(AUTHOR);

            String action = "main";
            if (request.getParameter(ID) != null) {
                action = "authorDetail";
            }
            String referToPage = Path.MAIN_PAGE;
            switch (action) {
                case LIST_AUTHORS:
                    request.setAttribute(LIST_AUTHORS, authorService.findAll());
                    break;
                case "authorDetail":
                    int id = Integer.parseInt(request.getParameter(ID));
                    Author author = authorService.find(id);
                    request.setAttribute(AUTHOR, author);
                    referToPage = Path.AUTHOR_DETAIL_PAGE;
                    break;
                default:
                    break;
            }

            request.getRequestDispatcher(referToPage).forward(request, response);
        } catch (Exception e) {
            logger.info(e.toString());
        }
    }
}
