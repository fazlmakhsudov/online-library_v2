package com.practice.library.web.filter;

import com.practice.library.util.Path;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "indexFilter", urlPatterns = {"/"})
public class IndexFilter implements Filter {
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        // No necessity to implement until
    }

    @Override
    public final void doFilter(final ServletRequest request,
                               final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        ((HttpServletResponse) response).sendRedirect(Path.MAIN_PAGE);
    }

    @Override
    public void destroy() {
        // No necessity to implement until
    }
}
