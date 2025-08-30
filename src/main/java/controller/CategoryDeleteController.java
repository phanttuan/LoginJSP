package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import service.CategoryService;
import service.CategoryServiceImpl;

@WebServlet(urlPatterns = { "/admin/category/delete" })
public class CategoryDeleteController extends HttpServlet {
	CategoryService cateService = new CategoryServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		// Kiểm tra quyền hạn người dùng
		HttpSession session = req.getSession();
		if(session != null && session.getAttribute("account") != null) {
			User user = (User) session.getAttribute("account");
			// Chỉ admin (roleid=1) mới có quyền xóa danh mục
			if(user.getRoleid() == 1) {
				String id = req.getParameter("id");
				cateService.delete(Integer.parseInt(id));
				resp.sendRedirect(req.getContextPath() + "/admin/category/list");
			} else {
				resp.sendRedirect(req.getContextPath() + "/waiting");
			}
		} else {
			resp.sendRedirect(req.getContextPath() + "/Login");
		}
	}
}