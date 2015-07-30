package kis.servlet;

import com.alibaba.fastjson.JSON;
import kis.entity.Company;
import kis.dao.CompanyHome;
import kis.entity.KisUser;
import kis.dao.KisUserHome;
import kis.util.JSONResult;
import kis.util.WebUtils;
import org.apache.commons.lang.math.NumberUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by jim on 2015/7/14.
 */
@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int logout = WebUtils.getIntParamValue(request, "logout");
        JSONResult result = new JSONResult();
        if (logout == 1) {
            request.getSession().removeAttribute("user");
            request.getSession().removeAttribute("company");
            result.setSuccess(true);
            JSON.writeJSONStringTo(result, response.getWriter());
            return;
        }

        String name = request.getParameter("username");
        String password = request.getParameter("password");
        int companyId = WebUtils.getIntParamValue(request, "company");
        // find user
        //System.out.println(username + "->" + password);
        KisUser user = new KisUserHome().findUserByName(name);
        if (user == null || !password.equals(user.getPasswd())) {
            // login failed
            result.setErrorMsg("用户名或密码错误");

        } else {
            request.getSession().setAttribute("user", user);
            Company company = new CompanyHome().findById(companyId);
            if (company == null) {
                result.setErrorMsg("公司数据错误");
            } else {
                request.getSession().setAttribute("company", company);
                result.setSuccess(true);
            }
        }

        JSON.writeJSONStringTo(result, response.getWriter());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
