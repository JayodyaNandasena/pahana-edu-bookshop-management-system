package com.pahanaedu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import com.pahanaedu.model.User;
import com.pahanaedu.model.enums.UserType;
import com.pahanaedu.util.DbConnectionFactory;

public class LoginDao {
	public User authenticateUser(String username, String password) throws SQLException {
		// Get password hash for username
        String storedPasswordHash = getPasswordHash(username);
        
        if (storedPasswordHash == null) {
            // perform dummy hash check to prevent timing attacks
            BCrypt.checkpw(password, "$2a$12$dummy.hash.to.prevent.timing.attacks.");
            return null;
        }
        
        // Verify password
        if (!BCrypt.checkpw(password, storedPasswordHash)) {
            return null;
        }
        
        // Password is correct, fetch and return user data
        return getUserData(username);
    }
    
    private String getPasswordHash(String username) throws SQLException {
        String sql = "SELECT password_hash FROM user WHERE username = ?";
        
        try (Connection conn = DbConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("password_hash");
                }
            }
        }
        return null;
    }
    
    private User getUserData(String username) throws SQLException {
        String sql = "SELECT id, username, name, user_type FROM user WHERE username = ?";
        
        try (Connection conn = DbConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setName(rs.getString("name"));
                    user.setUserType(UserType.valueOf(rs.getString("user_type")));
                    return user;
                }
            }
        }
        return null;
    }
}
