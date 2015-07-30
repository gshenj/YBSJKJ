package kis.filter;

import kis.entity.KisUser;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by jim on 2015/7/14.
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = "/sale/*")
public class AuthFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request=(HttpServletRequest)req;

        boolean isAjax = false;
        String x_requested_with = request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equalsIgnoreCase(x_requested_with)) {
            isAjax = true;
        }
        //String path = request.getRequestURI();
        HttpServletResponse response=(HttpServletResponse)resp;
        KisUser user = (KisUser) request.getSession().getAttribute("user");
        if (user==null) {
            if (isAjax)
                response.getWriter().write("{\"error\":1}");   // 没有登录，需要登录
            else
                response.sendRedirect(request.getContextPath()+"/login.jsp");
            return;
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        System.out.println("Init AuthFilter!");
    }


}
