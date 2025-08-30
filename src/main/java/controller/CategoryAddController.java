package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import model.Category;
import model.Constant;
import model.User;
import service.CategoryService;
import service.CategoryServiceImpl;

@MultipartConfig
@WebServlet(urlPatterns = { "/admin/category/add" })
public class CategoryAddController extends HttpServlet {
    CategoryService cateService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Kiểm tra quyền hạn người dùng
        HttpSession session = req.getSession();
        if (session != null && session.getAttribute("account") != null) {
            User user = (User) session.getAttribute("account");
            // Chỉ admin (roleid=1) mới có quyền thêm danh mục
            if (user.getRoleid() == 1) {
                RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/add-category.jsp");
                dispatcher.forward(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath() + "/waiting");
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/Login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Kiểm tra quyền hạn người dùng
        HttpSession session = req.getSession();
        if (session != null && session.getAttribute("account") != null) {
            User user = (User) session.getAttribute("account");
            // Chỉ admin (roleid=1) mới có quyền thêm danh mục
            if (user.getRoleid() == 1) {
                Category category = new Category();
                try {
                    resp.setContentType("text/html");
                    resp.setCharacterEncoding("UTF-8");
                    req.setCharacterEncoding("UTF-8");

                    // Read form fields directly via Servlet API
                    String name = req.getParameter("name");
                    if (name != null) {
                        category.setCatename(name);
                    }

                    Part iconPart = req.getPart("icon");
                    if (iconPart != null && iconPart.getSize() > 0) {
                        String submitted = iconPart.getSubmittedFileName();
                        String fileName = null;
                        if (submitted != null && !submitted.isEmpty()) {
                            int dot = submitted.lastIndexOf('.');
                            String ext = dot >= 0 ? submitted.substring(dot + 1) : "";
                            fileName = System.currentTimeMillis() + (ext.isEmpty() ? "" : "." + ext);
                        } else {
                            fileName = String.valueOf(System.currentTimeMillis());
                        }

                        File destDir = new File(Constant.DIR, "category");
                        if (!destDir.exists()) destDir.mkdirs();
                        File destFile = new File(destDir, fileName);

                        try (InputStream in = iconPart.getInputStream(); FileOutputStream out = new FileOutputStream(destFile)) {
                            byte[] buf = new byte[8192];
                            int r;
                            while ((r = in.read(buf)) != -1) {
                                out.write(buf, 0, r);
                            }
                        }
                        category.setIcon("category/" + fileName);
                    }

                    cateService.insert(category);
                    resp.sendRedirect(req.getContextPath() + "/admin/category/list");
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ServletException(e);
                }
            } else {
                resp.sendRedirect(req.getContextPath() + "/waiting");
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/Login");
        }
    }
}