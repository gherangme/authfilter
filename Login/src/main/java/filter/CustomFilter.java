package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//urlPattern: những link khi người dùng gọi sẽ kích hoạt filter
// /*: tất cả các link
@WebFilter(urlPatterns = {"/user"})
public class CustomFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Đây là filter");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(8 * 60 * 60); //set thời gian hết hạn của session

        String data = (String) session.getAttribute("username");

        System.out.println("Giá trị của session: "+data);
        request.setAttribute("username",data);
        if(data==null) {
            response.sendRedirect(request.getContextPath()+"/login");

        }else {
            filterChain.doFilter(request,response);
        }
        //Cho phép truy cập vào servlet được chỉ định ở urlPattern
//        filterChain.doFilter(request,response);
    }
}
