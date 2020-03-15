package mfh.faztech.online_library.web.filter;

import mfh.faztech.online_library.util.Path;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "indexFilter", urlPatterns = {"/"})
public class IndexFilter implements Filter {
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public final void doFilter(final ServletRequest request,
                               final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        ((HttpServletResponse) response).sendRedirect(Path.MAIN_PAGE);
    }

    @Override
    public void destroy() {
    }
}
