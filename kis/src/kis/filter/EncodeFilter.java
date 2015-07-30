package kis.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by jim on 2015/7/14.
 */
@WebFilter(filterName = "EncodeFilter", urlPatterns = "*")
public class EncodeFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        // 注意这里请求和响应都强制转化了一下
        request.setCharacterEncoding("UTF-8");	   //设置请求编码“UTF-8”比较通用
        response.setCharacterEncoding("UTF-8");	//设置相应编码
        chain.doFilter(req, resp);//转发请求
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
