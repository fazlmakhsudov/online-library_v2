package com.practice.library.web.filter;

import com.practice.library.util.DBInfo;
import com.practice.library.util.Path;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebFilter(filterName = "indexFilter", urlPatterns = {"/"},
        initParams = @WebInitParam(name = "password", value = "111111frost"))
public class IndexFilter implements Filter {
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        // No necessity to implement until
        Optional<String> optional = Optional.of(filterConfig.getInitParameter("password"));
        DBInfo.setJdbcPassword(optional.orElse("nopassword"));
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
