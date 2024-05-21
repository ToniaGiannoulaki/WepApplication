package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SystemDao {

    private Connection connection;

    public SystemDao() {
        connection = Utilities.DBUtil.getConnection(); //get DB connection
    }

    public String loginUnameCheck(String username) {
        String answer=null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE username=?");
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next())
            {
                answer="There is no user with the username: "+username+", please enter a valid username!";
                System.out.println("WRONG UNAME");
            }
            else
            {
                answer=username;
                System.out.println("CORRECT UNAME");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answer;
    }

    public String loginPasswordCheck(String username, String password) {
        String answer=null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from users where (username=? and password=?)");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next())
            {
                answer="Wrong Password!";
            }
            else
            {
                answer="You logged in!";
                System.out.println("Pass found");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answer;
    }

    public String getRole(String username) {
        String role=null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT type FROM users WHERE username=?");
            preparedStatement.setString(1, username);
            ResultSet rs1 = preparedStatement.executeQuery();
            if (rs1.next())
            {
                role= rs1.getString("type");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }

    public String signupUsernameCheck(String username) {
        String answer=null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users where username=?");
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next())
            {
                answer="ok";
            }
            else
            {
                answer="There is already a user with the username: "+username+", please enter a different username.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answer;
    }
}
