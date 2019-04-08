package fliter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request= (HttpServletRequest) req;
        String uri= request.getRequestURI();
        System.out.println(uri);
        if (uri.contains("js")||uri.contains("css")||uri .contains("jpg")||uri.contains("png")||uri.contains("ttf")||uri.contains("ico")||uri.contains("Login")
                ||uri.contains("wx"))
            chain.doFilter(request, resp);
        else{
            if (request.getSession().getAttribute("isLogin")==null)
                request.getRequestDispatcher("SystemLogin").forward(request,resp);
            else if ("true".equals(request.getSession().getAttribute("isLogin")))
                chain.doFilter(request, resp);
        }
//        chain.doFilter(request, resp);

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
