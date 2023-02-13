package servlet;

import service.LoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "login",urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("index.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println(username+" - "+password);

        LoginService loginService = new LoginService();
        boolean isSuccess = loginService.checkLogin(username,password);

        if(isSuccess) {

            HttpSession session = req.getSession();
            session.setAttribute("username",username);
            session.setMaxInactiveInterval(8 * 60 * 60); //set thời gian hết hạn của session

            String data = (String) session.getAttribute("username");

            System.out.println("Giá trị của session: "+data);

            resp.sendRedirect(req.getContextPath()+"/user");
        }else {
            req.getRequestDispatcher("index.jsp").forward(req,resp);
        }

    }
}
