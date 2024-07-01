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

    public String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public String getAlphaNumericString(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    public String getSalt(String username) {
        String salt=null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT salt from users where username=?");
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next())
            {
                salt=rs.getString("salt");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salt;
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

    public boolean signupAdmin(String username, String name, String surname, String password, String salt)
    {
        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT into users (username, name, surname, type, password, salt) values (?,?,?,?,?,?)");
            preparedStatement1.setString(1, username);
            preparedStatement1.setString(2, name);
            preparedStatement1.setString(3, surname);
            preparedStatement1.setString(4, "admin");
            preparedStatement1.setString(5, password);
            preparedStatement1.setString(6, salt);
            if(preparedStatement1.executeUpdate() == 0){
                System.out.println("Problem");
            }else System.out.println("ADDED user");

            PreparedStatement preparedStatement2 = connection.prepareStatement("INSERT into admin (username) values (?)");
            preparedStatement2.setString(1, username);
            if (preparedStatement2.executeUpdate() == 0) {
                System.out.println("Problem");
                throw new SQLException();
            } else System.out.println("ADDED admin");
        }catch(SQLException e) {
            e.printStackTrace();
            try {
                PreparedStatement delStatement = connection.prepareStatement("DELETE FROM users WHERE username=?");
                delStatement.setString(1, username);
                delStatement.executeUpdate();
            }catch (SQLException q){
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }

    public boolean signupClient(String username, String name, String surname, String address, String afm, String phone, String password, String salt)
    {
        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT into users (username, name, surname, type, password, salt) values (?,?,?,?,?,?)");
            preparedStatement1.setString(1, username);
            preparedStatement1.setString(2, name);
            preparedStatement1.setString(3, surname);
            preparedStatement1.setString(4, "client");
            preparedStatement1.setString(5, password);
            preparedStatement1.setString(6, salt);
            if(preparedStatement1.executeUpdate() == 0){
                System.out.println("Problem");
            }else System.out.println("ADDED user");

            PreparedStatement preparedStatement2 = connection.prepareStatement("INSERT into clients (user_username, address, phone_number, afm) values (?, ?, ?, ?)");
            preparedStatement2.setString(1, username);
            preparedStatement2.setString(2, address);
            preparedStatement2.setString(3, phone);
            preparedStatement2.setString(4, afm);
            if (preparedStatement2.executeUpdate() == 0) {
                System.out.println("Problem");
                throw new SQLException();
            } else System.out.println("ADDED client");
        }catch(SQLException e) {
            e.printStackTrace();
            try {
                PreparedStatement delStatement = connection.prepareStatement("DELETE FROM users WHERE username=?");
                delStatement.setString(1, username);
                delStatement.executeUpdate();
            }catch (SQLException q){
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }

    public boolean signupSeller(String username, String name, String surname, String password, String salt)
    {
        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT into users (username, name, surname, type, password, salt) values (?,?,?,?,?,?)");
            preparedStatement1.setString(1, username);
            preparedStatement1.setString(2, name);
            preparedStatement1.setString(3, surname);
            preparedStatement1.setString(4, "seller");
            preparedStatement1.setString(5, password);
            preparedStatement1.setString(6, salt);
            if(preparedStatement1.executeUpdate() == 0){
                System.out.println("Problem");
            }else System.out.println("ADDED user");

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT into sellers (username) values (?)");
            preparedStatement.setString(1, username);
            if (preparedStatement.executeUpdate() == 0) {
                System.out.println("Problem");
                throw new SQLException();
            } else System.out.println("ADDED seller");
        }catch(SQLException e) {
            e.printStackTrace();
            try {
                PreparedStatement delStatement = connection.prepareStatement("DELETE FROM users WHERE username=?");
                delStatement.setString(1, username);
                delStatement.executeUpdate();
            }catch (SQLException q){
                e.printStackTrace();
            }
            return false;
        }
        return true;
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
