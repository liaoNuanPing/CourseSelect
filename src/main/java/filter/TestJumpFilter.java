package filter;

import java.io.IOException;

public class TestJumpFilter implements javax.servlet.Filter {
    public void destroy() {
    }

    public void doFilter(javax.servlet.ServletRequest req, javax.servlet.ServletResponse resp, javax.servlet.FilterChain chain) throws javax.servlet.ServletException, IOException {
//        req.getRequestDispatcher("WEB-INF/H-ui.admin/index.html").forward(req,resp);
//        ((HttpServletResponse)resp).sendRedirect("WEB-INF/H-ui.admin/index.html");
            chain.doFilter(req, resp);
    }

    public void init(javax.servlet.FilterConfig config) throws javax.servlet.ServletException {

    }

}
