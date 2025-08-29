package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "LogoutServlet", urlPatterns = {"/logout"})
public class LogOutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if(session != null) {
            session.removeAttribute("account");
            session.invalidate();
        }
        // Sửa đường dẫn để trỏ đến Login.jsp trong thư mục views
        resp.sendRedirect(req.getContextPath() + "/views/Login.jsp");
    }
}