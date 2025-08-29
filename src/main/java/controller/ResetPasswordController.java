package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import service.UserService;
import service.UserServiceImpl;
import model.User;
import DBC.DBConnection;

@WebServlet(urlPatterns = "/reset-password")
public class ResetPasswordController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String token = req.getParameter("token");
        
        if (token == null || token.isEmpty()) {
            resp.sendRedirect("login"); // Nếu không có token, chuyển về trang login
            return;
        }
        
        // Kiểm tra token có hợp lệ không (chưa hết hạn và đúng người dùng)
        User user = validateToken(token);
        
        if (user == null) {
            req.setAttribute("alert", "Link đặt lại mật khẩu không hợp lệ hoặc đã hết hạn!");
            req.getRequestDispatcher("/views/Login.jsp").forward(req, resp);
            return;
        }
        
        // Token hợp lệ, chuyển đến trang đặt lại mật khẩu
        req.setAttribute("token", token);
        req.getRequestDispatcher("/views/reset-password.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String token = req.getParameter("token");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        
        // Kiểm tra các trường đã nhập đầy đủ
        if (token == null || password == null || confirmPassword == null) {
            req.setAttribute("alert", "Vui lòng nhập đầy đủ thông tin!");
            req.setAttribute("token", token);
            req.getRequestDispatcher("/views/reset-password.jsp").forward(req, resp);
            return;
        }
        
        // Kiểm tra mật khẩu và xác nhận mật khẩu có khớp nhau không
        if (!password.equals(confirmPassword)) {
            req.setAttribute("alert", "Mật khẩu xác nhận không khớp!");
            req.setAttribute("token", token);
            req.getRequestDispatcher("/views/reset-password.jsp").forward(req, resp);
            return;
        }
        
        // Kiểm tra token có hợp lệ không
        User user = validateToken(token);
        
        if (user == null) {
            req.setAttribute("alert", "Link đặt lại mật khẩu không hợp lệ hoặc đã hết hạn!");
            req.getRequestDispatcher("/views/Login.jsp").forward(req, resp);
            return;
        }
        
        // Cập nhật mật khẩu mới
        boolean success = updatePassword(user.getUserName(), password);
        
        // Xóa token đã sử dụng
        deleteToken(token);
        
        if (success) {
            req.setAttribute("alert", "Đặt lại mật khẩu thành công! Vui lòng đăng nhập.");
            req.getRequestDispatcher("/views/Login.jsp").forward(req, resp);
        } else {
            req.setAttribute("alert", "Có lỗi xảy ra! Vui lòng thử lại sau.");
            req.setAttribute("token", token);
            req.getRequestDispatcher("/views/reset-password.jsp").forward(req, resp);
        }
    }
    
    private User validateToken(String token) {
        System.out.println("Đang kiểm tra token: " + token);
        DBConnection db = new DBConnection();
        
        // Kiểm tra token tồn tại không
        String checkSql = "SELECT COUNT(*) FROM password_reset_tokens WHERE token = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(checkSql)) {
            ps.setString(1, token);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Số lượng token tìm thấy: " + count);
            }
        } catch (Exception e) {
            System.out.println("Lỗi kiểm tra token: " + e.getMessage());
        }
        
        // SQL gốc
        String sql = "SELECT u.* FROM [User] u JOIN password_reset_tokens t ON u.id = t.user_id " +
                    "WHERE t.token = ? AND t.expiry_date > GETDATE()";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, token);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserName(rs.getString("userName"));
                user.setEmail(rs.getString("email"));
                user.setPassWord(rs.getString("passWord"));
                System.out.println("Tìm thấy user: " + user.getUserName());
                return user;
            } else {
                System.out.println("Không tìm thấy user nào với token này");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi SQL: " + e.getMessage());
        }
        return null;
    }
    
    private boolean updatePassword(String username, String newPassword) {
        DBConnection db = new DBConnection();
        String sql = "UPDATE [User] SET passWord = ? WHERE userName = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newPassword); // Nên mã hóa mật khẩu trước khi lưu vào DB
            ps.setString(2, username);
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private void deleteToken(String token) {
        DBConnection db = new DBConnection();
        String sql = "DELETE FROM password_reset_tokens WHERE token = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, token);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}