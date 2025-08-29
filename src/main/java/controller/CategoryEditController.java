package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload2.core.FileUploadException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.Category;
import model.Constant;
import service.CategoryService;
import service.CategoryServiceImpl;

@WebServlet(urlPatterns = { "/admin/category/edit" })
public class CategoryEditController extends HttpServlet {
	CategoryService cateService = new CategoryServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		Category category = cateService.get(Integer.parseInt(id));
		req.setAttribute("category", category);
		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/edit-category.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Category category = new Category();
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
		servletFileUpload.setHeaderEncoding("UTF-8");
		try {
			resp.setContentType("text/html");
			resp.setCharacterEncoding("UTF-8");
			req.setCharacterEncoding("UTF-8");
			List<FileItem> items = servletFileUpload.parseRequest(req);
			for (FileItem item : items) {
				if (item.getFieldName().equals("id")) {
					category.setCateid(Integer.parseInt(item.getString()));
				} else if (item.getFieldName().equals("name")) {
					category.setCatename(item.getString("UTF-8"));
				} else if (item.getFieldName().equals("icon")) {
					if (item.getSize() > 0) {
						String originalFileName = item.getName();
						int index = originalFileName.lastIndexOf(".");
						String ext = originalFileName.substring(index + 1);
						String fileName = System.currentTimeMillis() + "." + ext;
						File file = new File(Constant.DIR + "/category/" + fileName);
						item.write(file);
						category.setIcon("category/" + fileName);
					} else {
						category.setIcon(null);
					}
				}
			}
			cateService.edit(category);
			resp.sendRedirect(req.getContextPath() + "/admin/category/list");
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
