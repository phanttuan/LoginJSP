package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.UUID;

import DBC.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import service.UserService;
import service.UserServiceImpl;
import model.User;
import util.EmailUtils;

@WebServlet(urlPatterns = "/forgot-password")
public class ForgotPasswordController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String emailOrUsername = req.getParameter("emailOrUsername");
        UserService service = new UserServiceImpl();
        System.out.println("emailOrUsername nhập vào: " + emailOrUsername);
        User user = service.findByEmailOrUsername(emailOrUsername);

        if (user == null) {
            req.setAttribute("alert", "Không tìm thấy tài khoản!");
            req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
            return;
        }

        // Tạo token ngẫu nhiên
        String token = UUID.randomUUID().toString();
        // TODO: Lưu token vào DB, gắn với user này, set thời gian hết hạn...
        // Lưu token vào DB
        DBConnection db = new DBConnection();
     // Sửa lại SQL để phù hợp với SQL Server
        String tokenSql = "INSERT INTO password_reset_tokens (user_id, token, expiry_date) VALUES (?, ?, DATEADD(HOUR, 1, GETDATE()))";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(tokenSql)) {
            ps.setInt(1, user.getId());
            ps.setString(2, token);
            int rows = ps.executeUpdate();
            
            // Thêm log để kiểm tra
            System.out.println("Token lưu thành công: " + (rows > 0 ? "Có" : "Không") + ", Token: " + token);
            
            if (rows <= 0) {
                throw new Exception("Không thể lưu token");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("LỖI KHI LƯU TOKEN: " + e.getMessage());
            req.setAttribute("alert", "Lỗi hệ thống: " + e.getMessage());
            req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
            return; // Dừng ngay nếu không lưu được token
        }

        String resetLink = "http://localhost:8080/LoginJSP/reset-password?token=" + token;
        String subject = "Đặt lại mật khẩu";
        String content = "Nhấn vào link sau để đặt lại mật khẩu: " + resetLink;

        try {
            EmailUtils.sendMail(user.getEmail(), subject, content);
            req.setAttribute("alert", "Đã gửi email đặt lại mật khẩu!");
        } catch (Exception e) {
            req.setAttribute("alert", "Lỗi gửi email: " + e.getMessage());
        }
        req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
        
    }
    
    
    
}