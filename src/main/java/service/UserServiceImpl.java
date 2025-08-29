package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import DBC.DBConnection;
import model.User;

public class UserServiceImpl implements UserService {
	UserDao userDao = new UserDaoImpl();

	@Override
	public User login(String username, String password) {
		User user = this.get(username);
		if (user != null && password.equals(user.getPassWord())) {
			return user;
		}
		return null;
	}

	@Override
	public User get(String username) {
		return userDao.get(username);
	}
	@Override
	public User findByEmailOrUsername(String emailOrUsername) {
		DBConnection db = new DBConnection();
	    String sql = "SELECT id, email, userName, fullName, passWord FROM [User] WHERE email = ? OR userName = ?";
	    try (Connection conn = db.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, emailOrUsername);
	        ps.setString(2, emailOrUsername);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            User user = new User();
	            // Quan trọng: Lấy và set id
	            user.setId(rs.getInt("id"));
	            user.setEmail(rs.getString("email"));
	            user.setUserName(rs.getString("userName"));
	            user.setFullName(rs.getString("fullName"));
	            user.setPassWord(rs.getString("passWord"));
	            
	            System.out.println("Tìm thấy user id: " + user.getId());
	            return user;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}

}
