package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Category;
import model.User;
import service.CategoryService;
import service.CategoryServiceImpl;

@WebServlet(urlPatterns = { "/admin/category/list" })
public class CategoryController extends HttpServlet {
	CategoryService cateService = new CategoryServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Kiểm tra quyền hạn người dùng
		HttpSession session = req.getSession();
		if(session != null && session.getAttribute("account") != null) {
			User user = (User) session.getAttribute("account");
			// Chỉ admin (roleid=1) mới có quyền truy cập
			if(user.getRoleid() == 1) {
				List<Category> cateList = cateService.getAll();
				req.setAttribute("cateList", cateList);
				RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/list-category.jsp");
				dispatcher.forward(req, resp);
			} else {
				resp.sendRedirect(req.getContextPath() + "/waiting");
			}
		} else {
			resp.sendRedirect(req.getContextPath() + "/Login");
		}
	}
}